package main.model.requirement.requirementImpl;

import java.util.ArrayList;
import java.util.List;
import main.model.character.MainCharacter;
import main.model.requirement.Requirement;

public class CanShowerRequirement implements Requirement{

	@Override
	public boolean isSatisfiedBy(MainCharacter c) {
		return c.getStats().canShower();
	}

	@Override
	public List<String> getFailureReasons(MainCharacter c){
		List<String> reasons = new ArrayList<>();

        // Check if there is enough energy
        if (c.getStats().getEnergy() <= 20) {
            reasons.add("Non hai abbastanza energia per lavarti, ");
        }

        // make sure hygiene is not too high
        if (c.getStats().getHygiene() >= 70) {
            reasons.add("Non c'è bisogno di lavarsi ora! Divertiti!");
        }

        return reasons; 
	}
}
