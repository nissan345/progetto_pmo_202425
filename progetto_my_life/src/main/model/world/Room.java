package main.model.world;

import java.util.List;
import java.util.Optional;
import main.model.character.MainCharacter;
import main.model.character.NPC;
import main.model.requirement.Requirement;
import main.model.world.gameItem.GameItem;

/**
 * Basic implementation of a generic Room
 */
public class Room {

    private String roomName;
    private Optional<NPC> npcInRoom;     
    private final List<GameItem> itemsInRoom;  
    private Requirement entryRequirement; 
    
    // CONSTRUCTOR
    public Room(String name, List<GameItem> items, Requirement requirement){
        this.roomName = name; 
        this.itemsInRoom = items;
        this.npcInRoom = Optional.empty();
        this.entryRequirement = requirement; 
    }
    
    // GETTERS 
    public String getRoomName(){return roomName;}
    public List<GameItem> getItemsInRoom() {return itemsInRoom;}
    public Optional<NPC> getNpcInRoom() {return npcInRoom;}
    public Requirement getEntryRequirement() {return this.entryRequirement;}
    
    
    /**
     * Shows if there is an NPC in the room 
     * @param n
     * @return true if the NPC is in the room, false otherwise
     */
    public boolean hasNpc(NPC n){
    	if(this.npcInRoom.isPresent()) {
    		return this.npcInRoom.get().getRelationship().equals(n.getRelationship());
    	}
        return false;
    }

    /**
     * Checks if an item is in the room 
     * @param o
     * @return true if the item is present, false otherwise
     */
    public boolean hasItemRoom(GameItem o){
        return itemsInRoom.stream()
                .anyMatch(item -> item.getName().equals(o.getName()));
    }
    
    /**
     * Setter of NPC, sets an NPC in a room
     * @param n
     * @throws IllegalArgumentException if the npc is null
     */
    public void setNpc(NPC n) {
    	if(n==null) {
    		throw new IllegalArgumentException("Npc cannot be null!");
    	}
        npcInRoom = Optional.of(n);
    };

    /**
     * Adds an item in the room
     * @param o
     */
    public void addItemRoom(GameItem o){
        this.itemsInRoom.add(o);
    };
    
    /**
     * Removes an item in the room 
     * @param o
     */
    public void removeItemRoom(GameItem o){
        this.itemsInRoom.remove(o);
    };

    /**
     * Checks if the character can enter the room based on their level 
     * @param character
     * @return true if the levelRequirement is met, false otherwise
     */
    public boolean canEnter(MainCharacter character) {
        return entryRequirement.isSatisfiedBy(character); 
    }

    /**
     * Tells the reason why the character cannot enter the room 
     * @param character
     * @return a list of failure reasons
     */
    public List<String> getEntryFailureReasons(MainCharacter character) {
        return this.entryRequirement.getFailureReasons(character); 
    }
    
    
    @Override
    public String toString(){
    	String roomInfo = null;
    	if(this.npcInRoom.isEmpty()) {
    		roomInfo = "\nItems presenti: " + this.itemsInRoom;
    	}else {
    		roomInfo = this.roomName + "\nNPC presenti: " + this.npcInRoom.get().getRelationship() + "\nItems presenti: " + this.itemsInRoom;
    	}
        return roomInfo;
    }
}
