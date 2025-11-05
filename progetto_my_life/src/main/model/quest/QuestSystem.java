package main.model.quest;

import java.util.*;
import java.util.function.BiPredicate;

import main.model.character.MainCharacter;
import main.model.character.NPC;
import main.model.world.Room;

/**
 * SPIEGAZIONE CLASSE -------------------------------------------------------------
 * Orchestratore centrale delle quest:
 * Quando vengono triggerati, gli NPC forniscono il catalogo delle quest
 * La quest viene assegnata al player e alla fine l'NPC applica la ricompensa
 */

public class QuestSystem {
    // trigger di attivazione: diventa true quando deve offrire una quest
    private final Map<Quest, BiPredicate<MainCharacter, Room>> triggers = new HashMap<>();
    private final List<Quest> catalog = new ArrayList<>(); // catalogo globale di tutte le quest
    private final Set<Quest> offered = new HashSet<>(); // quest già offerte almeno una volta al player (per non ripeterle)
    private final Set<Quest> turnedIn = new HashSet<>(); // quest completate dal player

    // MAIN METHODS ----------------------------------------------------------------
    
    /**
     * Registers an NPC's quests into the quest system
     * @param npc
     */
    public void registerNPC(NPC npc) {
        for (Quest quest : npc.getAvailableQuests()) {
            // Adds the quest to the catalog if not already present
            if (!catalog.contains(quest)) {
                catalog.add(quest);
            }
            
            // Registers the trigger condition for the quest
            BiPredicate<MainCharacter, Room> trigger = quest.getAssignerNPC().getTriggerCondition();
            if (trigger != null) {
                triggers.put(quest, trigger);
            }
        }
    }

    /**
     * Registers a quest trigger into the quest system
     * @param quest
     * @param trigger
     */
    public void registerTrigger(Quest quest, BiPredicate<MainCharacter, Room> trigger) {
        if(quest != null && trigger != null) {
            triggers.put(quest, trigger);
        }
    }

    /**
     * Checks whether any quest is triggered when the player enters a room
     * Returns the list of newly offered quests 
     * @param player
     * @param room
     */
    public List<Quest> onPlayerEnteredRoom(MainCharacter player, Room room){
        List<Quest> newlyOffered = new ArrayList<>();

        // If the quest has not been turned in yet and its trigger condition is satisfied, offer it
        for (Quest quest : catalog) {
            if (!turnedIn.contains(quest)) {
                if (offered.contains(quest) && !quest.isCompleted()) {
                    newlyOffered.add(quest);
                }
            }

            // If the quest hasn't been offered before, check its trigger condition
            if (!offered.contains(quest)) {
                BiPredicate<MainCharacter, Room> t = triggers.get(quest);
                if (t != null) {
                    boolean shouldOffer = false;
                    try {
                        shouldOffer = t.test(player, room);
                    } catch (RuntimeException ex) {
                        shouldOffer = false; // Check failed, do not offer the quest
                    }

                    if (shouldOffer) {
                        offered.add(quest); // Mark the quest as offered
                        newlyOffered.add(quest);
                        if (player != null) {
                            player.addQuest(quest); // Assign the quest to the player
                        }
                    }
                }
            }
        }
        return newlyOffered;
    }

    /**
     * Finds a completed quest assigned by the given NPC (needed for the next method)
     * @param player
     * @param npc
     */
    public Optional<Quest> findCompletedQuestForNPC(MainCharacter player, NPC npc) {
        if (player != null && npc != null) {
            return player.getCompletedQuestWithNPC(npc);
        }
        return Optional.empty();
    }

    /**
     * Turns in a completed quest to the given NPC
     * @param player
     * @param npc
     */
    public boolean tryTurnIn(MainCharacter player, NPC npc) {
        Optional<Quest> completed = findCompletedQuestForNPC(player, npc);
        if (completed.isPresent()) {
            Quest quest = completed.get();

            // Apply the reward
            quest.applyReward(player);

            // Mark the quest as turned in and remove it from the player's active quests
            turnedIn.add(quest);
            offered.remove(quest);
            player.removeQuest(quest);
            return true;
        }
        return false;
    }

    /**
     * Resets the quest system (for testing purposes)
     */
    public void reset() {
        triggers.clear();
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
