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
    private final List<GameItem> itemsInRoom;  // Indica gli NPC presenti nella room 
    private Requirement entryRequirement; 
    
    public Room(String name, List<GameItem> items, Requirement requirement){
        this.roomName = name; 
        this.itemsInRoom = items;
        this.npcInRoom = Optional.empty();
        this.entryRequirement = requirement; 
    }
    
    // GETTERS 
    public String getRoomName(){
        return roomName;
    }

    public List<GameItem> getItemsInRoom() {
        return itemsInRoom;
    }

    public Optional<NPC> getNpcInRoom() {
        return this.npcInRoom;
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
    	 return this.npcInRoom.isPresent() && this.npcInRoom.get().equals(n);
    }
    
    /**
     * 
     * @param n
     */
    public void setNpc(NPC n) {
        if (n.getPosition() != null && n.getPosition() != this) {
            throw new IllegalStateException(
                "NPC " + n.getRelationship() + "  is already in another room " + 
                n.getPosition().getRoomName() + ". Cannot be duplicated into: " + this.roomName
            );
        }
        npcInRoom = Optional.of(n);
    }

    /**
     * Checks if an item is in the room 
     * @param o
     * @return
     */
    public boolean hasItemRoom(GameItem o){
        return itemsInRoom.stream()
                .anyMatch(item -> item.getName().equals(o.getName()));
    }

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
    	String roomInfo = null;
    	if(this.npcInRoom.isEmpty()) {
    		roomInfo = "\nItems presenti: " + this.itemsInRoom;
    	}else {
    		roomInfo = this.roomName + "\nNPC presenti: " + this.npcInRoom.get().getRelationship() + "\nItems presenti: " + this.itemsInRoom;
    	}
        return roomInfo;
    }
}
