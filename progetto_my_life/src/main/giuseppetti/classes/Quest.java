package main.giuseppetti.classes;

import java.util.ArrayList;
import java.util.List;

import main.fabbri.classes.Character;
import main.giuseppetti.interfaces.CompletionCondition;

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
    
    // Verifies quest completion
    public boolean checkCompletion(Character character) {
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
   
    // GETTERS

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