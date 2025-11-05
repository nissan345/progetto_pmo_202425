package model.character;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import model.action.ActionResult;
import model.quest.Quest;
import model.world.Room;
import model.world.gameItem.GameObject;

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
    private Map<Quest, Set<String>> objectUsedForQuests;
    private List<Quest> ongoingQuests;
    private List<String> usedObjects; // Keeps track of used objects
 
    // CONSTRUCTOR ------------------------------------------------------------------------
    public MainCharacter(String name, Outfit outfit, Hair hair) {
        this.name = name;
        this.outfit = outfit;
        this.hair = hair;  
        this.objectUsedForQuests = new HashMap<>();
        this.stats = new Stats();
        this.lvl = 1; 
        this.xp = 0;
        this.xpToNext = computeXpToNext(1);
        this.currentRoom = null; // There's no room in the beginning
        this.ongoingQuests = new ArrayList<>();
        this.usedObjects = new ArrayList<>();
    }
    
    // GETTERS AND SETTER -------------------------------------------------------------------
    public String getName() {return name;}    
    public Outfit getOutfit() { return outfit; }
    public Hair getHair() { return hair;}
    public Stats getStats() { return stats; }
    public int getLvl() { return lvl; }
    public int getXp() { return xp; }
    public int getXpToNext() { return xpToNext; }
    public String getCurrentRoom() {
        if (currentRoom != null) {
            return currentRoom.getRoomName();
        } else {
            return "Nessuna room";
        }
    }
    
    // MAIN METHODS ----------------------------------------------------------------
    
    /**
     * Prints the current state of the MainCharacter stats.
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
            objectUsedForQuests.put(quest, new HashSet<>());
        }
    }
    
    /**
     * If a quest is completed, it removes it from the list of active quests
     * @param quest
     */
    public void removeQuest(Quest quest) {
        ongoingQuests.remove(quest);
        objectUsedForQuests.remove(quest);
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
     * @param 
     */
    // TODO

    // METHODS TO INTERACT WITH AN OBJECT -------------------------------------------------------
    
    /**
     * registers the use of an object
     * @param objectName
     */
    public void recordObjectsUsedForQuests(String objectName) {
        for (Quest q : ongoingQuests) {
            objectUsedForQuests.computeIfAbsent(q, k -> new HashSet<>()).add(objectName);
        }
    }
    
    /**
     * Verifies whether an object has been used or not
     * @param nameObject
     * @param quest
     * @return
     */
    public boolean hasUsedObjectForQuest(String nameObject, Quest quest) {
        return objectUsedForQuests.getOrDefault(quest, Set.of()).contains(nameObject);
    }
    
    /**
     * Performs an action
     * @param result
     * @param nameObject
     * @return
     */
    public String applyActionResult(ActionResult result, String nameObject) {

        String controlMessage = checkActionUsefulness(result);
        if (controlMessage != null) {
            return controlMessage;
        }
        recordObjectsUsedForQuests(nameObject);
        stats.changeEnergy(result.getDeltaEnergy());
        stats.changeHydration(result.getDeltaHydration());
        stats.changeHygiene(result.getDeltaHygiene());
        stats.changeSatiety(result.getDeltaSatiety());
        
        return result.getMessage();
    }

    // Registra l'uso di un oggetto
    public void registerObjectUse(String nameObject) {
        if (!usedObjects.contains(nameObject)) {
            usedObjects.add(nameObject);
        }
    }
    
    // Verifica se un oggetto è state usato
    public boolean hasUsedObject(String nameObject) {
        return usedObjects.contains(nameObject);
    }
    
    
    public String interact(GameObject oggetto) {
        ActionResult result = oggetto.use(this);
        return applyActionResult(result, oggetto.getName());
    }
    
    // dovrebbe farlo requirements
    private String checkActionUsefulness(ActionResult result) {
    	
        if (result.getDeltaEnergy() > 0 && stats.getEnergy() >= 100) {
            return "Sei già pieno di energy, non ha senso riposare ora!";
        }else if (result.getDeltaSatiety() < 0 && stats.getSatiety() <= 0) {
            return "Non hai satiety, non ha senso mangiare ora!";
        }else if (result.getDeltaHydration() < 0 && stats.getHydration() <= 0) {
            return "Non hai hydration, non ha senso bere ora!";
        }else if (result.getDeltaHygiene() > 0 && stats.getHygiene() >= 100) {
            return "Sei già pulitissimo, non serve lavarti!";
        }else {
        	return null;
        }
    }
    
    @Deprecated public int getSatiety() { return stats.getSatiety(); }
    @Deprecated public int getHydration() { return stats.getHydration(); }
    @Deprecated public int getEnergy() { return stats.getEnergy(); }
    @Deprecated public int getHygiene() { return stats.getHygiene(); }
    public void stateDecay(){
        stats.decay();
    }
}

    