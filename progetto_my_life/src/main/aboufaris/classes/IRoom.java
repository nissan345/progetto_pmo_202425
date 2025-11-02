package main.aboufaris.classes;

import java.util.List;
import java.util.Optional;
import main.aboufaris.interfaces.Room;
import main.giuseppetti.classes.NPC;
import main.neri.classes.OggettoGioco;

public class IRoom implements Room{
    private String nomeStanza;
    private Optional<NPC> npcInStanza;               // Indica gli oggetti presenti nella stanza
    private final List<OggettoGioco> oggettiInStanza;  // Indica gli NPC presenti nella stanza
    
    public IRoom(String nome, List<OggettoGioco> oggetti){
        this.nomeStanza = nome; 
        this.oggettiInStanza = oggetti;
        this.npcInStanza = Optional.empty();
    }

    public String getRoomName(){
        return nomeStanza;
    }

    public List<OggettoGioco> getOggettiInRoom() {
        return oggettiInStanza;
    }

    public Optional<NPC> getNpcInRoom() {
        return npcInStanza;
    }

    public boolean hasNpc(NPC n){
        return this.npcInStanza.isPresent();
    }

    public boolean hasOggettoRoom(OggettoGioco o){
        return oggettiInStanza.stream()
                .anyMatch(oggetto -> oggetto.getNome().equals(o.getNome()));
    }
    
    public void setNpc(NPC n) {
        npcInStanza = Optional.of(n);
    };

    public void addOggettoRoom(OggettoGioco o){
        this.oggettiInStanza.add(o);
    };
    
    public void removeOggettoRoom(OggettoGioco o){
        this.oggettiInStanza.remove(o);
    };

    @Override
    public String toString(){
    	String stringa = null;
    	if(this.npcInStanza.isEmpty()) {
    		stringa = "\nOggetti presenti: " + this.oggettiInStanza;
    	}else {
    		stringa = this.nomeStanza + "\nNPC presenti: " + this.npcInStanza.get().getRelazione() + "\nOggetti presenti: " + this.oggettiInStanza;
    	}
        return stringa;
    }
}