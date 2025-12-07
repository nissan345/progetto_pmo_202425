package main.model.quest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiPredicate;
import main.model.character.MainCharacter;
import main.model.character.npc.NPC;
import main.model.world.Room;

public class Quest {

    private final String name;
    private final String description;
    private final NPC assignerNPC;
    private final int affinityPoints;
    private final int xpReward;
    private final List<CompletionCondition> conditions;
    private final BiPredicate<MainCharacter, Room> triggerCondition;
    private boolean completed;

private Quest(Builder builder) {
        this.name = builder.name;
        this.description = builder.description;
        this.assignerNPC = builder.assignerNPC;
        this.affinityPoints = builder.affinityPoints;
        this.xpReward = builder.xpReward;
        this.conditions = new ArrayList<>(builder.conditions);
        this.triggerCondition = builder.triggerCondition;
        this.completed = false;
    }

    public static class Builder {
        private String name;
        private String description;
        private NPC assignerNPC;
        private int affinityPoints = 0;
        private int xpReward = 0;
        private List<CompletionCondition> conditions = new ArrayList<>();
        private BiPredicate<MainCharacter, Room> triggerCondition = (character, room) -> false;

        public Builder(String name, String description, NPC assignerNPC) {
            this.name = name;
            this.description = description;
            this.assignerNPC = assignerNPC;
        }

        public Builder affinityPoints(int affinityPoints) {
            this.affinityPoints = affinityPoints;
            return this;
        }

        public Builder xpReward(int xpReward) {
            this.xpReward = xpReward;
            return this;
        }

        public Builder addCondition(CompletionCondition condition) {
            this.conditions.add(condition);
            return this;
        }

        public Builder triggerCondition(BiPredicate<MainCharacter, Room> triggerCondition) {
            this.triggerCondition = triggerCondition;
            return this;
        }

        public Quest build() {
            return new Quest(this);
        }

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
    
    /**
     * 
     * @return
     */
    public void resetItems(MainCharacter character) {
    	for (CompletionCondition condition : conditions) {
            condition.onQuestCompleted(character);
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
        return Collections.unmodifiableList(this.conditions);
    }

    public boolean isCompleted() {
        return this.completed;
    } 

    public BiPredicate<MainCharacter, Room> getTriggerCondition() {
        return triggerCondition;
    }
}
