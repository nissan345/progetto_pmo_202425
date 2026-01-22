package main.model.quest;

import java.util.*;
import java.util.function.BiPredicate;
import main.model.character.MainCharacter;
import main.model.character.npc.NPC;
import main.model.world.Room;


/**
 * The QuestSystem class manages the registration, triggering, and completion of quests in the game.
 */

public class QuestSystem {
    // ATTRIBUTES ---------------------------------------------------------------------------
    private final List<Quest> catalog = new ArrayList<>();                                  // All registered quests
    private final Set<Quest> offered = new HashSet<>();                                     // Quests already offered to the character
    private final Set<Quest> turnedIn = new HashSet<>();                                    // Quests already turned in by the character

    // MAIN METHODS ----------------------------------------------------------------
    
/**
     * Registers an NPC's quests into the quest system
     * @param npc
     */
    public void registerNPC(NPC npc) {
        for (Quest quest : npc.getAvailableQuests()) {
            if (!catalog.contains(quest)) {
                catalog.add(quest);
            }
        }
    }
/**
     * Checks whether any quest is triggered when the character enters a room
     * Returns the list of newly offered quests 
     * @param character
     * @param room
     */
    public List<Quest> onPlayerEnteredRoom(MainCharacter character, Room room){
    	
    	if (character.getCurrentRoom() == null || !character.getCurrentRoom().equals(room)) {
            return new ArrayList<>();
        }
    	
        List<Quest> newlyOffered = new ArrayList<>();

        // Iterate through all quests in the catalog
        for (Quest quest : catalog) {
            
            // If the quest has already been turned in, skip it
            if (turnedIn.contains(quest)) {
                continue;
            }

            // If already offered and not completed, add to the return list (as an active reminder)
            if (offered.contains(quest) && !quest.isCompleted()) {
                newlyOffered.add(quest);
                continue; // Move to the next quest, no need to re-check the trigger
            }

            // If the quest hasn't been offered yet, check its trigger condition
            if (!offered.contains(quest)) {
                // Retrieve the trigger directly from the quest object
                BiPredicate<MainCharacter, Room> trigger = quest.getTriggerCondition();
                
                boolean shouldOffer = false;
                
                // Safety check: ensure the trigger is not null (defaults should handle this, but better safe)
                if (trigger != null) {
                    try {
                        shouldOffer = trigger.test(character, room);
                    } catch (RuntimeException ex) {
                        shouldOffer = false; // If the trigger execution fails, do not offer the quest
                        System.err.println("Error in quest trigger " + quest.getName() + ": " + ex.getMessage());
                    }
                }

                if (shouldOffer) {
                    offered.add(quest);           // Mark as offered
                    newlyOffered.add(quest);      // Add to the list to return
                    if (character != null) {
                        character.addQuest(quest);   // Assign to the character
                    }
                }
            }
        }
        return newlyOffered;
    }

    /**
     * Finds a completed quest assigned by the given NPC
     * @param character
     * @param npc
     */
    public Optional<Quest> findCompletedQuestForNPC(MainCharacter character, NPC npc) {
        if (character != null && npc != null) {
            return character.getCompletedQuestWithNPC(npc);
        }
        return Optional.empty();
    }

    /**
     * Turns in a completed quest to the given NPC
     * @param character
     * @param npc
     */
    public boolean tryTurnIn(MainCharacter character, NPC npc) {
        Optional<Quest> completed = findCompletedQuestForNPC(character, npc);
        if (completed.isPresent()) {
            Quest quest = completed.get();

            // Apply the reward
            quest.applyReward(character);
            quest.resetItems(character);

            // Mark the quest as turned in and remove it from the character's active quests
            turnedIn.add(quest);
            offered.remove(quest);
            character.removeQuest(quest);
            return true;
        }
        return false;
    }

    /**
     * Resets the quest system (for testing purposes)
     */
    public void reset() {
        catalog.clear();
        offered.clear();
        turnedIn.clear();
    }

    /**
     * Checks if a quest has been offered
     * @param quest
     */
    public boolean isOffered(Quest quest) {
        return offered.contains(quest);
    }

    /**
     * Checks if a quest has been turned in
     * @param quest
     */
    public boolean isTurnedIn(Quest quest) {
        return turnedIn.contains(quest);
    }
}