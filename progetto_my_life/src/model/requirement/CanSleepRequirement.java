package model.requirement;

import java.util.ArrayList;
import java.util.List;

import model.character.MainCharacter;

public class CanSleepRequirement implements Requirement{
	
	

	@Override
	public boolean isSatisfiedBy(MainCharacter c) {
		return c.getStats().canSleep();
	}

	@Override
	public List<String> getFailureReasons(MainCharacter c) {
		List<String> reasons = new ArrayList<>();

        // Controlla se l'energia è sufficiente
        if (c.getStats().getEnergy() <= 70) {
            reasons.add("Non puoi dormire adesso, sei troppo energetico");
        }

        // Controlla se l'igiene è sufficiente
        if (c.getStats().getHygiene() < 30) {
            reasons.add("Sei troppo sporco per dormire. Le lenzuola patiranno");
        }

        return reasons; 
	}

	
}
