package main.aboufaris.classes;

import java.util.List;
import main.aboufaris.interfaces.Stanza;
import main.giuseppetti.interfaces.NPC;
import main.neri.interfaces.OggettoGioco;

public class StanzaImpl implements Stanza{
    private final List<NPC> npcInStanza;               // Indica gli oggetti presenti nella stanza
    private final List<OggettoGioco> oggettiInStanza;  // Indica gli NPC presenti nella stanza

    public StanzaImpl(List<NPC> npc, List<OggettoGioco> oggetti){
        this.npcInStanza = npc;
        this.oggettiInStanza = oggetti;
    }

    public List<OggettoGioco> getOggettiInStanza() {
        return oggettiInStanza;
    }

    public List<NPC> getNpcInStanza() {
        return npcInStanza;
    }

    public boolean hasNpc(NPC n){
        return this.npcInStanza.contains(n);
    }

    public boolean hasOggettoStanza(OggettoGioco o){
        return this.oggettiInStanza.contains(o);
    }
    
    public void addNpc(NPC n) throws IllegalArgumentException{
        if(hasNpc(n)){
            throw new IllegalArgumentException();
        }
        this.npcInStanza.add(n);
    };
    public void addOggettoStanza(OggettoGioco o)throws IllegalArgumentException{
        if(hasOggettoStanza(o))
            throw new IllegalArgumentException();
        this.oggettiInStanza.add(o);
    };
    public void removeOggettoStanza(OggettoGioco o)throws IllegalArgumentException{
        if(!hasOggettoStanza(o))
            throw new IllegalArgumentException();
        this.oggettiInStanza.remove(o);
    };

    public void removeNpc(NPC n)throws IllegalArgumentException{
        if(!hasNpc(n)){
            throw new IllegalArgumentException();
        }
        this.npcInStanza.remove(n);
    };
}