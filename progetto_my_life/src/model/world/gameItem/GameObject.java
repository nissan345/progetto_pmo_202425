package main.neri.classes;

import java.util.List;
import main.fabbri.classes.Character;

/**
 * Base class for all objects present in the house.
 */
public class GameObject{
    protected final String name;
    protected final String description;
    protected final int size;
    public String message;
    protected String room;
    private boolean specialInteraction;
    private int deltaHunger, deltaThirst, deltaEnergy, deltaHygiene;
    
    public GameObject(Builder builder) {
        this.name = builder.name;
        this.description = builder.description;
        this.size = builder.size;
        this.room = builder.room;
        this.deltaEnergy = builder.deltaEnergy;
        this.deltaHunger = builder.deltaHunger;
        this.deltaHygiene = builder.deltaHygiene;
        this.deltaThirst = builder.deltaThirst;
        this.message = builder.message;
        this.specialInteraction = builder.specialInteraction;
    }
      
    /** Executes the default action associated with this object. */
    public ActionResult use(Character character) {
        return new ActionResult(message, deltaHunger, deltaThirst, 
                deltaEnergy, deltaHygiene);
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

    public ActionResult use(Character c, Object option) {
        return use(c);
    }

    /**
     * Builder class used to create GameObject instances.
     */
    public static class Builder {
        // Required fields
        private final String name;
        private final String room;
        private final int size;
        
        // Optional fields with default values
        private String description = "";
        private String message = "You use the object.";
        private boolean specialInteraction = false;
        private int deltaHunger = 0;
        private int deltaThirst = 0;
        private int deltaEnergy = 0;
        private int deltaHygiene = 0;
        
        // Constructor with required fields
        public Builder(String name, String room, int size) {
            this.name = name;
            this.room = room;
            this.size = size;
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
        
        public Builder hunger(int val) {
            this.deltaHunger = val;
            return this;
        }
        
        public Builder thirst(int val) {
            this.deltaThirst = val;
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
        
        // Final method that builds the object
        public GameObject build() {
            return new GameObject(this);
        }
    }
}
