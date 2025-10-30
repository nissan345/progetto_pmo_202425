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
       this.casa = CasaImpl.getCasaInstance();
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
    
    // Mezzo funziona, devo sistemare che se si esce dalla stanza si toglie il bottone per parlare con NPC
    public void onClickNpc(NPC n){
    	this.npcCorrente = n;
        view.mostraMessaggio(n.getDialogoIniziale());
    }
    
    //YES
    public void onSecondClickNpc(NPC n){
    	this.npcCorrente = n;
        List<OpzioniInterazione> opzioni = n.getOpzioniDisponibili(personaggio);
        view.mostraOpzioni(opzioni); // menu che dovrebbe mostrare le opzioni che fornisce l'NPC
    }
 
    //YES
    public void onSceltaOpzioneInterazione(OpzioniInterazione scelta){
    	if (npcCorrente == null) {
            view.mostraErrore("Nessun NPC selezionato!");
            return;
        }
        String messaggio = "";
        switch(scelta) {
            case CHIEDI_MISSIONE -> {
                Missione missione = npcCorrente.assegnaMissione(personaggio);
                if (missione != null) {
                    messaggio = "Nuova missione: " + missione.getNome();
                    personaggio.aggiungiMissione(missione);
                    view.mostraMessaggio(messaggio);
                } else {
                    messaggio ="Non ci sono missioni disponibili al momento.";
                    view.mostraMessaggio(messaggio);
                }
            }
            case CONSEGNA_MISSIONE -> {
                List<String> messaggi = npcCorrente.consegnaMissione(personaggio);
                boolean missioniCompletata = messaggi.stream()
                    .anyMatch(m -> m.contains("' completata!"));
                if(missioniCompletata){
                    contatoreMissioni++;
                    gestisciVittoria();
                }
                for(String m : messaggi)
                    view.mostraMessaggio(m);
            }
            case ESCI -> {
                messaggio = "Arrivederci!";
                view.mostraMessaggio(messaggio);
            }
            default -> {
                messaggio = "Opzione non valida.";
                view.mostraMessaggio(messaggio);
            }
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
        view.mostraStanza(nomeStanza);
        mostraOggettiStanzaCorrente();
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
        var intro = oggettoGioco.usa(personaggio); // “Apri il frigorifero...”
        if (intro != null && intro.getMessaggio() != null && !intro.getMessaggio().isEmpty()) {
            view.mostraMessaggio(intro.getMessaggio());
        }

        var opzioni = oggettoGioco.opzioniDisponibili(personaggio);
        Object scelta = view.mostraDialogSceltaGenerica("Scegli un'opzione", opzioni);
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
            Stanza stanza = s.getValue();
            view.mostraStanza(nome);
        }
    }

    private Stanza getStanzaCorrente(){
        return casa.getStanzaCorrente()
            .orElseThrow(() -> new IllegalStateException("Nessuna stanza corrente"));
    }

    public void mostraOggettiStanzaCorrente(){
        Stanza stanzaCorrente = getStanzaCorrente();
        List<OggettoGioco> oggettiPresenti = stanzaCorrente.getOggettiInStanza();
        List<String> nomeOggetti = new ArrayList<>();
        for(OggettoGioco o : oggettiPresenti) {
        	String nomeOggetto = o.getNome() + o.getDescrizione();
        	nomeOggetti.add(nomeOggetto);
        }
        view.mostraOggettiInStanza(nomeOggetti);
    }

    public void mostraNpcInStanzaCorrente(){
        Stanza stanzaCorrente = getStanzaCorrente();
        if(stanzaCorrente.getNpcInStanza().isEmpty()){
            view.mostraMessaggio("Non ci sono NPC in questa stanza.");
        }else{
            NPC npcInStanza = stanzaCorrente.getNpcInStanza().get();
            view.mostraNpc(npcInStanza.getRelazione());
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
        Optional<Missione> missione = personaggio.getMissioniAttivaConNPC(this.npcCorrente);
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


    
