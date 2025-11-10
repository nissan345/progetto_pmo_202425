package main.model.world.gameItem;

import java.util.List;
import java.util.function.BiFunction;
import main.model.action.ActionResult;
import main.model.character.MainCharacter;
import main.model.requirement.requirementImpl.*;
import main.model.requirement.Requirement;


/**
 * Base class for all items present in the house.
 */
public class GameItem{
	
	// Variables definition
    protected final String name;
    protected final String description;
    protected final int size;
    protected final Requirement requirement;
    protected String room;
    private String message;
    private int deltaSatiety, deltaHydration, deltaEnergy, deltaHygiene;
	private BiFunction<MainCharacter, GameItem, ActionResult> dynamicUse; // If set, this function will be called to perform the action, instead of use() method
    
	// CONSTRUCTOR
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
        this.dynamicUse = builder.dynamicUse;
        this.requirement = (builder.requirement != null ? builder.requirement : new AlwaysTrueRequirement());

    }
    
 // Getters
    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getSize() { return size; }
    public String getRoom() { return room; }
    public String getMessage() {return message;}
    public int getDeltaSatiety() {return deltaSatiety;}
	public int getDeltaHydration() {return deltaHydration;}
	public int getDeltaEnergy() { return deltaEnergy;}
	public int getDeltaHygiene() { return deltaHygiene; }
	public Requirement getRequirement() {return requirement;}
      
    /**
     * Executes the default action associated with this item. 
     * @param character
     * @return the result of the action
     */
    public ActionResult use(MainCharacter character) {
        if (!requirement.isSatisfiedBy(character)) {
            return new ActionResult(requirement.getFailureReasons(character)); 
        }
        if (dynamicUse != null) {
            return dynamicUse.apply(character, this);
        }
        
        return new ActionResult(message, deltaSatiety, deltaHydration, deltaEnergy, deltaHygiene);
    }
    
    /**
     * Checks if the GameItem requires a choice when used. Default implementation returns false. 
     * If a subclass implements this method, it can override it and return true in case the GameItem 
     * requires a choice
     * @return true if item requires choice, false otherwise
     */
    public boolean requiresChoice() { return false; }
    
    /**
     * Shows a list of options that can be made when interacting with GameItem that requires choice
     * @return list of available options
     */
    public List<?> availableOptions() {return List.of(); }
    
    /**
     * This is for GameItems that require choice, basic GameItems don't support choices. This method is meant to be overridden 
     * by GameItems that support a choice.
     * @param character
     * @param choice
     * @return the ActionResult of an item that doesn't support a choice
     */

    public ActionResult useWithChoice(MainCharacter character, FoodType choice) {return new ActionResult("This item doesn't support choices.");}

    
    /**
     * Builder class used to create GameItem instances.
     */
    public static class Builder {
        
		// Required fields
        private final String name;
        private final String room;
        private final int size;
        private Requirement requirement;
        
        // default values of the optional fields
        private int deltaSatiety = 0;
        private int deltaHydration = 0;
        private int deltaEnergy = 0;
        private int deltaHygiene = 0;
        private String description = "";
        private String message = "You use the item.";
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
        
        // Final method that builds the item
        public GameItem build() {
            return new GameItem(this);
        }
    }
}
