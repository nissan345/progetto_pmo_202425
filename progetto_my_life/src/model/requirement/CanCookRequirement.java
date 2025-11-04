package model.requirement;

import java.util.ArrayList;
import java.util.List;

import model.character.MainCharacter;

public class CanCookRequirement implements Requirement{

	@Override
	public boolean isSatisfiedBy(MainCharacter c) {
		return c.getStats().canCook();
	}

	@Override
	public List<String> getFailureReasons(MainCharacter c){
		List<String> reasons = new ArrayList<>();

        if (c.getStats().getSatiety() >= 40) {
            reasons.add("Non hai abbastanza fame per cucinare.");
        }

        if (c.getStats().getEnergy() < 40) {
            reasons.add("Non hai abbastanza energia per cucinare.");
        }

        if (c.getStats().getHygiene() < 50) {
            reasons.add("Sei troppo sporco per cucinare.");
        }

        return reasons; 
	}
}
