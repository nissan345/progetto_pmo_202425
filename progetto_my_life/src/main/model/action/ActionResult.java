package main.model.action;

import java.util.List;

/**
 * Result of an action with effects on the character
 */
public class ActionResult {
    private String message;
    private int deltaSatiety;
    private int deltaHydration;
    private int deltaEnergy;
    private int deltaHygiene;
    private static final int ACTION_TIME = 5;       // Usual duration of actions
    private Integer customDurationSeconds;          // duration of actions for certain objects
    private List<String> messages;
    
    public ActionResult(String message) {
        this(message, 0, 0, 0, 0);
    } 
    
    public ActionResult(String message, int deltaSatiety, int deltaHydration, int deltaEnergy, int deltaHygiene) {
        this.message = message;
        this.deltaSatiety = deltaSatiety;
        this.deltaHydration = deltaHydration;
        this.deltaEnergy = deltaEnergy;
        this.deltaHygiene = deltaHygiene;
    }
    
    public ActionResult(String message, int deltaSatiety, int deltaHydration, int deltaEnergy, int deltaHygiene, int durationSeconds) {
        this(message, deltaSatiety, deltaHydration, deltaEnergy, deltaHygiene);
        this.customDurationSeconds = durationSeconds;
    }
    
    public ActionResult(List<String> messages) {
    	this.messages = messages;
    }


    // Getters
    public String getMessage() { return message; }
    public int getDeltaSatiety() { return deltaSatiety; }
    public int getDeltaHydration() { return deltaHydration; }
    public int getDeltaEnergy() { return deltaEnergy; }
    public int getDeltaHygiene() { return deltaHygiene; }
    public int getActionDuration() { return customDurationSeconds != null ? customDurationSeconds : ACTION_TIME; }
	public List<String> getMessages() {return messages;}
}


