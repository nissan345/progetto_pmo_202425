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

    private final House casa;
    private final View view;
    private Timer gameTimer;
    private MainCharacter personaggio;
    private int contatoreQuesti;
    private NPC npcCorrente;
    
    // Costruttore privato
    private Control(){
       this.casa = new House();
       this.contatoreQuesti = 0;
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
        creaMainCharacterPersonalizzato();
        creaMondo();
        avviaTimerBisogni();
        view.mostraStatistiche(personaggio.printState());
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
        casa.addRoom(bagno);
        casa.addRoom(camera);
        casa.addRoom(cucina);
        casa.addRoom(salotto);
        casa.addRoom(giardino);
        casa.addRoom(sgabuzzino);

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
    private void creaMainCharacterPersonalizzato() {
        String name = view.chiediNameMainCharacter();
        Outfit outfit = scegliOpzioneDaEnum("Scegli i outfit", Outfit.values());
        Hair hair = scegliOpzioneDaEnum("Scegli i hair", Hair.values());
        this.personaggio = new MainCharacter(name, outfit, hair);
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
                personaggio.stateDecay();
                List<String> avvisi = controllaStatiCritici();
                if(!avvisi.isEmpty()){
                    for(String a : avvisi){
                        view.mostraAvviso(a); 
                    }
                }
                view.mostraStatistiche(this.personaggio.printState());
                gestisciSconfitta();
            
        });
        gameTimer.start();
    }
    
    public void aggiornaBottoniNpc(NPC npc) {
        // qui decidi quali label mostrare
        String name = npc.getRelazione();
        String relazione = npc.getRelazione();

        // passi SOLO stringhe + le azioni da eseguire
        
    }
    
    // Mezzo funziona, devo sistemare che se si esce dalla room si toglie il bottone per parlare con NPC
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
            case CHIEDI_MISSIONE -> "Chiedi quest";
            case CONSEGNA_MISSIONE -> "Consegna quest";
            case MISSIONE_IN_CORSO -> "Aiuto quest";
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
                Quest m = npcCorrente.assegnaQuest(personaggio);
                if (m != null) {
                    personaggio.addQuest(m);
                    view.mostraMessaggio("Nuova quest: " + m.getName() + "\n" + m.getDescription());
                } else {
                    view.mostraMessaggio("Non ci sono questi disponibili al momento.");
                }
            }
            case MISSIONE_IN_CORSO -> {
                Quest m = personaggio.getOngoingQuestWithNPC(npcCorrente).get();
                if (m != null) {
                	view.mostraMessaggio(npcCorrente.getDialogoQuestInCorso(m));
                } else {
                	view.mostraMessaggio("Non ci sono questi attive disponibili");
                }
            }
            case CONSEGNA_MISSIONE -> {
                List<String> msgs = npcCorrente.consegnaQuest(personaggio);
                boolean completata = msgs.stream().anyMatch(t -> t.contains("' completata!"));
                if (completata) { contatoreQuesti++; gestisciVittoria(); }
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

    public MainCharacter getMainCharacter(){
      return personaggio;
    }

    // DA VEDERE
    private boolean isSconfitta(){
        // MainCharacter muore perché uno dei suoi bisogni è sotto la soglia
        return personaggio.getEnergy() == 0 || personaggio.getHunger() ==0 || personaggio.getHygiene() == 0 
        || personaggio.getThirst() == 0;
    }

    // Funziona
     public void onClickEntra(String nameRoom){
        Optional<Room> ris = casa.enterRoom(nameRoom);
        if(ris.isEmpty()){
            view.mostraErrore("Room non trovata!");
            return;
        }
        // Aggiornare la posizione del personaggio, affinché possa usare un oggetto
        personaggio.pickCurrentRoom(ris.get());
        String description = ris.get().toString();
        view.mostraRoom(nameRoom, description);
        view.clearAzioniNpc();
        mostraOggettiCurrentRoom();
        mostraNpcInCurrentRoom();
    }

     //(Kind of))
    public void onClickEsci(String nameRoom){
        casa.exitRoom();
        view.mostraCasa(); // tornare nel menu principale
    }

    // Metodo che serve per gli effetti dell'uso dell'oggetto
    public void onClickOggetto(OggettoGioco oggettoGioco){
    	Room corrente = getCurrentRoom();
        if (!corrente.hasOggettoRoom(oggettoGioco)) {
            view.mostraErrore("L'oggetto non si trova in room!");
            return;
        }

        if (!oggettoGioco.richiedeScelta()) {
            // caso semplice
            var ra = oggettoGioco.usa(personaggio);
            String msg = personaggio.interagisci(oggettoGioco);
            view.mostraMessaggio(msg);
            view.mostraStatistiche(personaggio.printState());
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
            personaggio.applicaRisultatoAzione(ra, oggettoGioco.getName());
            view.mostraStatistiche(personaggio.printState());
        }
    }



	// Per la visualizzazione della mappa 
    public void getMappaCompleta(){
        Map<String,Room> stanze = casa.getRooms();
        for(Map.Entry<String, Room> s: stanze.entrySet()){
            String name = s.getKey();
            String description = s.getValue().toString();
            Room room = s.getValue();
            view.mostraRoom(name, description);
        }
    }

    private Room getCurrentRoom(){
        return casa.getCurrentRoom()
            .orElseThrow(() -> new IllegalStateException("Nessuna room corrente"));
    }

    public void mostraOggettiCurrentRoom() {
        Room room = getCurrentRoom();
        List<OggettoGioco> oggettiCorrenti = room.getOggettiInRoom();

        List<String> labels = oggettiCorrenti.stream()
            .map(o -> o.getName() + " - " + o.getDescription())
            .toList();

        view.mostraOggettiInRoom(labels, idx -> {
            if (idx >= 0 && idx < oggettiCorrenti.size()) {
                onClickOggetto(oggettiCorrenti.get(idx));
            }
        });
    }

    public void mostraNpcInCurrentRoom(){
        Room currentRoom = getCurrentRoom();
        if(currentRoom.getNpcInRoom().isEmpty()){
        	NPC npcInRoom = null;
            view.mostraMessaggio("Non ci sono NPC in questa room.");
        }else{
            NPC npcInRoom = currentRoom.getNpcInRoom().get();
            aggiornaBottoniNpc(npcInRoom);
        }
       
    }


    // Metodo che verifica se i bisogni sono sotto la soglia
    private List<String> controllaStatiCritici(){
        List<String> avvisi = new ArrayList<>();
        String name = personaggio.getName();

        aggiungiAvvisoBisogno(avvisi, personaggio.getHunger(), name, " deve mangiare!", "STA PER SVENIRE DALLA FAME!");
        aggiungiAvvisoBisogno(avvisi, personaggio.getEnergy(), name, " deve dormire!", "STA PER PERDERE I SENSI!");
        aggiungiAvvisoBisogno(avvisi, personaggio.getHygiene(), name, " deve lavarsi!", "NON SI RIESCE A RESPIRARGLI VICINO!");
        aggiungiAvvisoBisogno(avvisi, personaggio.getThirst(), name, " deve bere!", "STA PER DISIDRATARSI!");
        
        return avvisi;
    }

    private void aggiungiAvvisoBisogno(List<String> avvisi, int valore, String name, String messaggioBasso, String messaggioCritico){
        if(valore < SOGLIA_CRITICA) {
            avvisi.add("ALLARME! " + name + " " + messaggioCritico);
        }else if(valore < SOGLIA_BASSA) {
            avvisi.add("Attenzione! " + name + " " + messaggioBasso);
        }
    }

    
   // METODI PER IL GIOCO
    // Metodo che mostra su schermata tutte le questi attive del personaggio
    public void getQuestiAttive(){
        Optional<Quest> quest = personaggio.getOngoingQuestWithNPC(this.npcCorrente);
        if(!quest.isEmpty()) {
        	Quest questAttiva = quest.get();
        	view.mostraQuestAttiva(questAttiva.getName(), questAttiva.getDescription());
        }
            
       
    }

    public void gestisciVittoria(){
        // Si vince nel caso in cui un personaggio riesce a finire tutte le questi
        if(contatoreQuesti == MISSIONI_TOTALI){
            view.mostraVittoria();
            gameTimer.stop();
        }
       
    }

    public void gestisciSconfitta(){
        // MainCharacter muore perché uno dei suoi bisogni è sotto la soglia
        if(isSconfitta()){
            view.mostraSconfitta();
            gameTimer.stop();
        }
    }

	public void onOpzioneScelta(String valueOf) {
		// TODO Auto-generated method stub
		
	}


}


    