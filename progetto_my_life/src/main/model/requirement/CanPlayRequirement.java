package main.model.requirement;

import java.util.ArrayList;
import java.util.List;

import main.model.character.MainCharacter;

public class CanPlayRequirement implements Requirement{

	@Override
	public boolean isSatisfiedBy(MainCharacter c) {
		return c.getStats().canPlay();
	}

	@Override
	public List<String> getFailureReasons(MainCharacter c){
		List<String> reasons = new ArrayList<>();

        // Controlla se l'energia è sufficiente
        if (c.getStats().getEnergy() <= 20) {
            reasons.add("Non hai abbastanza energia per giocare.");
        }


        return reasons; 
	}
	
}
