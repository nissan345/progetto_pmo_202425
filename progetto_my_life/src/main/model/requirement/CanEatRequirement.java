package main.model.requirement;

import java.util.ArrayList;
import java.util.List;

import main.model.character.MainCharacter;

public class CanEatRequirement implements Requirement{

	@Override
	public boolean isSatisfiedBy(MainCharacter c) {
		return c.getStats().canEat();
	}

	@Override
	public List<String> getFailureReasons(MainCharacter c){
		List<String> reasons = new ArrayList<>();

        if (c.getStats().getSatiety() >= 85) {
            reasons.add("Non hai abbastanza fame per managiare.");
        }

        if (c.getStats().getEnergy() <= 40) {
            reasons.add("Non hai abbastanza energia per mangiare.");
        }

        if (c.getStats().getHygiene() <= 50) {
            reasons.add("Sei troppo sporco per mangiare.");
        }

        return reasons; 
	}
}
