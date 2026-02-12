package main.model.character.npc;

import main.model.character.npc.NPC.QuestDifficulty;
import main.model.quest.ItemDeliveryCondition;
import main.model.quest.ItemUsageCondition;
import main.model.quest.Quest;
import main.model.world.House;
import main.model.world.Room;
import main.model.world.gameItem.GameItem;

public class Dad extends NPC {

    // CONSTRUCTOR ---------------------------------------------------------------------
    public Dad(Room s, House house) {
        super("Papà", s, house);
    }
    
    // MAIN METHODS ------------------------------------------------------------------
    
    @Override
    public String getInitialDialogue() {
        if (getAffinity() < LOW_AFFINITY) {
            return "Mmh... tutto bene? Cerca di non combinare guai oggi.";
        } else {
            return "Ciao tesoro! Come sta andando la tua avventura?"; 
        } 
    }

    @Override
    public String getQuestAssignedDialogue(Quest quest) {
    	if (!quest.getAssignerNPC().equals(this)) {
            return "Non so nulla di questa faccenda.";
        }
        if (getAffinity() < LOW_AFFINITY) {
            return "Ho bisogno di una mano: " + quest.getDescription() + ". Cerca di farlo bene, d'accordo?";
        } else {
            return "Ottimo che tu voglia aiutare! " + quest.getDescription() + 
                   "\nSo che posso contare su di te. Torna da me quando avrai finito!";
        }
    }
    
    @Override 
    public String getQuestInProgressDialogue(Quest quest) {
    	
    	if (!quest.getAssignerNPC().equals(this)) {
    		return "Non so nulla di questa faccenda.";
    	}
        switch(quest.getName()) {
            case "Annaffia le piante":
                return "Hai già annaffiato le piante? Ricorda che l'annaffiatoio si trova in giardino.";
            case "Festa a sorpresa": 
                return "Com'è andata con i preparativi per la festa a sorpresa? Ricordati di: pulire con l'aspirapolvere, cucinare ai fornelli e mettere la musica con lo stereo!";
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
            return "Ben fatto. Sapevo che ce l'avresti fatta.";
        } else {
            return "Grazie mille! Hai fatto un ottimo lavoro.\nSei sempre così affidabile, sono orgoglioso di te!";
        }
    }

    /**
     * Initializes the quests for the NPC
     */
    @Override
    protected void initializeQuests() {
        // Get the item from the garden
        GameItem wateringCan = findItem("Annaffiatoio", "Giardino");
        
        // If the item exists, create and add the quest
        if (wateringCan != null) {
            Quest plantsQuest = new Quest.Builder("Annaffia le piante", "Dovresti annaffiare le piante.", this)
                .xpReward(QuestDifficulty.EASY.getXpReward())
                .affinityPoints(QuestDifficulty.EASY.getAffinityReward())
                .addCondition(new ItemUsageCondition(wateringCan))
                .triggerCondition((character, room) -> room.getRoomName().equals("Giardino")) 
                .build();

            this.addQuest(plantsQuest);
        }

        // Get the items from the other rooms
        GameItem vacuum = findItem("Aspirapolvere", "Sgabuzzino"); 
        GameItem stove = findItem("Fornelli", "Cucina");
        GameItem stereo = findItem("Stereo", "Salotto");

        if (vacuum != null && stove != null && stereo != null) {
            
            Quest partyQuest = new Quest.Builder("Festa a sorpresa", "Prepara la festa per la mamma!", this)
                .xpReward(QuestDifficulty.HARD.getXpReward())
                .affinityPoints(QuestDifficulty.HARD.getAffinityReward())
                .addCondition(new ItemUsageCondition(vacuum))
                .addCondition(new ItemUsageCondition(stove))
                .addCondition(new ItemUsageCondition(stereo))
                .triggerCondition((character, room) -> room.getRoomName().equals("Giardino") &&
                									   character.getJustEntered() &&
                									   character.hasCompletedQuest("Annaffia le piante"))
                .build();

            this.addQuest(partyQuest);
        }
        
        GameItem keys = findItem("Chiavi macchina", "Cucina");
        
        // If the item exists, create and add the quest
        if (keys != null) {
            Quest plantsQuest = new Quest.Builder("Riporta le chiavi", "Potresti portarmi le chiavi della macchina.", this)
                .xpReward(QuestDifficulty.MEDIUM.getXpReward())
                .affinityPoints(QuestDifficulty.MEDIUM.getAffinityReward())
                .addCondition(new ItemDeliveryCondition (keys.getName()))
                .triggerCondition((character, room) -> room.getRoomName().equals("Giardino") &&
                									   character.getJustEntered() &&
													   character.hasCompletedQuest("Festa a sorpresa")) 
                .build();

            this.addQuest(plantsQuest);
        }
        
        
    }
}