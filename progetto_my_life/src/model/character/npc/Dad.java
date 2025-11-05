package model.character.npc;

import java.util.Arrays;
import model.character.NPC;
import model.quest.Quest;
import model.world.Room;
import model.world.gameItem.ObjectActionCondition;

public class Dad extends NPC {

    public Dad(Room s) {
        super("Dad", s);
    }
    
    @Override
    public String getInitialDialogue() {
        return "Ciao tesoro! Come sta andando la tua avventura?";
    }

    @Override
    public String getQuestAssignedDialogue(Quest quest) {
        return "Ottimo che tu voglia aiutare! " + quest.getDescription() + 
               "\nSo che posso contare su di te. Torna da me quando avrai finito!";
    }
    
    @Override 
    public String getQuestInProgressDialogue(Quest quest) {
    	switch(quest.getName()) {
    		case "Annaffia le piante":
    			return "Hai già Annaffiato le piante? Ricorda che l'Innaffiatoio si trova in giardino";
    		case "Festa a sorpresa": 
    			return "Com'è andata con i preparativi per la festa a sorpresa? Ricordati di: pulire con l'aspirapolvere, cucinare ai fornelli e mettere la musica con lo stereo!";
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
       
        Quest plantsQuest = new Quest("Innaffia le piante", 
                                   "Dovresti innaffiare le piante",
                                   this, 
                                   15, 
                                   Arrays.asList(new ObjectActionCondition("Watering can"))
                            );
        this.addQuest(plantsQuest);
        
        Quest surprisePartyQuest = new Quest("Festa a Sorpresa", 
        											  "Dovresti preparare una festa a sorpresa per la mamma, fai delle pulizie in casa e metti della musica in Salotto", 
        											  this, 
        											  30, 
        											  Arrays.asList(new ObjectActionCondition("Vacuum cleaner"), 
        													        new ObjectActionCondition("Stove"), 
        													        new ObjectActionCondition("Stereo and records"))
        		);
        this.addQuest(surprisePartyQuest);
    }
}