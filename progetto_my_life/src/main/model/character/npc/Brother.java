package main.model.character.npc;

import main.model.quest.ItemDeliveryCondition;
import main.model.quest.ItemUsageCondition;
import main.model.quest.Quest;
import main.model.world.House;
import main.model.world.Room; 
import main.model.world.gameItem.GameItem;

public class Brother extends NPC {
	
    // CONSTRUCTOR ---------------------------------------------------------------------
    public Brother(Room s, House house) {
        super("Brother", s, house);
    }
    
    // MAIN METHODS ------------------------------------------------------------------

    @Override
    public String getInitialDialogue() {
        if (getAffinity() < LOW_AFFINITY) {
            return "Non mi dare fastidio, ho da fare."; 
        } else {
            return "Ehi. Ti serve qualcosa?";
        } 
    }

    @Override
    public String getQuestAssignedDialogue(Quest quest) {
    	if (!quest.getAssignerNPC().equals(this)) {
            return "Non so nulla di questa faccenda.";
        }
        if (getAffinity() < LOW_AFFINITY) {
            return "Senti, mi serve una mano: " + quest.getDescription() + " Fai in fretta";
        } else {
            return "Ehii, mi faresti un favore? " + quest.getDescription();
        }
    }
    
    @Override 
    public String getQuestInProgressDialogue(Quest quest) {
    	
    	if (!quest.getAssignerNPC().equals(this)) {
    		return "Non so nulla di questa faccenda.";
    	}
    	
    	switch(quest.getName()) {
    	case "Cibo per tutti":
    		return "Stai preparando da mangiare? Ricorda di usare i fornelli!";
    	case "Lava vestiti":
    		return "Hai messo i panni in lavatrice? Ricorda di usare la lavatrice!"; 
    	case "Videogioco retro":
    		return "Hai trovato il mio videogioco? Prova a chiedere alla mamma";
    	default:
    		return "Come sta andando con la quest? Torna da me quando hai finito!";
    	} 
    }

    @Override
    public String getQuestCompletionDialogue(Quest quest) {
    	if (!quest.getAssignerNPC().equals(this)) {
            return "Non so nulla di questa faccenda.";
        }
        if (getAffinity() < LOW_AFFINITY) {
            return "Era ora. Grazie.";
        } else {
            return "Grazie mille per avermi aiutato, mi hai salvato.";
        } 
    }

    @Override
    protected void initializeQuests() {
        // Get the item from the kitchen room
        GameItem stove = findItem("Fornelli", "Cucina");
        
        // If the item exists, create and add the quest
        if (stove != null) {
            Quest kitchenQuest = new Quest.Builder("Cibo per tutti", 
                    "Dei nostri amici vengono a casa, potresti prepare qualcosa per tutti mentre io pulisco la mia camera",  
                                this)
                .xpReward(50)
                .affinityPoints(20)
                .addCondition(new ItemUsageCondition(stove))
                .triggerCondition((player, room) -> room.getRoomName().equals("Cucina")) 
                .build();

            addQuest(kitchenQuest);
        }
        
        GameItem washingMachine = findItem("Lavatrice", "Bagno");
        
        // If the item exists, create and add the quest
        if (washingMachine != null) {
            Quest washingQuest = new Quest.Builder("Lava vestiti", 
                    "Potresti mettere i miei vestiti nella lavatrice?",  
                                this)
                .xpReward(55)
                .affinityPoints(25)
                .addCondition(new ItemUsageCondition(washingMachine))
                .triggerCondition((player, room) -> room.getRoomName().equals("Cucina") &&
                									player.hasCompletedQuest("Cibo per turri"))
                									
                .build();

            addQuest(washingQuest);
        } 
        
        GameItem videogame = findItem("Videogioco retro", "Salotto");
        
        Quest videogameQuest = new Quest.Builder("Videogioco retro", 
                    "Potresti trovare il mio vecchio videogioco Shenmue II, devo finire la mia collezione di giochi retro",  
                                this)
                .xpReward(250)
                .affinityPoints(50)
                .addCondition(new ItemDeliveryCondition(videogame))
                .triggerCondition((player, room) -> room.getRoomName().equals("Cucina") &&
                									player.hasCompletedQuest("Lava vestiti"))
                									
                .build();

            addQuest(videogameQuest);
    }
}