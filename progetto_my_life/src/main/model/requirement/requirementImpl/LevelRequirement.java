package main.model.requirement.requirementImpl;

import java.util.ArrayList;
import java.util.List;
import main.model.character.MainCharacter;
import main.model.requirement.Requirement;

/**
 *
 * @author OS
 */
public class LevelRequirement implements Requirement{

    private int requiredLvl; 
    public LevelRequirement(int requiredLvl) {
        if (requiredLvl < 1) throw  new IllegalArgumentException("requiredLvl >= 1"); 
        this.requiredLvl = requiredLvl; 
    }

    @Override
    public boolean isSatisfiedBy(MainCharacter c) {
        return c.getLvl() >= this.requiredLvl; 
    }

    @Override
    public List<String> getFailureReasons(MainCharacter c) {
        List<String> failureReasons = new ArrayList<>();
        
        if (c.getLvl() < this.requiredLvl) {
            failureReasons.add("Livello insufficiente. Livello richiesto: " + this.requiredLvl + ", livello attuale: " + c.getLvl());
        }

        return failureReasons;
    }

    public int getRequiredLvl() {
        return this.requiredLvl; 
    }


}

