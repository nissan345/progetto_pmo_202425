package main.model.requirement;

import java.util.ArrayList;
import java.util.List;

import main.model.character.MainCharacter;

public class CanShowerRequirement implements Requirement{

	@Override
	public boolean isSatisfiedBy(MainCharacter c) {
		return c.getStats().canShower();
	}

	@Override
	public List<String> getFailureReasons(MainCharacter c){
		List<String> reasons = new ArrayList<>();

        // Controlla se l'energia è sufficiente
        if (c.getStats().getEnergy() <= 20) {
            reasons.add("Non hai abbastanza energia per lavarti, ");
        }

        // Controlla se l'igiene è sufficiente
        if (c.getStats().getHygiene() >= 70) {
            reasons.add("Non c'è bisogno di lavarsi ora! Divertiti!");
        }

        return reasons; 
	}
}
