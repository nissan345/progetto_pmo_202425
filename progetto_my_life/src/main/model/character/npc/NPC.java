package main.model.character.npc;

import java.util.*;
import main.model.quest.CompletionCondition;
import main.model.quest.Quest;
import main.model.world.House;
import main.model.world.Room;
import main.model.world.gameItem.GameItem;

public abstract class NPC {
	
	protected final int LOW_AFFINITY = 15;
	
    private final String relationship;
    private final Room position; 
    private int affinity;
    private List<Quest> availableQuests;
    private final House house;                                      // Reference to the house so that it can see the items in other rooms

    // CONSTRUCTOR ---------------------------------------------------------------------
    public NPC(final String relationship, final Room s, House house) {
        this.relationship = relationship;
        this.position = s; 
        this.affinity = 0;
        this.availableQuests = new ArrayList<>();
        this.house = house; 
        initializeQuests();
    }

    // ABSTRACT METHODS --------------------------------------------------------------
    
    public abstract String getInitialDialogue();
    public abstract String getQuestAssignedDialogue(Quest quest);
    public abstract String getQuestInProgressDialogue(Quest quest);
    public abstract String getQuestCompletionDialogue(Quest quest);
    protected abstract void initializeQuests();
    
    // CONCRETE METHODS ------------------------------------------------------------ 

    /**
     * Adds a quest to the NPC's available quests
     * @param quest 
     */    
    protected void addQuest(Quest quest) {
        availableQuests.add(quest);
    }
    
    /**
     * Increases affinity between character and NPC (0-100 range)
     * @param affinityPoints
     */
    public void increaseAffinity(int affinityPoints) {
        this.affinity = Math.min(100, this.affinity + affinityPoints);
    }

    /**
     * Find an item in a room by its name and return it if found, otherwise return null
     * @param itemName
     * @param roomName
     * @return
     */
    protected GameItem findItem(String itemName, String roomName) {
        Room room = this.getHouse().getRoom(roomName);
        if (room == null) return null;

        return room.getItemsInRoom().stream()
            .filter(item -> item.getName().equalsIgnoreCase(itemName)) 
            .findFirst()
            .orElse(null);
    }

    // GETTERS ---------------------------------------------------------------------
    public String getRelationship() { 
        return this.relationship; 
    }
    
    public int getAffinity() { 
        return this.affinity; 
    }
    
    public Room getPosition() { 
        return this.position; 
    }

    public House getHouse() {
        return this.house;
    }

    public List<Quest> getAvailableQuests() { 
        return new ArrayList<>(this.availableQuests); 
    }
}