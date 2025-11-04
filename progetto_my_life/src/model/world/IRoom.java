package model.world;

import java.util.List;
import java.util.Optional;

import model.character.NPC;
import model.world.gameItem.GameObject;
import model.world.gameItem.GameObject;



public class IRoom implements Room{


    private String nomeStanza;
    private Optional<NPC> npcInStanza;               // Indica gli oggetti presenti nella room
    private final List<GameObject> oggettiInStanza;  // Indica gli NPC presenti nella room
    
    public IRoom(String nome, List<GameObject> objects){
        this.nomeStanza = nome; 
        this.oggettiInStanza = objects;
        this.npcInStanza = Optional.empty();
    }

    public String getRoomName(){
        return nomeStanza;
    }

    public List<GameObject> getOggettiInRoom() {
        return oggettiInStanza;
    }

    public Optional<NPC> getNpcInRoom() {
        return npcInStanza;
    }

    public boolean hasNpc(NPC n){
        return this.npcInStanza.isPresent();
    }

    public boolean hasOggettoRoom(GameObject o){
        return oggettiInStanza.stream()
                .anyMatch(oggetto -> oggetto.getName().equals(o.getName()));
    }
    
    public void setNpc(NPC n) {
        npcInStanza = Optional.of(n);
    };

    public void addOggettoRoom(GameObject o){
        this.oggettiInStanza.add(o);
    };
    
    public void removeOggettoRoom(GameObject o){
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
