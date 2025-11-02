package main.giuseppetti.classes;

import java.util.Arrays;

import main.aboufaris.interfaces.Room;

public class Mum extends NPC {

    public Mum(Room s) {
        super("Mum", s);
    }
    
    @Override
    public String getInitialDialogue() {
        return "Ciao tesoro! Come sta andando la tua giornata?";
    }

    @Override
    public String getQuestAssignedDialogue(Quest quest) {
        return "Ottimo che tu voglia aiutare! " + quest.getDescription() + 
        	   "\nSo che posso contare su di te. Torna da me quando avrai finito!";
    }
    
    @Override 
    public String getQuestInProgressDialogue(Quest quest) {
    	switch(quest.getName()) {
    		case "L'album perduto":
    			return "Hai trovato l'album? Guarda bene nel salotto, dovrebbe essere da qualche parte!";
    		default:
    			return "Come sta andando con la quest? Torna da me quando hai finito!";
        } 
    }
    
    @Override
    public String getQuestCompletionDialogue(Quest quest) {
        return "Grazie mille! Hai fatto un ottimo lavoro." +
               "'!\nSei sempre così affidabile, sono orgoglioso di te!"; 
    }

    @Override
    protected void initializeQuests() {
        Quest albumQuest = new Quest("L'album perduto", 
                "Dovresti riportarmi il vecchio album di famiglia che ho perduto da qualche parte in casa e riportamelo",  
                                   this,
                                   15, 
                                   Arrays.asList(new ObjectActionCondition("Album"))
                            );
        this.addQuest(albumQuest);
    }
}