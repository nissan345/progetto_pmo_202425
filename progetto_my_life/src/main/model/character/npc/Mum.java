package main.model.character.npc;


import main.model.character.MainCharacter;
import main.model.quest.ItemDeliveryCondition;
import main.model.quest.ItemUsageCondition;
import main.model.quest.Quest;
import main.model.world.House;
import main.model.world.Room;
import main.model.world.gameItem.GameItem;

public class Mum extends NPC {
	
	private final int HIGH_AFFINITY = 70;
	
	private boolean giftGiven;

    // CONSTRUCTOR ---------------------------------------------------------------------
    public Mum(Room s, House house) {
        super("Mum", s, house);
        this.giftGiven = false;
    }

    // MAIN METHODS ------------------------------------------------------------------
    
    @Override
    public String getInitialDialogue() {
        if (getAffinity() < LOW_AFFINITY) {
            return "Ciao tesoro! Mi raccomando ricordati le faccende di casa da svolgere";
        } else {
            return "Ciao tesoro! Come sta andando la tua giornata?"; 
        } 
    }

    @Override
    public String getQuestAssignedDialogue(Quest quest) {
    	if (!quest.getAssignerNPC().equals(this)) {
            return "Non so nulla di questa faccenda.";
        }
        
        if (getAffinity() < LOW_AFFINITY) {
             return "Grazie tesoro! " + quest.getDescription() + 
               "\nFai con calma, non ti stancare troppo!";
        } else {
        	return "Grazie tesoro! " + quest.getDescription() + 
                    "\nSo che posso contare su di te. Torna da me quando avrai finito!";
        }
        
    }
    
    @Override 
    public String getQuestInProgressDialogue(Quest quest) {
    	
    	if (!quest.getAssignerNPC().equals(this)) {
    		return "Non so nulla di questa faccenda.";
    	}
    	
        switch(quest.getName()) {
            case "L'album perduto":
                return "Hai trovato l'album? Guarda bene nel salotto, dovrebbe essere da qualche parte!";
            case "Visita ospiti":
                return "Sei ancora così? Vai subito a farti una doccia e cambiati d'abito, gli ospiti arriveranno a momenti!";
            case "La Citazione Mancante":
                return "Hai trovato il libro? Guarda sulla libreria.";
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
            return "Grazie per l'aiuto.";
        } else {
            return "Grazie mille! Hai fatto un ottimo lavoro!\nSei sempre così affidabile, sono orgogliosa di te!"; 
        }
    }
    
    public String checkGiftInteraction(MainCharacter character) {
        if (getAffinity() >= HIGH_AFFINITY && !giftGiven) {
            GameItem gift = createUniqueGift();

            character.getCurrentRoom().addItemRoom(gift);
            
            character.pickUpItemAction(gift);
            
            if (character.getInventory().hasItem(gift.getName())) { 
                this.giftGiven = true;
                return "Tieni, ho trovato questo vecchio videogioco in soffitta. Pensavo ti piacesse!\n" +
                       "(Hai ricevuto: Videogioco retro)";
            } else {
                character.getCurrentRoom().removeItemRoom(gift);
                return "Ho un regalo per te, ma hai le tasche piene! Torna quando hai spazio.";
            }
        }
        return "";
    }
    
    private GameItem createUniqueGift() {
    	return new GameItem.Builder("Videogioco retro", "Salotto", 10)
                .message("Un classico gioco degli anni 2000.") 
                .build();
    }

    @Override
    protected void initializeQuests() {
        
        GameItem album = findItem("Album", "Salotto");
        
        if (album != null) {
            Quest albumQuest = new Quest.Builder("L'album perduto", 
                  "Dovresti riportarmi il vecchio album di famiglia che ho perduto da qualche parte in casa e riportamelo",  
                               this)
                .xpReward(QuestDifficulty.MEDIUM.getXpReward())
                .affinityPoints(QuestDifficulty.MEDIUM.getAffinityReward())
                .addCondition(new ItemDeliveryCondition(album))
                .triggerCondition((player, room) -> room.getRoomName().equals("Salotto")) 
                .build();

            this.addQuest(albumQuest);
        }
        
        GameItem shower = findItem("Doccia", "Bagno");          
        GameItem wardrobe = findItem("Armadio", "Camera da Letto"); 
        
        if (shower != null && wardrobe != null) {
            Quest guestsQuest = new Quest.Builder("Visita ospiti", 
                    "Sta sera verrano degli ospiti. Per favore, fatti una doccia e mettiti qualcosa di elegante!", 
                    this)
                .xpReward(QuestDifficulty.MEDIUM.getXpReward())
                .affinityPoints(QuestDifficulty.MEDIUM.getAffinityReward())
                .addCondition(new ItemUsageCondition(shower))
                .addCondition(new ItemUsageCondition(wardrobe))
                .triggerCondition((player, room) -> room.getRoomName().equals("Salotto") && 
                									player.hasCompletedQuest("L'album perduto"))
                .build();
            
            this.addQuest(guestsQuest);
        }
        
        GameItem bookshelf = findItem("Libreria", "Salotto"); 
        
        if (bookshelf != null) {
            Quest bookQuest = new Quest.Builder("La Citazione Mancante", 
                    "Sto finendo di scrivere un articolo, ma non ricordo una citazione. Controlla nella libreria per me!", 
                    this)
                .xpReward(QuestDifficulty.EASY.getXpReward())
                .affinityPoints(QuestDifficulty.EASY.getAffinityReward())
                .addCondition(new ItemUsageCondition(bookshelf))
                .triggerCondition((player, room) -> room.getRoomName().equals("Salotto") && 
                									player.hasCompletedQuest("Visita ospiti"))
                .build();
            
            this.addQuest(bookQuest);
        }
    }
}