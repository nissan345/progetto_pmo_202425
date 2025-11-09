package main.model.world;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class House {
    

    private Map<String, Room> rooms;
    private Optional<Room> currentRoom;

    public House(){
        this.rooms = new HashMap<>();
        this.currentRoom = Optional.empty();
    }

    public void addRoom(Room s){
        this.rooms.put(s.getRoomName(), s);
    }

    public Optional<Room> getCurrentRoom(){
    	if(currentRoom.isEmpty()) {
    		throw new UnsupportedOperationException("You need to enter a room, to get inside");
    	}
        return currentRoom;
    }

    public Map<String, Room> getRooms(){ 
        return new HashMap<>(rooms); 
    }

    public Optional<Room> enterRoom(String nome){
        Room s = this.rooms.get(nome);
        if (s != null) {
            this.currentRoom = Optional.of(s);
            return Optional.of(s);
        }
        return Optional.empty(); // Can't find a room
    }

    public Map<String, Room> exitRoom(){
        if(currentRoom.isEmpty()){
            throw new UnsupportedOperationException();
        }
        return this.rooms;        
    }

    public Room getRoom(String s){
        return this.rooms.get(s);
    }
}

