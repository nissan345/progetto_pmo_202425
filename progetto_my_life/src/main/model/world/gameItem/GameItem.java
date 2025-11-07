package main.model.world.gameItem;

import java.util.List;
import java.util.function.BiFunction;

import main.model.action.ActionResult;
import main.model.character.MainCharacter;
import main.model.requirement.Requirement;


/**
 * Base class for all items present in the house.
 */
public class GameItem{
    protected final String name;
    protected final String description;
    protected final int size;
    public String message;
    protected String room;
    private boolean specialInteraction;
    private int deltaSatiety, deltaHydration, deltaEnergy, deltaHygiene;
    private final Requirement requirement;
    private BiFunction<MainCharacter, GameItem, ActionResult> dynamicUse; 
    
    public GameItem(Builder builder) {
        this.name = builder.name;
        this.description = builder.description;
        this.size = builder.size;
        this.room = builder.room;
        this.deltaEnergy = builder.deltaEnergy;
        this.deltaSatiety = builder.deltaSatiety;
        this.deltaHygiene = builder.deltaHygiene;
        this.deltaHydration = builder.deltaHydration;
        this.message = builder.message;
        this.specialInteraction = builder.specialInteraction;
        this.dynamicUse = builder.dynamicUse;
        this.requirement = builder.requirement;
    }
      
    /**
     * Executes the default action associated with this item. 
     * @param character
     * @return
     */
    public ActionResult use(MainCharacter character) {
        if (!requirement.isSatisfiedBy(character)) {
            return new ActionResult(requirement.getFailureReasons(character));  // Restituisce il motivo del fallimento
        }
		if (dynamicUse != null) {
            return dynamicUse.apply(character, this);
        }
        return new ActionResult(message, deltaSatiety, deltaHydration, deltaEnergy, deltaHygiene);
    }
    
    // Getters
    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getSize() { return size; }
    public String getRoom() { return room; }
   
    @Override
    public String toString() {
        return name + " (" + room + ")";
    }
    
    public boolean hasSpecialInteraction() {
        return specialInteraction;
    }
    
    // Additional methods
    public boolean requiresChoice() { return false; }

    public List<?> availableOptions(Character c) { 
        return List.of(); 
    }

    public ActionResult use(MainCharacter c, FoodType food) {
        return use(c);
    }

    /**
     * Builder class used to create GameItem instances.
     */
    public static class Builder {
        public Requirement requirement;
		// Required fields
        private final String name;
        private final String room;
        private final int size;
        
        // Optional fields with default values
        private String description = "";
        private String message = "You use the item.";
        private boolean specialInteraction = false;
        private int deltaSatiety = 0;
        private int deltaHydration = 0;
        private int deltaEnergy = 0;
        private int deltaHygiene = 0;
		private BiFunction<MainCharacter, GameItem, ActionResult> dynamicUse;
        
        // Constructor with required fields
        public Builder(String name, String room, int size) {
            this.name = name;
            this.room = room;
            this.size = size;
        }
        
        
        public Builder requirement(Requirement r) {
        	this.requirement = r;
        	return this;
        }

        public Builder dynamic(BiFunction<MainCharacter, GameItem, ActionResult> fn) {
            this.dynamicUse = fn;
            return this;
        }
        // Fluent builder methods
        public Builder description(String val) {
            this.description = val;
            return this;
        }
        
        public Builder message(String val) {
            this.message = val;
            return this;
        }
        
        public Builder satiety(int val) {
            this.deltaSatiety = val;
            return this;
        }
        
        public Builder hydration(int val) {
            this.deltaHydration = val;
            return this;
        }
        
        public Builder energy(int val) {
            this.deltaEnergy = val;
            return this;
        }
        
        public Builder hygiene(int val) {
            this.deltaHygiene = val;
            return this;
        }
        
        public Builder specialInteraction(boolean interaction) {
            this.specialInteraction = interaction;
            return this;
        }
        
        // Final method that builds the item
        public GameItem build() {
            return new GameItem(this);
        }
    }
}
