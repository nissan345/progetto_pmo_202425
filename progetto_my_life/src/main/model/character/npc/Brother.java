package main.model.character.npc;

import java.util.Arrays;
import main.model.character.NPC;
import main.model.quest.CompletionCondition;
import main.model.quest.Quest;
import main.model.world.Room; 

public class Brother extends NPC {
    
    public Brother(Room s) {
        super("Brother", s);
    }
    
    @Override
    public String getInitialDialogue() {
        return "Non mi dare fastidio";
    }

    @Override
    public String getQuestAssignedDialogue(Quest quest) {
        return "Ehii " + quest.getDescription() + ", non dirlo a mamma e papà mi raccomando";
    }
    
    @Override 
    public String getQuestInProgressDialogue(Quest quest) {
    	switch(quest.getName()) {
    	case "Cibo per tutti":
    		return "Stai preparando da mangiare? Ricorda di usare i fornelli!"; 
    	default:
    		return "Come sta andando con la quest? Torna da me quando hai finito!";
    } 
    }

    @Override
    public String getQuestCompletionDialogue(Quest quest) {
        return "Grazie mille per avermi aiutato";
    }

    @Override
    protected void initializeQuests() {
        Quest kitchenQuest = new Quest("Cibo per tutti", 
                				   "Dei nostri amici vengono a casa, potresti prepare qualcosa per tutti mentre io pulisco la mia camera", 
                                   this, 
                                   20,
                                   25, 
                                   Arrays.asList(new CompletionCondition("Fornelli"))
                            );
        this.addQuest(kitchenQuest);
    }
}