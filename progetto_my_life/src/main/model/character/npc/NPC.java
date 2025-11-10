package main.model.character.npc;

import java.util.*;
import java.util.function.BiPredicate;

import main.model.character.MainCharacter;
import main.model.quest.CompletionCondition;
import main.model.quest.Quest;
import main.model.world.House;
import main.model.world.Room;
import main.model.world.gameItem.GameItem;

public abstract class NPC {
    private final String relationship;
    private final Room position; 
    private int affinity;
    private List<Quest> availableQuests;
    private BiPredicate<MainCharacter, Room> triggerCondition;
    private final House house;                                      // Reference to the house so that it can see the items in other rooms

    // CONSTRUCTOR ---------------------------------------------------------------------
    public NPC(final String relationship, final Room s, House house) {
        this.relationship = relationship;
        this.position = s; 
        this.affinity = 0;
        this.availableQuests = new ArrayList<>();
        this.triggerCondition = (character, room) -> false;
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
     * Creates a single completion condition for an item
     * @param item
     * @return
     */
    protected CompletionCondition createCondition(GameItem item) {
        return new CompletionCondition(item);
    }

    /**
     * Adds a quest to the NPC's available quests
     * @param quest 
     */    
    protected void addQuest(Quest quest) {
        availableQuests.add(quest);
    }

    /**
     * Sets the trigger condition for the NPC
     * @param condition 
     * @return
     */
    public BiPredicate<MainCharacter, Room> getTriggerCondition() {
        return this.triggerCondition;
    }

    /**
     * Increases affinity between character and NPC (0-100 range)
     * @param affinityPoints
     */
    public void increaseAffinity(int affinityPoints) {
        this.affinity = Math.min(100, this.affinity + affinityPoints);
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