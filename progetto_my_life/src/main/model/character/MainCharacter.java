package main.model.character;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
<<<<<<< HEAD:progetto_my_life/src/model/character/MainCharacter.java

import model.action.ActionResult;
import model.quest.Quest;
import model.world.Room;
import model.world.gameItem.GameObject;
=======
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
>>>>>>> main:progetto_my_life/src/main/model/character/MainCharacter.java

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
<<<<<<< HEAD:progetto_my_life/src/model/character/MainCharacter.java
<<<<<<< Updated upstream:progetto_my_life/src/model/character/MainCharacter.java
<<<<<<< Updated upstream:progetto_my_life/src/model/character/MainCharacter.java
    private List<String> usedObjects; // Keeps track of used objects
=======
    private List<GameItem> usedItems; // Keeps track of used Items
>>>>>>> Stashed changes:progetto_my_life/src/main/model/character/MainCharacter.java
=======
    private List<GameItem> usedItems; // Keeps track of used Items
>>>>>>> Stashed changes:progetto_my_life/src/main/model/character/MainCharacter.java
=======
    private List<String> usedItems; // Keeps track of used Items
>>>>>>> main:progetto_my_life/src/main/model/character/MainCharacter.java
 
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
<<<<<<< HEAD:progetto_my_life/src/model/character/MainCharacter.java
=======
     * @return
>>>>>>> main:progetto_my_life/src/main/model/character/MainCharacter.java
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
   
    // METODI PER LA PERSONALIZZAZIONE -------------------------------------------------------

    /* METODO PER CAMBIARE VESTITI
    public String cambiaVestiti(Vestito nuoviVestiti) {
        this.outfit = nuoviVestiti;
        return "Hai cambiato i outfit in: " + nuoviVestiti.getName();
    }
    // DA TOGLIERE
    // METODO PER CAMBIARE CAPELLI
    public String cambiaCapelli(Capelli nuoviCapelli) {
        this.hair = nuoviCapelli;
        return "Hai cambiato i hair in: " + nuoviCapelli.getName();
    }
    // HA SENSO MA NON SAPPIAMO SE SERVE
    // METODO PER MAPPARE LO STATO COMPLETO
    public Map<String, Integer> getStatoCompleto() {
        Map<String, Integer> state = new HashMap<>();
        state.put("satiety", satiety);
        state.put("hydration", hydration);
        state.put("energy", energy);
        state.put("hygiene", hygiene);
        return state;
    }
 */

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
<<<<<<< HEAD:progetto_my_life/src/model/character/MainCharacter.java
     * @param 
<<<<<<< Updated upstream:progetto_my_life/src/model/character/MainCharacter.java
     */
    // TODO

<<<<<<< Updated upstream:progetto_my_life/src/model/character/MainCharacter.java
    // METHODS TO INTERACT WITH AN OBJECT -------------------------------------------------------
=======
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
>>>>>>> main:progetto_my_life/src/main/model/character/MainCharacter.java
    
    /**
     * Registers the use of an Item for an ongoing quest
     * @param ItemName
     */
    public void recordItemsUsedForQuests(String ItemName) {
        for (Quest q : ongoingQuests) {
<<<<<<< HEAD:progetto_my_life/src/model/character/MainCharacter.java
            objectUsedForQuests.computeIfAbsent(q, k -> new HashSet<>()).add(objectName);
=======
    // METHODS TO INTERACT WITH AN ITEM -------------------------------------------------------
    
    /**
     * registers the use of an Item
     * @param item
     */
    public void recordItemsUsedForQuests(GameItem item) {
        for (Quest q : ongoingQuests) {
            ItemUsedForQuests.computeIfAbsent(q, k -> new HashSet<>()).add(item.getName());
>>>>>>> Stashed changes:progetto_my_life/src/main/model/character/MainCharacter.java
=======
            ItemUsedForQuests.computeIfAbsent(q, k -> new HashSet<>()).add(ItemName);
>>>>>>> main:progetto_my_life/src/main/model/character/MainCharacter.java
        }
    }
    
    /**
<<<<<<< HEAD:progetto_my_life/src/model/character/MainCharacter.java
<<<<<<< Updated upstream:progetto_my_life/src/model/character/MainCharacter.java
     * Verifies whether an object has been used or not
     * @param nameObject
     * @param quest
     * @return
     */
    public boolean hasUsedObjectForQuest(String nameObject, Quest quest) {
        return objectUsedForQuests.getOrDefault(quest, Set.of()).contains(nameObject);
=======
=======
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

    // METHODS TO INTERACT WITH AN ITEM -------------------------------------------------------
    
    /**
     * registers the use of an Item
     * @param item
     */
    public void recordItemsUsedForQuests(GameItem item) {
        for (Quest q : ongoingQuests) {
            ItemUsedForQuests.computeIfAbsent(q, k -> new HashSet<>()).add(item.getName());
        }
    }
    
    /**
>>>>>>> Stashed changes:progetto_my_life/src/main/model/character/MainCharacter.java
     * Verifies whether an Item has been used or not
     * @param nameItem
     * @param quest
     * @return
     */
    public boolean hasUsedItemForQuest(GameItem item, Quest quest) {
        return ItemUsedForQuests.getOrDefault(quest, Set.of()).contains(item.getName());
<<<<<<< Updated upstream:progetto_my_life/src/model/character/MainCharacter.java
>>>>>>> Stashed changes:progetto_my_life/src/main/model/character/MainCharacter.java
=======
>>>>>>> Stashed changes:progetto_my_life/src/main/model/character/MainCharacter.java
=======
     * Verifies whether an Item has been used or not for a specific quest
     * @param nameItem
     * @param quest
     * @return
     */
    public boolean hasUsedItemForQuest(String nameItem, Quest quest) {
        return ItemUsedForQuests.getOrDefault(quest, Set.of()).contains(nameItem);
>>>>>>> main:progetto_my_life/src/main/model/character/MainCharacter.java
    }
    
    /**
     * Applies the result of an action to the MainCharacter stats
     * @param result
     * @param nameItem
     * @return
     */
<<<<<<< HEAD:progetto_my_life/src/model/character/MainCharacter.java
<<<<<<< Updated upstream:progetto_my_life/src/model/character/MainCharacter.java
<<<<<<< Updated upstream:progetto_my_life/src/model/character/MainCharacter.java
    public String applyActionResult(ActionResult result, String nameObject) {

        String controlMessage = checkActionUsefulness(result);
        if (controlMessage != null) {
            return controlMessage;
        }
        recordObjectsUsedForQuests(nameObject);
=======
    public String applyActionResult(ActionResult result, GameItem item) {

        recordItemsUsedForQuests(item);
>>>>>>> Stashed changes:progetto_my_life/src/main/model/character/MainCharacter.java
=======
    public String applyActionResult(ActionResult result, GameItem item) {

        recordItemsUsedForQuests(item);
>>>>>>> Stashed changes:progetto_my_life/src/main/model/character/MainCharacter.java
=======
    public String applyActionResult(ActionResult result, String nameItem) {
        recordItemsUsedForQuests(nameItem);
>>>>>>> main:progetto_my_life/src/main/model/character/MainCharacter.java
        stats.changeEnergy(result.getDeltaEnergy());
        stats.changeHydration(result.getDeltaHydration());
        stats.changeHygiene(result.getDeltaHygiene());
        stats.changeSatiety(result.getDeltaSatiety());
        
        return result.getMessage();
    }
<<<<<<< HEAD:progetto_my_life/src/model/character/MainCharacter.java
<<<<<<< Updated upstream:progetto_my_life/src/model/character/MainCharacter.java
<<<<<<< Updated upstream:progetto_my_life/src/model/character/MainCharacter.java

    // Registra l'uso di un oggetto
    public void registerObjectUse(String nameObject) {
        if (!usedObjects.contains(nameObject)) {
            usedObjects.add(nameObject);
        }
=======
=======
>>>>>>> Stashed changes:progetto_my_life/src/main/model/character/MainCharacter.java
    
    /**
     * Interacts with a GameItem
     * @param item
     * @return
     */
    public boolean hasUsedItem(GameItem item) {
        return usedItems.contains(item);
<<<<<<< Updated upstream:progetto_my_life/src/model/character/MainCharacter.java
>>>>>>> Stashed changes:progetto_my_life/src/main/model/character/MainCharacter.java
=======
>>>>>>> Stashed changes:progetto_my_life/src/main/model/character/MainCharacter.java
=======
   
    /**
     * Interacts with a GameItem
     * @param item
     * @return
     */
    public String interact(GameItem item) {
        ActionResult result = item.use(this);
        return applyActionResult(result, item.getName());
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
>>>>>>> main:progetto_my_life/src/main/model/character/MainCharacter.java
    }
   
    /**
     * Applies the natural decay of stats over time
     */
    public void stateDecay(){
        stats.decay();
    }
<<<<<<< Updated upstream:progetto_my_life/src/model/character/MainCharacter.java
<<<<<<< Updated upstream:progetto_my_life/src/model/character/MainCharacter.java
}
<<<<<<< HEAD:progetto_my_life/src/model/character/MainCharacter.java

    
=======
}
>>>>>>> Stashed changes:progetto_my_life/src/main/model/character/MainCharacter.java
=======
}
>>>>>>> Stashed changes:progetto_my_life/src/main/model/character/MainCharacter.java
=======
>>>>>>> main:progetto_my_life/src/main/model/character/MainCharacter.java
