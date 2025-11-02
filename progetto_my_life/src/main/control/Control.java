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
    private Character character;
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
        view.mostraStatistiche(character.stampaStato());
        view.mostraCasa();
    }

    // METODI PER INIZIALIZZARE IL MONDO DI GIOCO
    // FUNZIONA
    // Creazione del mondo di gioco, stanze, NPC e oggetti
    private void creaMondo(){
         // Creazione delle stanze
        Room bagno = FabbricaOggetti.creaBagno();
        Room camera = FabbricaOggetti.creaCameraDaLetto();
        Room cucina = FabbricaOggetti.creaCucina();
        Room salotto = FabbricaOggetti.creaSalotto();
        Room giardino = FabbricaOggetti.creaGiardino();
        Room sgabuzzino = FabbricaOggetti.creaSgabuzzino();
        
        // Aggiunge le stanze alla casa
        casa.aggiungiStanza(bagno);
        casa.aggiungiStanza(camera);
        casa.aggiungiStanza(cucina);
        casa.aggiungiStanza(salotto);
        casa.aggiungiStanza(giardino);
        casa.aggiungiStanza(sgabuzzino);

        // Creazione Npc
        Mum mum = new Mum(salotto);
        Dad dad = new Dad(giardino);
        Brother brother = new Brother(cucina);

        // Aggiungere nelle stanze gli NPC
        salotto.setNpc(mum);
        giardino.setNpc(dad);
        cucina.setNpc(brother);
    }
    
    // FUNZIONA
    // Creazione del character personalizzato
    private void creaPersonaggioPersonalizzato() {
        String nome = view.chiediNomePersonaggio();
        Vestito vestiti = scegliOpzioneDaEnum("Scegli i vestiti", Vestito.values());
        Capelli capelli = scegliOpzioneDaEnum("Scegli i capelli", Capelli.values());
        this.character = new Character(nome, vestiti, capelli);
    }
    
    // FUNZIONA
    private <T> T scegliOpzioneDaEnum(String message, T[] opzioniDisponibili){
        List<String> opzioni = Arrays.stream(opzioniDisponibili)
            .map(Object::toString)
            .toList();
        int scelta = view.mostraOpzioniPersonalizzazione(message, opzioni);
        return opzioniDisponibili[scelta];
    }
    
    // FUNZIONA
    // Metodo che gestisce il timer, serve per il decadimento dei bisogni
    private void avviaTimerBisogni(){
        gameTimer = new Timer(DECADIMENTO_STATO, e-> {
                character.decadimentoStato();
                List<String> avvisi = controllaStatiCritici();
                if(!avvisi.isEmpty()){
                    for(String a : avvisi){
                        view.mostraAvviso(a); 
                    }
                }
                view.mostraStatistiche(this.character.stampaStato());
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
    
    // Mezzo funziona, devo sistemare che se si esce dalla room si toglie il bottone per parlare con NPC
    public void onClickNpc(NPC n){
    	this.npcCorrente = n;
        view.mostraMessaggio(n.getDialogoIniziale());
    }
    public void onSecondClickNpc(NPC n) {
        this.npcCorrente = n;

        List<InteractionOption> opzioni = n.getOpzioniDisponibili(character);
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

    private String labelPer(InteractionOption op) {
        return switch (op) {
            case REQUEST_QUEST -> "Chiedi quest";
            case TURN_IN_QUEST -> "Consegna quest";
            case QUEST_IN_PROGRESS -> "Aiuto quest";
            case EXIT -> "Esci";
        };
    }

    public void onSceltaOpzioneInterazione(InteractionOption scelta) {
    	if (npcCorrente == null) {
            view.mostraErrore("Nessun NPC selezionato!");
            return;
        }
        switch (scelta) {
            case REQUEST_QUEST -> {
                Quest m = npcCorrente.assegnaMissione(character);
                if (m != null) {
                    character.aggiungiMissione(m);
                    view.mostraMessaggio("Nuova quest: " + m.getNome() + "\n" + m.getDescrizione());
                } else {
                    view.mostraMessaggio("Non ci sono missioni disponibili al momento.");
                }
            }
            case QUEST_IN_PROGRESS -> {
                Quest m = character.getMissioneAttivaConNPC(npcCorrente).get();
                if (m != null) {
                	view.mostraMessaggio(npcCorrente.getDialogoMissioneInCorso(m));
                } else {
                	view.mostraMessaggio("Non ci sono missioni attive disponibili");
                }
            }
            case TURN_IN_QUEST -> {
                List<String> msgs = npcCorrente.consegnaMissione(character);
                boolean completata = msgs.stream().anyMatch(t -> t.contains("' completata!"));
                if (completata) { contatoreMissioni++; gestisciVittoria(); }
                msgs.forEach(view::mostraMessaggio);
            }
            case EXIT -> view.mostraMessaggio("Arrivederci!");
            default -> view.mostraMessaggio("Opzione non valida.");
        }
    }
    public void stopGame(){
        if(gameTimer != null){
            gameTimer.stop();
        }
    }

    public Character getPersonaggio(){
      return character;
    }

    // DA VEDERE
    private boolean isSconfitta(){
        // Character muore perché uno dei suoi bisogni è sotto la soglia
        return character.getEnergia() == 0 || character.getFame() ==0 || character.getIgiene() == 0 
        || character.getSete() == 0;
    }

    // Funziona
     public void onClickEntra(String nomeStanza){
        Optional<Room> ris = casa.entraInStanza(nomeStanza);
        if(ris.isEmpty()){
            view.mostraErrore("Room non trovata!");
            return;
        }
        // Aggiornare la posizione del character, affinché possa usare un oggetto
        character.scegliStanza(ris.get());
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
    	Room corrente = getStanzaCorrente();
        if (!corrente.hasOggettoStanza(oggettoGioco)) {
            view.mostraErrore("L'oggetto non si trova in room!");
            return;
        }

        if (!oggettoGioco.richiedeScelta()) {
            // caso semplice
            var ra = oggettoGioco.usa(character);
            String msg = character.interagisci(oggettoGioco);
            view.mostraMessaggio(msg);
            view.mostraStatistiche(character.stampaStato());
            return;
        }

        // caso con scelta: prima message poi lista opzioni
        var intro = oggettoGioco.usa(character); 
        if (intro != null && intro.getMessaggio() != null && !intro.getMessaggio().isEmpty()) {
            view.mostraMessaggio(intro.getMessaggio());
        }

        var opzioni = oggettoGioco.opzioniDisponibili(character);
        Object scelta = view.mostraDialogSceltaGenerica("Scegli un'opzione","Azioni disponibili:", opzioni);
        if (scelta != null) {
            var ra = oggettoGioco.usa(character, scelta);
            character.applicaRisultatoAzione(ra, oggettoGioco.getNome());
            view.mostraStatistiche(character.stampaStato());
        }
    }



	// Per la visualizzazione della mappa 
    public void getMappaCompleta(){
        Map<String,Room> stanze = casa.getStanze();
        for(Map.Entry<String, Room> s: stanze.entrySet()){
            String nome = s.getKey();
            String descrizione = s.getValue().toString();
            Room room = s.getValue();
            view.mostraStanza(nome, descrizione);
        }
    }

    private Room getStanzaCorrente(){
        return casa.getStanzaCorrente()
            .orElseThrow(() -> new IllegalStateException("Nessuna room corrente"));
    }

    public void mostraOggettiStanzaCorrente() {
        Room room = getStanzaCorrente();
        List<OggettoGioco> oggettiCorrenti = room.getOggettiInStanza();

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
        Room stanzaCorrente = getStanzaCorrente();
        if(stanzaCorrente.getNpcInStanza().isEmpty()){
        	NPC npcInStanza = null;
            view.mostraMessaggio("Non ci sono NPC in questa room.");
        }else{
            NPC npcInStanza = stanzaCorrente.getNpcInStanza().get();
            aggiornaBottoniNpc(npcInStanza);
        }
       
    }


    // Metodo che verifica se i bisogni sono sotto la soglia
    private List<String> controllaStatiCritici(){
        List<String> avvisi = new ArrayList<>();
        String nome = character.getNome();

        aggiungiAvvisoBisogno(avvisi, character.getFame(), nome, " deve mangiare!", "STA PER SVENIRE DALLA FAME!");
        aggiungiAvvisoBisogno(avvisi, character.getEnergia(), nome, " deve dormire!", "STA PER PERDERE I SENSI!");
        aggiungiAvvisoBisogno(avvisi, character.getIgiene(), nome, " deve lavarsi!", "NON SI RIESCE A RESPIRARGLI VICINO!");
        aggiungiAvvisoBisogno(avvisi, character.getSete(), nome, " deve bere!", "STA PER DISIDRATARSI!");
        
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
    // Metodo che mostra su schermata tutte le missioni attive del character
    public void getMissioniAttive(){
        Optional<Quest> quest = character.getMissioneAttivaConNPC(this.npcCorrente);
        if(!quest.isEmpty()) {
        	Quest missioneAttiva = quest.get();
        	view.mostraMissioneAttiva(missioneAttiva.getNome(), missioneAttiva.getDescrizione());
        }
            
       
    }

    public void gestisciVittoria(){
        // Si vince nel caso in cui un character riesce a finire tutte le missioni
        if(contatoreMissioni == MISSIONI_TOTALI){
            view.mostraVittoria();
            gameTimer.stop();
        }
       
    }

    public void gestisciSconfitta(){
        // Character muore perché uno dei suoi bisogni è sotto la soglia
        if(isSconfitta()){
            view.mostraSconfitta();
            gameTimer.stop();
        }
    }

	public void onOpzioneScelta(String valueOf) {
		// TODO Auto-generated method stub
		
	}


}


    