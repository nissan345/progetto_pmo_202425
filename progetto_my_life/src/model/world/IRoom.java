package model.world;

import java.util.List;
import java.util.Optional;
import model.character.MainCharacter;
import model.character.NPC;
import model.requirement.Requirement;
import model.world.gameItem.GameObject;



public class IRoom implements Room{

    private String roomName;
    private Optional<NPC> npcInRoom;               // Indica gli oggetti presenti nella room
    private final List<GameObject> objectsInRoom;  // Indica gli NPC presenti nella room 
    private Requirement entryRequirement; 
    public IRoom(String name, List<GameObject> objects, Requirement requirement){
        this.roomName = name; 
        this.objectsInRoom = objects;
        this.npcInRoom = Optional.empty();
        this.entryRequirement = requirement; 
    }

    public String getRoomName(){
        return roomName;
    }

    public List<GameObject> getObjectsInRoom() {
        return objectsInRoom;
    }

    public Optional<NPC> getNpcInRoom() {
        return npcInRoom;
    }

    @Override
    public Requirement getEntryRequirement() {
        return this.entryRequirement; 
    }

    public boolean hasNpc(NPC n){
        return this.npcInRoom.isPresent();
    }

    public boolean hasObjectRoom(GameObject o){
        return objectsInRoom.stream()
                .anyMatch(object -> object.getName().equals(o.getName()));
    }
    
    public void setNpc(NPC n) {
        npcInRoom = Optional.of(n);
    };

    @Override
    public void addObjectRoom(GameObject o){
        this.objectsInRoom.add(o);
    };
    
    @Override
    public void removeObjectRoom(GameObject o){
        this.objectsInRoom.remove(o);
    };

    @Override
    public boolean canEnter(MainCharacter character) {
        return entryRequirement.isSatisfiedBy(character); 
    }

    @Override
    public List<String> getEntryFailureReasons(MainCharacter character) {
        return this.entryRequirement.getFailureReasons(character); 
    }

    @Override
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
