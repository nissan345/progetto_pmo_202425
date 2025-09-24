package main.aboufaris.classes;

import java.util.List;
import main.aboufaris.interfaces.Stanza;
import main.giuseppetti.interfaces.NPC;
import main.neri.interfaces.OggettoGioco;

public class StanzaImpl implements Stanza{
    List<NPC> npcInStanza;               // Indica gli oggetti presenti nella stanza
    List<OggettoGioco> oggettiInStanza;  // Indica gli NPC presenti nella stanza

    public StanzaImpl(List<NPC> npc, List<OggettoGioco> oggetti){
        this.npcInStanza = npc;
        this.oggettiInStanza = oggetti;
    }

    
    
}