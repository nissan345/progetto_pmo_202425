package main.control;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.swing.Timer;
import main.aboufaris.classes.*;
import main.aboufaris.interfaces.*;
import main.fabbri.classes.*;
import main.giuseppetti.classes.*;
import main.neri.classes.*;
import main.view.*;

public final class Control {
	
    // Costanti
    private final int MISSIONI_TOTALI = 3;
    private final int SOGLIA_BASSA = 20;
    private final int SOGLIA_CRITICA = 5;
    private final int DECADIMENTO_STATO = 20000;
    // Singleton
    private static Control singletonController;

    private final Casa casa;
    private final View view;
    private Timer gameTimer;
    private Personaggio personaggio;
    private int contatoreMissioni;
    private NPC npcCorrente;
    
    // Costruttore privato
    private Control(){
       this.casa = new CasaImpl();
       this.contatoreMissioni = 0;
       this.view = new View();
       this.view.setController(this);
       this.npcCorrente = null;
    }

    // Singleton 
    public static Control getControlInstance(){
        if(Control.singletonController == null){
            Control.singletonController = new Control();
        }
        return Control.singletonController;
    }

    // Inizio del gioco
    public void startGame(){
        view.mostraMenu();
        creaPersonaggioPersonalizzato();
        creaMondo();
        avviaTimerBisogni();
        view.mostraStatistiche(personaggio.stampaStato());
        view.mostraCasa();
    }

    // METODI PER INIZIALIZZARE IL MONDO DI GIOCO
    // FUNZIONA
    // Creazione del mondo di gioco, stanze, NPC e oggetti
    private void creaMondo(){
         // Creazione delle stanze
        Stanza bagno = FabbricaOggetti.creaBagno();
        Stanza camera = FabbricaOggetti.creaCameraDaLetto();
        Stanza cucina = FabbricaOggetti.creaCucina();
        Stanza salotto = FabbricaOggetti.creaSalotto();
        Stanza giardino = FabbricaOggetti.creaGiardino();
        Stanza sgabuzzino = FabbricaOggetti.creaSgabuzzino();
        
        // Aggiunge le stanze alla casa
        casa.aggiungiStanza(bagno);
        casa.aggiungiStanza(camera);
        casa.aggiungiStanza(cucina);
        casa.aggiungiStanza(salotto);
        casa.aggiungiStanza(giardino);
        casa.aggiungiStanza(sgabuzzino);

        // Creazione Npc
        Madre madre = new Madre(salotto);
        Padre padre = new Padre(giardino);
        Fratello fratello = new Fratello(cucina);

        // Aggiungere nelle stanze gli NPC
        salotto.setNpc(madre);
        giardino.setNpc(padre);
        cucina.setNpc(fratello);
    }
    
    // FUNZIONA
    // Creazione del personaggio personalizzato
    private void creaPersonaggioPersonalizzato() {
        String nome = view.chiediNomePersonaggio();
        Vestito vestiti = scegliOpzioneDaEnum("Scegli i vestiti", Vestito.values());
        Capelli capelli = scegliOpzioneDaEnum("Scegli i capelli", Capelli.values());
        this.personaggio = new Personaggio(nome, vestiti, capelli);
    }
    
    // FUNZIONA
    private <T> T scegliOpzioneDaEnum(String messaggio, T[] opzioniDisponibili){
        List<String> opzioni = Arrays.stream(opzioniDisponibili)
            .map(Object::toString)
            .toList();
        int scelta = view.mostraOpzioniPersonalizzazione(messaggio, opzioni);
        return opzioniDisponibili[scelta];
    }
    
    // FUNZIONA
    // Metodo che gestisce il timer, serve per il decadimento dei bisogni
    private void avviaTimerBisogni(){
        gameTimer = new Timer(DECADIMENTO_STATO, e-> {
                personaggio.decadimentoStato();
                List<String> avvisi = controllaStatiCritici();
                if(!avvisi.isEmpty()){
                    for(String a : avvisi){
                        view.mostraAvviso(a); 
                    }
                }
                view.mostraStatistiche(this.personaggio.stampaStato());
                gestisciSconfitta();
            
        });
        gameTimer.start();
    }
    
    public void aggiornaBottoniNpc(NPC npc) {
        // qui decidi quali label mostrare
        String nome = npc.getRelazione();
        String relazione = npc.getRelazione();

        // passi SOLO stringhe + le azioni da eseguire
        view.mostraNpcInterattivi(
            nome,
            relazione,
            () -> onClickNpc(npc),        // primo click: dialogo
            () -> onSecondClickNpc(npc)   // secondo click: opzioni
        );
    }
    
    // Mezzo funziona, devo sistemare che se si esce dalla stanza si toglie il bottone per parlare con NPC
    public void onClickNpc(NPC n){
    	this.npcCorrente = n;
        view.mostraMessaggio(n.getDialogoIniziale());
    }
    public void onSecondClickNpc(NPC n) {
        this.npcCorrente = n;

        List<OpzioniInterazione> opzioni = n.getOpzioniDisponibili(personaggio);
        if (opzioni == null || opzioni.isEmpty()) {
            view.mostraMessaggio("Non ci sono opzioni di interazione.");
            return;
        }

        // Etichette leggibili (se non ti interessa la bella label, usa op::toString)
        List<String> labels = opzioni.stream()
                .map(this::labelPer)   // vedi metodo sotto
                .toList();

        int idx = view.mostraOpzioniIndice(
                "Interazione con " + n.getRelazione(),
                "Scegli un'opzione di interazione:",
                labels
        );

        if (idx >= 0) {
            onSceltaOpzioneInterazione(opzioni.get(idx));
        }
    }

    private String labelPer(OpzioniInterazione op) {
        return switch (op) {
            case CHIEDI_MISSIONE -> "Chiedi missione";
            case CONSEGNA_MISSIONE -> "Consegna missione";
            case MISSIONE_IN_CORSO -> "Aiuto missione";
            case ESCI -> "Esci";
        };
    }

    public void onSceltaOpzioneInterazione(OpzioniInterazione scelta) {
    	if (npcCorrente == null) {
            view.mostraErrore("Nessun NPC selezionato!");
            return;
        }
        switch (scelta) {
            case CHIEDI_MISSIONE -> {
                Missione m = npcCorrente.assegnaMissione(personaggio);
                if (m != null) {
                    personaggio.aggiungiMissione(m);
                    view.mostraMessaggio("Nuova missione: " + m.getNome() + "\n" + m.getDescrizione());
                } else {
                    view.mostraMessaggio("Non ci sono missioni disponibili al momento.");
                }
            }
            case MISSIONE_IN_CORSO -> {
                Missione m = personaggio.getMissioneAttivaConNPC(npcCorrente).get();
                if (m != null) {
                	view.mostraMessaggio(npcCorrente.getDialogoMissioneInCorso(m));
                } else {
                	view.mostraMessaggio("Non ci sono missioni attive disponibili");
                }
            }
            case CONSEGNA_MISSIONE -> {
                List<String> msgs = npcCorrente.consegnaMissione(personaggio);
                boolean completata = msgs.stream().anyMatch(t -> t.contains("' completata!"));
                if (completata) { contatoreMissioni++; gestisciVittoria(); }
                msgs.forEach(view::mostraMessaggio);
            }
            case ESCI -> view.mostraMessaggio("Arrivederci!");
            default -> view.mostraMessaggio("Opzione non valida.");
        }
    }
    public void stopGame(){
        if(gameTimer != null){
            gameTimer.stop();
        }
    }

    public Personaggio getPersonaggio(){
      return personaggio;
    }

    // DA VEDERE
    private boolean isSconfitta(){
        // Personaggio muore perché uno dei suoi bisogni è sotto la soglia
        return personaggio.getEnergia() == 0 || personaggio.getFame() ==0 || personaggio.getIgiene() == 0 
        || personaggio.getSete() == 0;
    }

    // Funziona
     public void onClickEntra(String nomeStanza){
        Optional<Stanza> ris = casa.entraInStanza(nomeStanza);
        if(ris.isEmpty()){
            view.mostraErrore("Stanza non trovata!");
            return;
        }
        // Aggiornare la posizione del personaggio, affinché possa usare un oggetto
        personaggio.scegliStanza(ris.get());
        String descrizione = ris.get().toString();
        view.mostraStanza(nomeStanza, descrizione);
        view.clearAzioniNpc();
        mostraOggettiStanzaCorrente();
        mostraNpcInStanzaCorrente();
    }

     //(Kind of))
    public void onClickEsci(String nomeStanza){
        casa.esciDaStanza();
        view.mostraCasa(); // tornare nel menu principale
    }

    // Metodo che serve per gli effetti dell'uso dell'oggetto
    public void onClickOggetto(OggettoGioco oggettoGioco){
    	Stanza corrente = getStanzaCorrente();
        if (!corrente.hasOggettoStanza(oggettoGioco)) {
            view.mostraErrore("L'oggetto non si trova in stanza!");
            return;
        }

        if (!oggettoGioco.richiedeScelta()) {
            // caso semplice
            var ra = oggettoGioco.usa(personaggio);
            String msg = personaggio.interagisci(oggettoGioco);
            view.mostraMessaggio(msg);
            view.mostraStatistiche(personaggio.stampaStato());
            return;
        }

        // caso con scelta: prima messaggio poi lista opzioni
        var intro = oggettoGioco.usa(personaggio); 
        if (intro != null && intro.getMessaggio() != null && !intro.getMessaggio().isEmpty()) {
            view.mostraMessaggio(intro.getMessaggio());
        }

        var opzioni = oggettoGioco.opzioniDisponibili(personaggio);
        Object scelta = view.mostraDialogSceltaGenerica("Scegli un'opzione","Azioni disponibili:", opzioni);
        if (scelta != null) {
            var ra = oggettoGioco.usa(personaggio, scelta);
            personaggio.applicaRisultatoAzione(ra, oggettoGioco.getNome());
            view.mostraStatistiche(personaggio.stampaStato());
        }
    }



	// Per la visualizzazione della mappa 
    public void getMappaCompleta(){
        Map<String,Stanza> stanze = casa.getStanze();
        for(Map.Entry<String, Stanza> s: stanze.entrySet()){
            String nome = s.getKey();
            String descrizione = s.getValue().toString();
            Stanza stanza = s.getValue();
            view.mostraStanza(nome, descrizione);
        }
    }

    private Stanza getStanzaCorrente(){
        return casa.getStanzaCorrente()
            .orElseThrow(() -> new IllegalStateException("Nessuna stanza corrente"));
    }

    public void mostraOggettiStanzaCorrente() {
        Stanza stanza = getStanzaCorrente();
        List<OggettoGioco> oggettiCorrenti = stanza.getOggettiInStanza();

        List<String> labels = oggettiCorrenti.stream()
            .map(o -> o.getNome() + " - " + o.getDescrizione())
            .toList();

        view.mostraOggettiInStanza(labels, idx -> {
            if (idx >= 0 && idx < oggettiCorrenti.size()) {
                onClickOggetto(oggettiCorrenti.get(idx));
            }
        });
    }

    public void mostraNpcInStanzaCorrente(){
        Stanza stanzaCorrente = getStanzaCorrente();
        if(stanzaCorrente.getNpcInStanza().isEmpty()){
        	NPC npcInStanza = null;
            view.mostraMessaggio("Non ci sono NPC in questa stanza.");
        }else{
            NPC npcInStanza = stanzaCorrente.getNpcInStanza().get();
            aggiornaBottoniNpc(npcInStanza);
        }
       
    }


    // Metodo che verifica se i bisogni sono sotto la soglia
    private List<String> controllaStatiCritici(){
        List<String> avvisi = new ArrayList<>();
        String nome = personaggio.getNome();

        aggiungiAvvisoBisogno(avvisi, personaggio.getFame(), nome, " deve mangiare!", "STA PER SVENIRE DALLA FAME!");
        aggiungiAvvisoBisogno(avvisi, personaggio.getEnergia(), nome, " deve dormire!", "STA PER PERDERE I SENSI!");
        aggiungiAvvisoBisogno(avvisi, personaggio.getIgiene(), nome, " deve lavarsi!", "NON SI RIESCE A RESPIRARGLI VICINO!");
        aggiungiAvvisoBisogno(avvisi, personaggio.getSete(), nome, " deve bere!", "STA PER DISIDRATARSI!");
        
        return avvisi;
    }

    private void aggiungiAvvisoBisogno(List<String> avvisi, int valore, String nome, String messaggioBasso, String messaggioCritico){
        if(valore < SOGLIA_CRITICA) {
            avvisi.add("ALLARME! " + nome + " " + messaggioCritico);
        }else if(valore < SOGLIA_BASSA) {
            avvisi.add("Attenzione! " + nome + " " + messaggioBasso);
        }
    }

    
   // METODI PER IL GIOCO
    // Metodo che mostra su schermata tutte le missioni attive del personaggio
    public void getMissioniAttive(){
        Optional<Missione> missione = personaggio.getMissioneAttivaConNPC(this.npcCorrente);
        if(!missione.isEmpty()) {
        	Missione missioneAttiva = missione.get();
        	view.mostraMissioneAttiva(missioneAttiva.getNome(), missioneAttiva.getDescrizione());
        }
            
       
    }

    public void gestisciVittoria(){
        // Si vince nel caso in cui un personaggio riesce a finire tutte le missioni
        if(contatoreMissioni == MISSIONI_TOTALI){
            view.mostraVittoria();
            gameTimer.stop();
        }
       
    }

    public void gestisciSconfitta(){
        // Personaggio muore perché uno dei suoi bisogni è sotto la soglia
        if(isSconfitta()){
            view.mostraSconfitta();
            gameTimer.stop();
        }
    }

	public void onOpzioneScelta(String valueOf) {
		// TODO Auto-generated method stub
		
	}


}


    