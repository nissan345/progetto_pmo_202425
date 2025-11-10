package main.model.quest;

import java.util.ArrayList;
import java.util.List;
import main.model.character.MainCharacter;
import main.model.character.npc.NPC;

public class Quest {

    private final String name;
    private final String description;
    private final NPC assignerNPC;
    private final int affinityPoints;
    private final int xpReward;
    private final List<CompletionCondition> conditions;
    private boolean completed;

    public Quest(final String name, final String description, final NPC assignerNPC, final int affinityPoints, final int xpReward,final List<CompletionCondition> conditions) {
        this.name = name;
        this.description = description;
        this.assignerNPC = assignerNPC;
        this.affinityPoints = affinityPoints;
        this.xpReward = xpReward; 
        this.conditions = new ArrayList<>(conditions);
        this.completed = false;
    }

    // MAIN METHODS ----------------------------------------------------------------
    /**
     * Verifies quest completion
     *
     * @param character
     * @return true if the quest is completed, false otherwise
     */
    public boolean checkCompletion(MainCharacter character) {
        if (completed) {
            return true;
        }

        if (character == null) {
            return false;
        }

        // If all conditions are satisfied, the quest is completed
        for (CompletionCondition condition : conditions) {
            if (!condition.checkCompletion(character)) {
                return false;
            }
        }

        // Mark as completed only when all conditions are satisfied
        this.completed = true;
        return true;
    }

    /**
     * Applies the reward of the quest to the given character
     *
     * @param character
     */
    public void applyReward(MainCharacter character) {
        assignerNPC.increaseAffinity(affinityPoints); 
        character.addXp(xpReward);
    }

    // GETTERS ---------------------------------------------------------------------
    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public NPC getAssignerNPC() {
        return this.assignerNPC;
    }

    public int getAffinityPoints() {
        return this.affinityPoints;
    }

    public List<CompletionCondition> getConditions() {
        return this.conditions;
    }

    public boolean isCompleted() {
        return this.completed;
    }
}
