package model.action;

import java.util.List;
import java.util.Optional;
import model.character.MainCharacter;
import model.world.Room;
import model.world.gameItem.GameObject;

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
    private Integer customDurationSeconds;
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

    public class PickItemAction {

    public ActionResult execute(MainCharacter character, GameObject item) {
        Room room = character.getCurrentRoom();

        if (!room.hasOggettoRoom(item)) {
            return new ActionResult("L'oggetto non è presente nella stanza!");
        }

        boolean added = character.getInventory().addItem(item);
        if (!added) {
            return new ActionResult("Inventario pieno! Non puoi raccogliere " + item.getName());
        }

        room.removeOggettoRoom(item);
        return new ActionResult("Hai raccolto " + item.getName() + "!");
    }
}

public class DropItemAction {

    public ActionResult execute(MainCharacter character, GameObject item) {
        Room room = character.getCurrentRoom();

        Optional<GameObject> removedItem = character.getInventory().removeItem(item.getName());

        if (removedItem.isEmpty()) {
            return new ActionResult("Non hai " + item.getName() + " nell'inventario!");
        }

        room.addOggettoRoom(removedItem.get());
        return new ActionResult("Hai lasciato " + item.getName() + " nella stanza.");
            }
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


