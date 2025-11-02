package main.aboufaris.classes;
import main.aboufaris.interfaces.Casa;
<<<<<<< Updated upstream
public class CasaImpl implements Casa{
    
=======
import main.aboufaris.interfaces.Room;

public class CasaImpl implements Casa{
    

    private Map<String, Room> stanze;
    private Optional<Room> currentRoom;

    public CasaImpl(){
        this.stanze = new HashMap<>();
        this.currentRoom = Optional.empty();
    }

  

    public void aggiungiRoom(Room s){
        this.stanze.put(s.getNameRoom(), s);
    }

    public Optional<Room> getCurrentRoom(){
        return currentRoom;
    }

    public Map<String, Room> getStanze(){ 
        return new HashMap<>(stanze); 
    }

    public Optional<Room> entraInRoom(String name){
        Room s = this.stanze.get(name);
        if (s != null) {
            this.currentRoom = Optional.of(s);
            return Optional.of(s);
        }
        return Optional.empty(); // Room non trovata
    }

    public Map<String, Room> esciDaRoom(){
        if(currentRoom.isEmpty()){
            throw new UnsupportedOperationException();
        }
        return this.stanze;        
    }

    public Room getRoom(String s){
        return this.stanze.get(s);
    }
>>>>>>> Stashed changes
}
