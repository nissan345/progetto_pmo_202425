package main.controller.handlers;

import java.util.List;
import java.util.Optional;

import main.controller.Controller;
import main.model.character.MainCharacter;
import main.model.character.npc.Mum;
import main.model.character.npc.NPC;
import main.model.quest.CompletionCondition;
import main.model.quest.Quest;
import main.model.quest.QuestSystem;
import main.model.world.Room;
import main.view.View;

/**
 * Handles interactions between the MainCharacter and NPCs.
 * It separates the logic for automatic events (triggered when entering a room) 
 * and manual events (triggered when the user clicks to talk to an NPC).
 */
public class NpcInteractionHandler {

    // ATTRIBUTES ------------------------------------------------------------------------
    private Controller controller;
    private MainCharacter mainCharacter;
    private View view;
    private QuestSystem questSystem;

    // CONSTRUCTOR -----------------------------------------------------------------------

    /**
     * Constructs a new NpcInteractionHandler.
     * @param controller The main game controller.
     * @param mainCharacter The player character.
     * @param view The UI view for displaying dialogues and messages.
     * @param questSystem The system managing the state of quests.
     */
    public NpcInteractionHandler(Controller controller, MainCharacter mainCharacter, View view, QuestSystem questSystem) {
        this.controller = controller;
        this.mainCharacter = mainCharacter;
        this.view = view;
        this.questSystem = questSystem;
    }

    // AUTOMATIC EVENTS (ROOM ENTRY) -----------------------------------------------------

    /**
     * Handles automatic interactions that occur when the player enters a room.
     * 1. Checks if there is a completed quest with the NPC in the room. If so, and if it's auto-completable, turns it in.
     * 2. If no quest is turned in, checks if a new quest can be assigned by the NPC and assigns it.
     */
    public void handleRoomEntryInteraction() {
        Room currentRoom = mainCharacter.getCurrentRoom();
        Optional<NPC> npcOpt = currentRoom.getNpcInRoom();

        if (npcOpt.isPresent()) {
            NPC npc = npcOpt.get();

            // If the player enters the room and has finished the task, complete it immediately.
            Optional<Quest> completedQuest = mainCharacter.getCompletedQuestWithNPC(npc);
            if (completedQuest.isPresent()) {
                if (this.canAutoTurnIn(completedQuest.get())) {
                    performQuestTurnIn(npc, completedQuest.get());
                }
                return; 
            }

            // Only assign if the player does NOT already have an active quest with this NPC.
            if (!mainCharacter.hasActiveQuestWithNPC(npc)) {
                
                // QuestSystem returns quests that are triggered by entering this room
                List<Quest> newQuests = questSystem.onPlayerEnteredRoom(mainCharacter, currentRoom);

                // Takes the first valid new quest
                Optional<Quest> validNewQuest = newQuests.stream()
                    .filter(q -> (!mainCharacter.getCompletedQuests().contains(q) && q.getAssignerNPC().equals(npc))) // Ensure it's not a repeated old quest
                    .findFirst();

                if (validNewQuest.isPresent()) {
                    performQuestAssignment(npc, validNewQuest.get());
                }
            }
            mainCharacter.setJustEntered(false);
        }
    }

    // MANUAL EVENTS (USER CLICK) --------------------------------------------------------

    /**
     * Handles the interaction logic when the user explicitly clicks the "Talk" button for an NPC.
     * 1. Checks for any completed quest (manual turn-in).
     * 2. Checks for special interactions 
     * 3. Checks for an ongoing active quest to show progress dialogue.
     * 4. Otherwise, shows the standard initial dialogue.
     */
    public void handleUserClickInteraction() {
        Room currentRoom = mainCharacter.getCurrentRoom();
        Optional<NPC> npcOpt = currentRoom.getNpcInRoom();

        if (npcOpt.isPresent()) {
            NPC npc = npcOpt.get();

            Optional<Quest> completedQuest = mainCharacter.getCompletedQuestWithNPC(npc);
            
            if (completedQuest.isPresent()) {
                performQuestTurnIn(npc, completedQuest.get());
                return; 
            }

            if (npc instanceof Mum) {
                String giftMessage = ((Mum) npc).checkGiftInteraction(mainCharacter);
                if (!giftMessage.isEmpty()) {
                	view.showNpcMessage(npc.getName(), giftMessage);
                    controller.updateView();
                    return; 
                }
            }
            
            Optional<Quest> ongoing = mainCharacter.getOngoingQuests().stream()
                    .filter(q -> q.getAssignerNPC().equals(npc))
                    .findFirst();

            if (ongoing.isPresent()) {
                String dialogue = npc.getQuestInProgressDialogue(ongoing.get());
                view.showQuestDialogue(npc.getName(), dialogue);
            } 
            else {
                String dialogue = npc.getInitialDialogue();
                view.showNpcMessage(npc.getName(), dialogue);
            }
        }
    }

    // HELPER METHODS --------------------------------------------------------------------

    /**
     * Executes the logic for assigning a new quest to the player.
     * Displays a visual alert and the NPC's assignment dialogue.
     * @param npc The NPC assigning the quest.
     * @param quest The quest being assigned.
     */
    private void performQuestAssignment(NPC npc, Quest quest) {
        // Visual Alert
    	view.showQuestMessage("Nuova quest: " + quest.getName());
        
        // NPC Dialogue
        String assignDialogue = npc.getQuestAssignedDialogue(quest);
        view.showQuestDialogue(npc.getName(), assignDialogue);

    }

    /**
     * Executes the logic for turning in a completed quest.
     * Displays the completion dialogue, processes the reward, and updates the view.
     * @param npc The NPC receiving the quest.
     * @param quest The completed quest.
     */
    private void performQuestTurnIn(NPC npc, Quest quest) {
        String completionDialogue = npc.getQuestCompletionDialogue(quest);
        view.showQuestDialogue(npc.getName(), completionDialogue);

        boolean success = questSystem.tryTurnIn(mainCharacter, npc);
        
        if (success) {
        	view.showQuestMessage("Quest Completata: " + quest.getName());
            controller.updateView(); 
        }
    }
    
    /**
     * Checks if a quest can be turned in automatically without user interaction.
     * Iterates through the quest's conditions to verify if all allow auto-completion.
     * @param quest The quest to check.
     * @return true if the quest can be auto-completed, false otherwise.
     */
    private boolean canAutoTurnIn(Quest quest) {

        for (CompletionCondition condition : quest.getConditions()) {
            if (!condition.isAutoCompletable()) {
                return false;
            }
        }
        return true;
    }
}