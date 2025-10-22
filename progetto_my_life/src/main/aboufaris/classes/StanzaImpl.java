package main.aboufaris.classes;

import java.util.List;
import java.util.Optional;
import main.aboufaris.interfaces.Stanza;
import main.giuseppetti.classes.NPC;
import main.neri.classe.OggettoGioco;

public class StanzaImpl implements Stanza{
    private String nomeStanza;
    private Optional<NPC> npcInStanza;               // Indica gli oggetti presenti nella stanza
    private final List<OggettoGioco> oggettiInStanza;  // Indica gli NPC presenti nella stanza
    
    public StanzaImpl(String nome, List<OggettoGioco> oggetti){
        this.nomeStanza = nome;
        this.oggettiInStanza = oggetti;
        this.npcInStanza = Optional.empty();
    }

    public String getNomeStanza(){
        return nomeStanza;
    }

    public List<OggettoGioco> getOggettiInStanza() {
        return oggettiInStanza;
    }

    public Optional<NPC> getNpcInStanza() {
        return npcInStanza;
    }

    public boolean hasNpc(NPC n){
        return this.npcInStanza.isPresent();
    }

    public boolean hasOggettoStanza(OggettoGioco o){
        return this.oggettiInStanza.contains(o);
    }
    
    public void setNpc(NPC n) {
        npcInStanza = Optional.of(n);
    };

    public void addOggettoStanza(OggettoGioco o){
        this.oggettiInStanza.add(o);
    };
    
    public void removeOggettoStanza(OggettoGioco o){
        this.oggettiInStanza.remove(o);
    };

    @Override
    public String toString(){
        return this.nomeStanza + "\nNPC presenti: " + this.npcInStanza + "\nOggetti presenti: " + this.oggettiInStanza;
    }
}