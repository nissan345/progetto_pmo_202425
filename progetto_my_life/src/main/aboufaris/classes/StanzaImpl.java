package main.aboufaris.classes;

import java.util.List;
import main.aboufaris.interfaces.Stanza;
import main.giuseppetti.classes.NPC;
import main.neri.classe.OggettoGioco;

public class StanzaImpl implements Stanza{
    private final List<NPC> npcInStanza;               // Indica gli oggetti presenti nella stanza
    private final List<OggettoGioco> oggettiInStanza;  // Indica gli NPC presenti nella stanza
    private String nomeStanza;

    public StanzaImpl(String nome, List<NPC> npc, List<OggettoGioco> oggetti){
        this.nomeStanza = nome;
        this.npcInStanza = npc;
        this.oggettiInStanza = oggetti;
    }

    public String getNomeStanza(){
        return nomeStanza;
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
    
    public void addNpc(NPC n) {
        this.npcInStanza.add(n);
    };
    public void addOggettoStanza(OggettoGioco o){
        this.oggettiInStanza.add(o);
    };
    public void removeOggettoStanza(OggettoGioco o){
        this.oggettiInStanza.remove(o);
    };

    public void removeNpc(NPC n){
        this.npcInStanza.remove(n);
    };

    @Override
    public String toString(){
        return this.nomeStanza + "\nNPC presenti: " + this.npcInStanza + "\nOggetti presenti: " + this.oggettiInStanza;
    }
}