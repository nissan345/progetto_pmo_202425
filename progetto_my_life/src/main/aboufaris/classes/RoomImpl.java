package main.aboufaris.classes;

import java.util.List;
import java.util.Optional;
import main.aboufaris.interfaces.Room;
import main.giuseppetti.classes.NPC;
import main.neri.classes.OggettoGioco;

public class RoomImpl implements Room{
    private String nameRoom;
    private Optional<NPC> npcInRoom;               // Indica gli oggetti presenti nella room
    private final List<OggettoGioco> oggettiInRoom;  // Indica gli NPC presenti nella room
    
    public RoomImpl(String name, List<OggettoGioco> oggetti){
        this.nameRoom = name; 
        this.oggettiInRoom = oggetti;
        this.npcInRoom = Optional.empty();
    }

    public String getNameRoom(){
        return nameRoom;
    }

    public List<OggettoGioco> getOggettiInRoom() {
        return oggettiInRoom;
    }

    public Optional<NPC> getNpcInRoom() {
        return npcInRoom;
    }

    public boolean hasNpc(NPC n){
        return this.npcInRoom.isPresent();
    }

    public boolean hasOggettoRoom(OggettoGioco o){
        return oggettiInRoom.stream()
                .anyMatch(oggetto -> oggetto.getName().equals(o.getName()));
    }
    
    public void setNpc(NPC n) {
        npcInRoom = Optional.of(n);
    };

    public void addOggettoRoom(OggettoGioco o){
        this.oggettiInRoom.add(o);
    };
    
    public void removeOggettoRoom(OggettoGioco o){
        this.oggettiInRoom.remove(o);
    };

    @Override
    public String toString(){
    	String stringa = null;
    	if(this.npcInRoom.isEmpty()) {
    		stringa = "\nOggetti presenti: " + this.oggettiInRoom;
    	}else {
    		stringa = this.nameRoom + "\nNPC presenti: " + this.npcInRoom.get().getRelazione() + "\nOggetti presenti: " + this.oggettiInRoom;
    	}
        return stringa;
    }
}