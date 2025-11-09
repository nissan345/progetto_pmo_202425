package main.model.requirement;

import java.util.ArrayList;
import java.util.List;
import main.model.character.MainCharacter;

public class CanSleepRequirement implements Requirement{
	
	

	@Override
	public boolean isSatisfiedBy(MainCharacter c) {
		return c.getStats().canSleep();
	}

	@Override
	public List<String> getFailureReasons(MainCharacter c) {
		List<String> reasons = new ArrayList<>();

        // make sure that energy is not too high
        if (c.getStats().getEnergy() >= 70) {
            reasons.add("Non puoi dormire adesso, sei troppo energetico");
        }

        // Check if there is enough hygiene
        if (c.getStats().getHygiene() <= 30) {
            reasons.add("Sei troppo sporco per dormire. Le lenzuola patiranno");
        }

        return reasons; 
	}

	
}
