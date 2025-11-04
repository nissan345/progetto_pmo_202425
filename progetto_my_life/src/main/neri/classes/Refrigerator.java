package main.neri.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import main.fabbri.classes.*;

public class Refrigerator extends GameObject {
    
    private final Map<FoodType, ActionResult> foodEffects;
    
    public Refrigerator() {
        super(new Builder("Refrigerator", "Kitchen", 90)
                .message("You open the refrigerator... Choose what to eat!"));
        
        this.foodEffects = new HashMap<>();
        // Initialize all foods with their effects
        for (FoodType food : FoodType.values()) {
            foodEffects.put(food, new ActionResult(
                food.getDescription(), 
                food.getHunger(), 
                food.getThirst(), 
                food.getEnergy(),
                0
            ));
        }
    }
    
    @Override
    public boolean requiresChoice() { return true; }

    @Override
    public java.util.List<FoodType> availableOptions(Character p) { 
        return new ArrayList<>(foodEffects.keySet()); 
    }

    @Override
    public ActionResult use(Character p) { 
        return new ActionResult("You open the refrigerator... Choose what to eat!"); 
    }

    @Override
    public ActionResult use(Character p, Object option) {
        FoodType food = (FoodType) option;
        return foodEffects.getOrDefault(
            food, 
            new ActionResult(
                food.getDescription(), 
                food.getHunger(), 
                food.getThirst(), 
                food.getEnergy(), 
                0
            )
        );
    }
}
