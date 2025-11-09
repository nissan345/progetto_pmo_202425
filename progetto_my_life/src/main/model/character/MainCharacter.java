package main.model.character;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import main.model.action.ActionResult;
import main.model.action.DropItemAction;
import main.model.action.PickItemAction;
import main.model.quest.Quest;
import main.model.world.Room;
import main.model.world.gameItem.GameItem;
import main.model.world.gameItem.Inventory;

/**
 * The MainCharacter class represents the playable character in the game,
 */

public class MainCharacter {

    // ATTRIBUTES ------------------------------------------------------------------------
    private String name;
    private Outfit outfit;
    private Hair hair;
    private Stats stats;
    private int lvl; 
    private int xp; 
    private int xpToNext; 
	private Room currentRoom;
	private Inventory inventory;
    private Map<Quest, Set<String>> ItemUsedForQuests;
    private List<Quest> ongoingQuests;
    private List<GameItem> usedItems; // Keeps track of used Items
 
    // CONSTRUCTOR ------------------------------------------------------------------------
    public MainCharacter(String name, Outfit outfit, Hair hair) {
        this.name = name;
        this.outfit = outfit;
        this.hair = hair;  
        this.ItemUsedForQuests = new HashMap<>();
        this.stats = new Stats();
        this.lvl = 1; 
        this.xp = 0;
        this.xpToNext = computeXpToNext(1);
        this.currentRoom = null; // There's no room in the beginning
        this.ongoingQuests = new ArrayList<>();
        this.usedItems = new ArrayList<>();
    }
    
    // GETTERS ---------------------------------------------------------------------------
    public String getName() {return name;}    
    public Outfit getOutfit() { return outfit; }
    public Hair getHair() { return hair;}
    public Stats getStats() { return stats; }
    public int getLvl() { return lvl; }
    public int getXp() { return xp; }
    public int getXpToNext() { return xpToNext; }
    public Room getCurrentRoom() {return currentRoom;}
    public Inventory getInventory() { return inventory; }
    
    // MAIN METHODS ----------------------------------------------------------------
    
    /**
     * Prints the current state of the MainCharacter stats.
     * @return
     */
    public String printState() {
        StringBuilder state = new StringBuilder();
        state.append("\n STATO DI ").append(name.toUpperCase()).append("\n");
        state.append("Vestiti: ").append(outfit.getName()).append("\n");
        state.append("Capelli: ").append(hair.getName()).append("\n");
        state.append("Fame: ").append(stats.getSatiety()).append("/100\n");
        state.append("Sete: ").append(stats.getHydration()).append("/100\n");
        state.append("Energia: ").append(stats.getEnergy()).append("/100\n");
        state.append("Igiene: ").append(stats.getHygiene()).append("/100\n");
         state.append("Livello: ").append(this.lvl).append("/").append("\n"); 
        state.append("XP: ").append(this.xp).append("/").append(this.xpToNext).append("\n"); 
        state.append("Posizione: ").append(getCurrentRoom()).append("\n");
        
        return state.toString();
    }

    /**
     * Sets the current room of the MainCharacter
     * @param room
     * @return
     */
    public String pickCurrentRoom(Room room) {
        if (!room.canEnter(this)) {
        List<String> reasons = room.getEntryFailureReasons(this);
        StringBuilder message = new StringBuilder("Non puoi entrare in: " + room.getRoomName() + "\n");
        for (String reason : reasons) {
            message.append("- ").append(reason).append("\n");
        }
        return message.toString();
        }
        this.currentRoom = room;
        return "Sei entrato in: " + room.getRoomName();
    }

    // LEVEL SYSTEM ----------------------------------------------------------------------------------
    
    /**
     * Creates a progressive growth of xp needed to level up
     * @param level  
     * @return
     */
    private int computeXpToNext(int level) {
        double inc = Math.pow(Math.max(0, level - 1), 1.2);
        return 100 + (int)Math.round(50 * inc);
    }

    /**
     * Adds xp to the MainCharacter
     * @param amount
     */
    public void addXp(int amount) {
        if (amount <= 0) return;
        this.xp += amount;
        while (xp >= xpToNext) {
            xp -= xpToNext;
            levelUp();
        }
    }

    /**
     * Levels up the MainCharacter.
     */
    public void levelUp() {
        this.lvl++; 
        this.xpToNext = computeXpToNext(lvl); 
    }
  
    // QUESTS SYSTEM -----------------------------------------------------------------------

    /**
     * Adds a quest to a list of active quests
     * @param quest
     */
    public void addQuest(Quest quest) {
        if (quest != null && !ongoingQuests.contains(quest)) {
            ongoingQuests.add(quest);
            ItemUsedForQuests.put(quest, new HashSet<>());
        }
    }
    
    /**
     * If a quest is completed, it removes it from the list of active quests
     * @param quest
     */
    public void removeQuest(Quest quest) {
        ongoingQuests.remove(quest);
        ItemUsedForQuests.remove(quest);
    }

    /**
     * Verifies if the MainCharacter has active quests with a specific NPC
     * @param npc
     * @return
     */
    public boolean hasActiveQuestWithNPC(NPC npc) {
        return ongoingQuests.stream()
            .anyMatch(quest -> quest.getAssignerNPC().equals(npc));
    }

    /**
     * Shows the ongoing quests with an NPC
     * @param npc
     * @return
     */
    public Optional<Quest> getActiveQuestWithNPC(NPC npc) {
        return ongoingQuests.stream()
            .filter(quest -> quest.getAssignerNPC().equals(npc))
            .findFirst();
    }

    /**
     * Automatically verifies the completion of every active quest with a specific NPC
     * @param npc
     * @return
     */
    public Optional<Quest> getCompletedQuestWithNPC(NPC npc) {
	    return ongoingQuests.stream()
        .filter(q -> q.getAssignerNPC().equals(npc))
        .filter(q -> q.checkCompletion(this))
        .findFirst();
	}

    /**
     * Automatically verifies the completion of every active quest with a specific NPC
     * @param npc
     * @return
     */
    public List<Quest> getOngoingQuestsWithNPC(NPC npc) {
        List<Quest> questsWithNPC = new ArrayList<>();
        for (Quest q : ongoingQuests) {
            if (q.getAssignerNPC().equals(npc)) {
                questsWithNPC.add(q);
            }
        }
        return questsWithNPC;
    }

    // METHODS TO INTERACT WITH ITEMS -------------------------------------------------------
    
    /**
     * Registers the use of an Item for an ongoing quest
     * @param ItemName
     */
    public void recordItemsUsedForQuests(GameItem item) {
        for (Quest q : ongoingQuests) {
            ItemUsedForQuests.computeIfAbsent(q, k -> new HashSet<>()).add(item.getName());
        }
    }
    
    /**
     * Verifies whether an Item has been used or not for any ongoing quest
     * @param item
     * @return
     */
    public boolean hasUsedItemForQuest(GameItem item) {
        return ongoingQuests.stream()
            .anyMatch(quest -> 
                ItemUsedForQuests.getOrDefault(quest, Set.of()).contains(item.getName())
            );
    }
    
    /**
     * Applies the result of an action to the MainCharacter stats
     * @param result
     * @param nameItem
     * @return
     */
    public String applyActionResult(ActionResult result, GameItem item) {

        recordItemsUsedForQuests(item);
        stats.changeEnergy(result.getDeltaEnergy());
        stats.changeHydration(result.getDeltaHydration());
        stats.changeHygiene(result.getDeltaHygiene());
        stats.changeSatiety(result.getDeltaSatiety());
        
        return result.getMessage();
    }
   
    
    /**
     * Picks up a GameItem and puts in the inventory
     * @param item
     * @return
     */
    public ActionResult pickUp(GameItem item) {
        return new PickItemAction().execute(this, item);
    }

    /**
     * Removes a GameItem from the inventory
     * @param item
     * @return
     */
    public ActionResult drop(GameItem item) {
        return new DropItemAction().execute(this, item);
    }
   
    /**
     * Applies the natural decay of stats over time
     */
    public void stateDecay() {
    	stats.decay();
    }
}