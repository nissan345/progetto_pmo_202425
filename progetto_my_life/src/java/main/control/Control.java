package main.control;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.swing.Timer;
import main.model.character.*;
import main.model.character.npc.*;
import main.model.quest.*;
import main.model.world.*;
import main.model.world.factory.*;
import main.model.world.gameItem.GameItem;
import main.view.View;

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
        Room bagno = ItemFactory.createBathroom();
        Room camera = ItemFactory.createBedroom();
        Room cucina = ItemFactory.createKitchen();
        Room salotto = ItemFactory.createLivingRoom();
        Room giardino = ItemFactory.createGarden();
        Room sgabuzzino = ItemFactory.createStorageRoom();
        
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
        mostraNpcInCurrentRoom();
    }

     //(Kind of))
    public void onClickEsci(String nameRoom){
        casa.exitRoom();
        view.mostraCasa(); // tornare nel menu principale
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

    
  

    public void gestisciVittoria(){
        // Si vince nel caso in cui un character riesce a finire tutte le questi
        if(contatoreQuesti == MISSIONI_TOTALI){
       
            gameTimer.stop();
        }
       
    }

    public void gestisciSconfitta(){
        // MainCharacter muore perché uno dei suoi bisogni è sotto la soglia
        if(isSconfitta()){
         
            gameTimer.stop();
        }
    }

	public void onOpzioneScelta(String valueOf) {
		// TODO Auto-generated method stub
		
	}


}


    