package main.model.character.npc;

import main.model.quest.ItemUsageCondition;
import main.model.quest.Quest;
import main.model.world.House;
import main.model.world.Room;
import main.model.world.gameItem.GameItem;

public class Dad extends NPC {

    // CONSTRUCTOR ---------------------------------------------------------------------
    public Dad(Room s, House house) {
        super("Dad", s, house);
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
                return "Hai già annaffiato le piante? Ricorda che l'annaffiatoio si trova in giardino";
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
            return "Ben fatto. Sapevo che potevi farcela se ti impegnavi.";
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
        GameItem annaffiatoio = findItem("Annaffiatoio", "Giardino");
        
        // If the item exists, create and add the quest
        if (annaffiatoio != null) {
            Quest plantsQuest = new Quest.Builder("Annaffia le piante", "Dovresti innaffiare le piante", this)
                .xpReward(50)
                .affinityPoints(20)
                .addCondition(new ItemUsageCondition(annaffiatoio))
                .triggerCondition((player, room) -> room.getRoomName().equals("Giardino")) 
                .build();

            this.addQuest(plantsQuest);
        }

        // Get the items from the other rooms
        GameItem aspirapolvere = findItem("Aspirapolvere", "Sgabuzzino"); 
        GameItem fornelli = findItem("Fornelli", "Cucina");
        GameItem stereo = findItem("Stereo", "Salotto");

        if (aspirapolvere != null && fornelli != null && stereo != null) {
            
            Quest partyQuest = new Quest.Builder("Festa a sorpresa", "Prepara la festa per la mamma!", this)
                .xpReward(80)
                .affinityPoints(50)
                .addCondition(new ItemUsageCondition(aspirapolvere))
                .addCondition(new ItemUsageCondition(fornelli))
                .addCondition(new ItemUsageCondition(stereo))
                .triggerCondition((player, room) -> room.getRoomName().equals("Giardino") &&
                									player.hasCompletedQuest("Annaffia le piante"))
                .build();

            this.addQuest(partyQuest);
        }
        
        GameItem keys = findItem("Chiavi macchina", "Cucina");
        
        // If the item exists, create and add the quest
        if (keys != null) {
            Quest plantsQuest = new Quest.Builder("Riporta le chiavi", "Potresti portarmi le chiavi della macchina", this)
                .xpReward(50)
                .affinityPoints(30)
                .addCondition(new ItemUsageCondition(annaffiatoio))
                .triggerCondition((player, room) -> room.getRoomName().equals("Giardino") &&
													player.hasCompletedQuest("Festa a sorpresa")) 
                .build();

            this.addQuest(plantsQuest);
        }
        
        
    }
}