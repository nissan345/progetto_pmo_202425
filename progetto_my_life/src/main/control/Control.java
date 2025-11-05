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
import model.character.Hair;
import model.character.MainCharacter;
import model.character.NPC;
import model.character.Outfit;
import model.character.npc.Brother;
import model.character.npc.Dad;
import model.character.npc.Mum;
import model.quest.InteractionOption;
import model.quest.Quest;
import model.world.House;
import model.world.Room;
import model.world.factory.FabbricaOggetti;
import model.world.gameItem.GameItem;

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
    private MainCharacter character;
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
        view.mostraStatistiche(character.printState());
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
    private void creaMainCharacterPersonalizzato() {
        String name = view.chiediNameMainCharacter();
        Outfit outfit = scegliOpzioneDaEnum("Scegli i outfit", Outfit.values());
        Hair hair = scegliOpzioneDaEnum("Scegli i hair", Hair.values());
        this.character = new MainCharacter(name, outfit, hair);
    }
    
    // FUNZIONA
    private <T> T scegliOpzioneDaEnum(String message, T[] opzioniDisponibili){
        List<String> opzioni = Arrays.stream(opzioniDisponibili)
            .map(Item::toString)
            .toList();
        int scelta = view.mostraOpzioniPersonalizzazione(message, opzioni);
        return opzioniDisponibili[scelta];
    }
    
    // FUNZIONA
    // Metodo che gestisce il timer, serve per il decadimento dei bisogni
    private void avviaTimerBisogni(){
        gameTimer = new Timer(DECADIMENTO_STATO, e-> {
                character.stateDecay();
                List<String> avvisi = controllaStatiCritici();
                if(!avvisi.isEmpty()){
                    for(String a : avvisi){
                        view.mostraAvviso(a); 
                    }
                }
                view.mostraStatistiche(this.character.printState());
                gestisciSconfitta();
            
        });
        gameTimer.start();
    }
    
    public void aggiornaBottoniNpc(NPC npc) {
        // qui decidi quali label mostrare
        String name = npc.getRelationship();
        String relazione = npc.getRelationship();

        // passi SOLO stringhe + le azioni da eseguire
        
    }
    
    // Mezzo funziona, devo sistemare che se si esce dalla room si toglie il bottone per parlare con NPC
    public void onClickNpc(NPC n){
    	this.npcCorrente = n;
        view.mostraMessaggio(n.getInitialDialogue());
    }
    public void onSecondClickNpc(NPC n) {
        this.npcCorrente = n;

        List<InteractionOption> opzioni = n.getAvailableOptions(character);
        if (opzioni == null || opzioni.isEmpty()) {
            view.mostraMessaggio("Non ci sono opzioni di interazione.");
            return;
        }

        // Etichette leggibili (se non ti interessa la bella label, usa op::toString)
        List<String> labels = opzioni.stream()
                .map(this::labelPer)   // vedi metodo sotto
                .toList();

        int idx = view.mostraOpzioniIndice(
                "Interazione con " + n.getRelationship(),
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
                    view.mostraMessaggio("Nuova quest: " + m.getName() + "\n" + m.getDescription());
                } else {
                    view.mostraMessaggio("Non ci sono questi disponibili al momento.");
                }
            }
            case QUEST_IN_PROGRESS -> {
                Quest m = character.getMissioneAttivaConNPC(npcCorrente).get();
                if (m != null) {
                	view.mostraMessaggio(npcCorrente.getQuestInProgressDialogue(m));
                } else {
                	view.mostraMessaggio("Non ci sono questi attive disponibili");
                }
            }
            case TURN_IN_QUEST -> {
                List<String> msgs = npcCorrente.consegnaMissione(character);
                boolean completata = msgs.stream().anyMatch(t -> t.contains("' completata!"));
                if (completata) { contatoreQuesti++; gestisciVittoria(); }
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

    public MainCharacter getMainCharacter(){
      return character;
    }

    // DA VEDERE
    private boolean isSconfitta(){
        // MainCharacter muore perché uno dei suoi bisogni è sotto la soglia
        return character.getEnergy() == 0 || character.getSatiety() ==0 || character.getHygiene() == 0 
        || character.getSatiety() == 0;
    }

    // Funziona
     public void onClickEntra(String nameRoom){
        Optional<Room> ris = casa.enterRoom(nameRoom);
        if(ris.isEmpty()){
            view.mostraErrore("Room non trovata!");
            return;
        }
        // Aggiornare la posizione del character, affinché possa usare un oggetto
        character.pickCurrentRoom(ris.get());
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
    public void onClickOggetto(GameItem oggettoGioco){
    	Room corrente = getCurrentRoom();
        if (!corrente.hasOggettoRoom(oggettoGioco)) {
            view.mostraErrore("L'oggetto non si trova in room!");
            return;
        }

        if (!oggettoGioco.richiedeScelta()) {
            // caso semplice
            var ra = oggettoGioco.usa(character);
            String msg = character.interagisci(oggettoGioco);
            view.mostraMessaggio(msg);
            view.mostraStatistiche(character.printState());
            return;
        }

        // caso con scelta: prima message poi lista opzioni
        var intro = oggettoGioco.usa(character); 
        if (intro != null && intro.getMessaggio() != null && !intro.getMessaggio().isEmpty()) {
            view.mostraMessaggio(intro.getMessaggio());
        }

        var opzioni = oggettoGioco.opzioniDisponibili(character);
        Item scelta = view.mostraDialogSceltaGenerica("Scegli un'opzione","Azioni disponibili:", opzioni);
        if (scelta != null) {
            var ra = oggettoGioco.usa(character, scelta);
            character.applicaActionResult(ra, oggettoGioco.getName());
            view.mostraStatistiche(character.printState());
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
        List<GameItem> oggettiCorrenti = room.getOggettiInRoom();

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
        String name = character.getName();

        aggiungiAvvisoBisogno(avvisi, character.getSatiety(), name, " deve mangiare!", "STA PER SVENIRE DALLA FAME!");
        aggiungiAvvisoBisogno(avvisi, character.getEnergy(), name, " deve dormire!", "STA PER PERDERE I SENSI!");
        aggiungiAvvisoBisogno(avvisi, character.getHygiene(), name, " deve lavarsi!", "NON SI RIESCE A RESPIRARGLI VICINO!");
        aggiungiAvvisoBisogno(avvisi, character.getSatiety(), name, " deve bere!", "STA PER DISIDRATARSI!");
        
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
    // Metodo che mostra su schermata tutte le questi attive del character
    public void getQuestiAttive(){
        Optional<Quest> quest = character.getOngoingQuestWithNPC(this.npcCorrente);
        if(!quest.isEmpty()) {
        	Quest questAttiva = quest.get();
        	view.mostraQuestAttiva(questAttiva.getName(), questAttiva.getDescription());
        }
            
       
    }

    public void gestisciVittoria(){
        // Si vince nel caso in cui un character riesce a finire tutte le questi
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


    