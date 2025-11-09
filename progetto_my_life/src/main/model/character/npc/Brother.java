package main.model.character.npc;


import java.util.Arrays;
import java.util.Collections;

import main.model.character.NPC;
import main.model.quest.Quest;
import main.model.world.Room;
import main.model.world.House;
import main.model.world.gameItem.GameItem; 

public class Brother extends NPC {
    
    public Brother(Room s, House house) {
        super("Brother", s, house);
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
        Room kitchen = this.getHouse().getRoom("Cucina");
        GameItem fornelli = kitchen.getItemsInRoom().stream()
            .filter(item -> item.getName().equals("Fornelli"))
            .findFirst()
            .orElse(null);
        
        // Crea una lista con una sola condizione usando il metodo helper
        Quest kitchenQuest = new Quest(
            "Cibo per tutti", 
            "Dei nostri amici vengono a casa, potresti prepare qualcosa per tutti mentre io pulisco la mia camera", 
            this, 
            20, 
            25, 
            Collections.singletonList(createCondition(fornelli))
        );
        
        addQuest(kitchenQuest);
    }
}