package main.model.world;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * House is a map of the rooms
 */
public class House {
    
	
    private Map<String, Room> rooms;
    private Optional<Room> currentRoom;

    // CONSTRUCTOR
    public House(){
        this.rooms = new HashMap<>();
        this.currentRoom = Optional.empty();
    }
    
    // GETTERS
    public Map<String, Room> getRooms(){ return new HashMap<>(rooms); }
    

    /**
     * adds Room inside the house
     * @param s
     */
    public void addRoom(Room s){this.rooms.put(s.getRoomName(), s); }

    /**
     * @return currentRoom, if present
     * @throw UnsupportedOperationException if there's no currentRoom
     */
    public Optional<Room> getCurrentRoom(){
    	if(currentRoom.isEmpty()) {
    		throw new UnsupportedOperationException("You need to enter a room, to get inside");
    	}
    	return currentRoom;
    }

    /**
     * Method used to enter a specific room.
     * @param name
     * @return Optional of Room, if the Room is present, if the room is not in the house, returns Optional.empty
     */
    public Optional<Room> enterRoom(String name){
        Room s = this.rooms.get(name);
        if (s != null) {
            this.currentRoom = Optional.of(s);
            return Optional.of(s);
        }
        return Optional.empty(); // Can't find a room
    }

    /**
     * Method to exit a specific room
     * @return the map of rooms
     * @throws UnsupportedOperationException if there's no currentRoom
     */
    public Map<String, Room> exitRoom(){
        if(currentRoom.isEmpty()){
            throw new UnsupportedOperationException();
        }
        return this.rooms;        
    }

    public Room getRoom(String s){return this.rooms.get(s);}
}

