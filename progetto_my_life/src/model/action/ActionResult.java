package model.action;

/**
 * Result of an action with effects on the character
 */
public class ActionResult {
    private String message;
    private int deltaSatiety;
    private int deltaHydration;
    private int deltaEnergy;
    private int deltaHygiene;
    private static final int ACTION_TIME = 5; // All actions take 5 seconds
    
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
    
    // Getters
    public String getMessage() { return message; }
    public int getDeltaSatiety() { return deltaSatiety; }
    public int getDeltaHydration() { return deltaHydration; }
    public int getDeltaEnergy() { return deltaEnergy; }
    public int getDeltaHygiene() { return deltaHygiene; }
    public int getActionDuration() { return ACTION_TIME; }
}


