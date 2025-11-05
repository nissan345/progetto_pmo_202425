package model.quest;

import java.util.ArrayList;
import java.util.List;
import model.character.MainCharacter;
import model.character.NPC;

public class Quest {

    private final String name;
    private final String description;
    private final NPC assignerNPC;
    private int affinityPoints;
    private List<CompletionCondition> conditions;
    private boolean completed;

    public Quest(String name, String description, NPC assignerNPC, int affinityPoints, List<CompletionCondition> conditions) {
        this.name = name;
        this.description = description;
        this.assignerNPC = assignerNPC;
        this.affinityPoints = affinityPoints;
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
     * @param player
     */
    public void applyReward(MainCharacter player) {
        if (player != null && completed) {
            assignerNPC.increaseAffinity(assignerNPC, affinityPoints); // TO DO: Il metodo è protected quindi non lo legge
            // Additional rewards can be added here
        }
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
