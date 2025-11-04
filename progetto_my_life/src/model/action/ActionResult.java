package main.neri.classes;

/**
 * Result of an action with effects on the character
 */
public class ActionResult {
    private String message;
    private int deltaHunger;
    private int deltaThirst;
    private int deltaEnergy;
    private int deltaHygiene;
    private static final int ACTION_TIME = 5; // All actions take 5 seconds
    
    public ActionResult(String message) {
        this(message, 0, 0, 0, 0);
    } 
    
    public ActionResult(String message, int deltaHunger, int deltaThirst, int deltaEnergy, int deltaHygiene) {
        this.message = message;
        this.deltaHunger = deltaHunger;
        this.deltaThirst = deltaThirst;
        this.deltaEnergy = deltaEnergy;
        this.deltaHygiene = deltaHygiene;
    }
    
    // Getters
    public String getMessage() { return message; }
    public int getDeltaHunger() { return deltaHunger; }
    public int getDeltaThirst() { return deltaThirst; }
    public int getDeltaEnergy() { return deltaEnergy; }
    public int getDeltaHygiene() { return deltaHygiene; }
    public int getActionDuration() { return ACTION_TIME; }
}


