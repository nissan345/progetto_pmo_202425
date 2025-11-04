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

public class MainCharacter {
    private String name;
    private Outfit outfit;
    private Hair hair;
    private Stats stats;
	private Room currentRoom;
    private Map<Quest, Set<String>> itemUsedForQuests;
    private List<Quest> ongoingQuests;
    private List<String> usedObjects; // Traccia gli oggetti usati
 
    // CONSTRUCTOR ------------------------------------------------------------------------
    public MainCharacter(String name, Outfit outfit, Hair hair) {
        this.name = name;
        this.outfit = outfit;
        this.hair = hair;  
        this.itemUsedForQuests = new HashMap<>();
        this.stats = new Stats();
        this.currentRoom = null; // There's no room in the beginning
        this.ongoingQuests = new ArrayList<>();
        this.usedObjects = new ArrayList<>();
    }
    
    // GETTERS AND SETTERA -------------------------------------------------------------------
    public String getName() {return name;}    
    public Outfit getVestiti() { return outfit; }
    public Hair getCapelli() { return hair;}
    public Stats getStats() { return stats; }
    public String getCurrentRoom() {
        if (currentRoom != null) {
            return currentRoom.getRoomName();
        } else {
            return "Nessuna room";
        }
    }
    
    
        
    // MAIN METHODS ----------------------------------------------------------------
    public String printState() {
        StringBuilder state = new StringBuilder();
        state.append("\n STATO DI ").append(name.toUpperCase()).append("\n");
        state.append("Vestiti: ").append(outfit.getName()).append("\n");
        state.append("Capelli: ").append(hair.getName()).append("\n");
        state.append("Fame: ").append(stats.getSatiety()).append("/100\n");
        state.append("Sete: ").append(stats.getHydration()).append("/100\n");
        state.append("Energia: ").append(stats.getEnergy()).append("/100\n");
        state.append("Igiene: ").append(stats.getHygiene()).append("/100\n");
        state.append("Posizione: ").append(getCurrentRoom()).append("\n");
        
        return state.toString();
    }

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
  
 // METODI PER LE MISSIONI -----------------------------------------------------------------------


    /**
     * Adds a quest to a list of active quests
     * @param quest
     */
    public void addQuest(Quest quest) {
        if (quest != null && !ongoingQuests.contains(quest)) {
            ongoingQuests.add(quest);
            itemUsedForQuests.put(quest, new HashSet<>());
        }
    }
    
    /**
     * If a quest is completed, it removes it from the list of active quests
     * @param quest
     */
    public void removeQuest(Quest quest) {
        ongoingQuests.remove(quest);
        itemUsedForQuests.remove(quest);
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

    // METHODS TO INTERACT WITH AN OBJECT -------------------------------------------------------
    
    /**
     * registers the use of an object
     * @param itemName
     */
    public void recordItemsUsedForQuests(String itemName) {
    for (Quest q : ongoingQuests) {
        itemUsedForQuests.computeIfAbsent(q, k -> new HashSet<>()).add(itemName);
    }
}
    

    /**
     * Verifies whether an object has been used or not
     * @param nameObject
     * @param quest
     * @return
     */
    public boolean hasUsedItemForQuest(String nameObject, Quest quest) {
        return itemUsedForQuests.getOrDefault(quest, Set.of()).contains(nameObject);
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
        recordItemsUsedForQuests(nameObject);
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

    