package model.character;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;

import model.quest.Quest;
import model.world.Room;

public abstract class NPC {
    private final String relationship;
    private final Room position; 
    private int affinity;
    private List<Quest> availableQuests;

    //private List<InteractionOption> options;
    private BiPredicate<MainCharacter, Room> triggerCondition;


    public NPC(final String relationship, final Room s) {
        this.relationship = relationship;
        this.position = s; 
        this.affinity = 0;
        this.availableQuests = new ArrayList<>();

       // this.options = new ArrayList<>();
       this.triggerCondition = (character, room) -> false; // Default: no trigger
        initializeQuests();
    }

    // ABSTRACT METHODS --------------------------------------------------------------
    
    /**
     * Adds a quest to the NPC's available quests
     */  
    public abstract String getInitialDialogue();
    
    /**
     * Dialogue when NPC assigns a quest
     * @param quest 
     */  
    public abstract String getQuestAssignedDialogue(Quest quest);
    
    /**
     * Dialogue when quest is still in progress
     * @param quest 
     */  
    public abstract String getQuestInProgressDialogue(Quest quest);
    
    /**
     * Dialogue when quest is completed
     * @param quest 
     */  
    public abstract String getQuestCompletionDialogue(Quest quest);
    
    /**
     * Quest initialization method
     */ 
    protected abstract void initializeQuests();
    
    // CONCRETE METHODS ------------------------------------------------------------ 
    
    // DA ELIMINARE SE AUTOMATIZZIAMO LE QUEST
    // Handles character-NPC interaction
    /*
     * 
    public List<InteractionOption> getAvailableOptions(MainCharacter character) {
        this.options.clear();

        Optional<Quest> completedQuest = character.getCompletedQuestWithNPC(this);
        Optional<Quest> activeQuest = character.getActiveQuestWithNPC(this);

        if (completedQuest.isPresent()) {
            this.options.add(InteractionOption.TURN_IN_QUEST);

        } else if (activeQuest.isPresent()) {
            this.options.add(InteractionOption.QUEST_IN_PROGRESS);

        } else if (!availableQuests.isEmpty()) {
            this.options.add(InteractionOption.REQUEST_QUEST);
        }

        this.options.add(InteractionOption.EXIT);
        return this.options;
    }
    
    public Quest assignQuest(MainCharacter character) {
    	if(character.hasActiveQuestWithNPC(this) || availableQuests.isEmpty()) {
    		return null;
    	}
    	Quest quest = availableQuests.remove(0);
    	character.addQuest(quest);
    	return quest;
    }

    public List<String> turnInQuest(MainCharacter character) {
        List<String> messages = new ArrayList<>();
        Optional<Quest> completedQuest = character.getCompletedQuestWithNPC(this);

        if (completedQuest.isEmpty()) {
            messages.add("Nessuna quest completata con " + this.relationship);
            return messages;
        }
        
        Quest quest = completedQuest.get();
        increaseAffinity(quest.getAffinityPoints());
        character.removeQuest(quest);
        
        messages.add("Quest '" + quest.getName() + "' completata!");
        messages.add("affinità con " + this.relationship + ": " + this.affinity + "/100");
        return messages;
    }
     */

    /**
     * Adds a quest to the NPC's available quests
     * @param quest 
     */    
    protected void addQuest(Quest quest) {
        availableQuests.add(quest);
    }

    /**
     * Sets the trigger condition for offering quests
     * @param condition 
     */
    public BiPredicate<MainCharacter, Room> getTriggerCondition() {
        return this.triggerCondition;
    }

    //  QUEST COMPLETION REWARD METHOD --------------------------------------------------------

    /**
     * Rewards the character for completing a quest
     * @param quest 
     * @param character
     */
    public void rewardFor(Quest quest, MainCharacter character) {
        increaseAffinity(quest.getAffinityPoints());
        
    }

    /**
     * Increases affinity between character and NPC (0-100 range)
     * @param affinityPoints
     */
    protected void increaseAffinity(int affinityPoints) {
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

    public List<Quest> getAvailableQuests() { 
        return new ArrayList<>(this.availableQuests); 
    }
    
}
