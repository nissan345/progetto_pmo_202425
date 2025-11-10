package main.model.requirement;

import java.util.ArrayList;
import java.util.List;
import main.model.character.MainCharacter;

public class CanDrinkRequirement implements Requirement{

	@Override
	public boolean isSatisfiedBy(MainCharacter c) {
		return c.getStats().canDrink();
	}

	@Override
	public List<String> getFailureReasons(MainCharacter c){
		List<String> reasons = new ArrayList<>();

        if (c.getStats().getHydration() >= 70) {
            reasons.add("Non hai abbastanza sete per bere.");
        }


        return reasons; 
	}
	
}
