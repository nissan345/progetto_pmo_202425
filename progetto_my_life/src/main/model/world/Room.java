package main.model.world;

import java.util.List;
import java.util.Optional;
import main.model.character.MainCharacter;
import main.model.character.NPC;
import main.model.requirement.Requirement;
import main.model.world.gameItem.GameItem;

public class Room {

    private String roomName;
    private Optional<NPC> npcInRoom;               // Indica gli oggetti presenti nella room
    private final List<GameItem> objectsInRoom;  // Indica gli NPC presenti nella room 
    private Requirement entryRequirement; 
    public Room(String name, List<GameItem> objects, Requirement requirement){
        this.roomName = name; 
        this.objectsInRoom = objects;
        this.npcInRoom = Optional.empty();
        this.entryRequirement = requirement; 
    }
    
    // GETTERS 
    public String getRoomName(){
        return roomName;
    }

    public List<GameItem> getObjectsInRoom() {
        return objectsInRoom;
    }

    public Optional<NPC> getNpcInRoom() {
        return npcInRoom;
    }

    public Requirement getEntryRequirement() {
        return this.entryRequirement; 
    }

    /**
     * Shows if there is an NPC in the room 
     * @param n
     * @return
     */
    public boolean hasNpc(NPC n){
        return this.npcInRoom.isPresent();
    }

    /**
     * Checks if an item is in the room 
     * @param o
     * @return
     */
    public boolean hasObjectRoom(GameItem o){
        return objectsInRoom.stream()
                .anyMatch(object -> object.getName().equals(o.getName()));
    }
    
    public void setNpc(NPC n) {
        npcInRoom = Optional.of(n);
    };

    /**
     * Adds an item in the room
     * @param o
     */
    public void addObjectRoom(GameItem o){
        this.objectsInRoom.add(o);
    };
    
    /**
     * Removes an item in the room 
     * @param o
     */
    public void removeObjectRoom(GameItem o){
        this.objectsInRoom.remove(o);
    };

    /**
     * Chcecks if the character can enter the room based on their level 
     * @param character
     * @return
     */
    public boolean canEnter(MainCharacter character) {
        return entryRequirement.isSatisfiedBy(character); 
    }

    /**
     * Tells why the character cannot enter the room 
     * @param character
     * @return
     */
    public List<String> getEntryFailureReasons(MainCharacter character) {
        return this.entryRequirement.getFailureReasons(character); 
    }

    public String toString(){
    	String stringa = null;
    	if(this.npcInRoom.isEmpty()) {
    		stringa = "\nObjects presenti: " + this.objectsInRoom;
    	}else {
    		stringa = this.roomName + "\nNPC presenti: " + this.npcInRoom.get().getRelationship() + "\nObjects presenti: " + this.objectsInRoom;
    	}
        return stringa;
    }
}
