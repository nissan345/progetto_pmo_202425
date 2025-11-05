package main.model.world;

import java.util.List;
import java.util.Optional;

import main.model.character.NPC;
import main.model.world.gameItem.GameItem;



public class Room{


    private String nomeStanza;
    private Optional<NPC> npcInStanza;               // Indica gli oggetti presenti nella room
    private final List<GameItem> oggettiInStanza;  // Indica gli NPC presenti nella room
    
    public Room(String nome, List<GameItem> items){
        this.nomeStanza = nome; 
        this.oggettiInStanza = items;
        this.npcInStanza = Optional.empty();
    }

    public String getRoomName(){
        return nomeStanza;
    }

    public List<GameItem> getOggettiInRoom() {
        return oggettiInStanza;
    }

    public Optional<NPC> getNpcInRoom() {
        return npcInStanza;
    }

    public boolean hasNpc(NPC n){
        return this.npcInStanza.isPresent();
    }

    public boolean hasOggettoRoom(GameItem o){
        return oggettiInStanza.stream()
                .anyMatch(oggetto -> oggetto.getName().equals(o.getName()));
    }
    
    public void setNpc(NPC n) {
        npcInStanza = Optional.of(n);
    };

    public void addOggettoRoom(GameItem o){
        this.oggettiInStanza.add(o);
    };
    
    public void removeOggettoRoom(GameItem o){
        this.oggettiInStanza.remove(o);
    };

    @Override
    public String toString(){
    	String stringa = null;
    	if(this.npcInStanza.isEmpty()) {
    		stringa = "\nOggetti presenti: " + this.oggettiInStanza;
    	}else {
    		stringa = this.nomeStanza + "\nNPC presenti: " + this.npcInStanza.get().getRelationship() + "\nOggetti presenti: " + this.oggettiInStanza;
    	}
        return stringa;
    }
}
