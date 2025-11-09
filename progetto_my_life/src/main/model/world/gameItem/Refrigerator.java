package main.model.world.gameItem;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import main.model.action.ActionResult;
import main.model.character.MainCharacter;
import main.model.requirement.CanEatRequirement;


/**
 * Specialised GameItem representing a refrigerator that contains various food items. This class requires user choice, 
 * when interacting with the refrigerator. The different choices that are offered modify the stats differently. Also validates
 * requirements before allowing access to it 
 */
public class Refrigerator extends GameItem {
    
    private final Map<FoodType, ActionResult> foodEffects;
    
    public Refrigerator() {
        super(new Builder("Frigorifero", "Cucina", 90)
                .message("Apri il frigorifero, scegli cosa mangiare!")
                .requirement(new CanEatRequirement()));
        
        this.foodEffects = new EnumMap<>(FoodType.class);
        for (FoodType food : FoodType.values()) {
            foodEffects.put(food, new ActionResult(
                "Mangi " + food.getName(), 
                food.getSatiety(), 
                food.getHydration(), 
                food.getEnergy(),
                0
            ));
        }
    }
    
    @Override
    public boolean requiresChoice() { return true; }

    @Override
    public List<FoodType> availableOptions() { return new ArrayList<>(foodEffects.keySet()); }

    @Override
    public ActionResult use(MainCharacter character) { 
    	ActionResult deny = denyIfNotAllowed(character);
    	if(deny != null) {
    		return deny;
    	}
        return new ActionResult("Adesso che hai aperto il frigorifero, cosa ti va di mangiare?"); 
    }

    @Override
    public ActionResult useWithChoice(MainCharacter character, FoodType choice) {
    	ActionResult deny = denyIfNotAllowed(character);
    	if(deny != null) {
    		return deny;
    	}
    	 if (!foodEffects.containsKey(choice) || choice == null) {
             return new ActionResult("Questo cibo non è disponibile nel frigorifero!");
         }

    	 return foodEffects.get(choice);
    } 
    
    private ActionResult denyIfNotAllowed(MainCharacter c) {
        return requirement.isSatisfiedBy(c) ? null
            : new ActionResult(requirement.getFailureReasons(c));
    }

}

