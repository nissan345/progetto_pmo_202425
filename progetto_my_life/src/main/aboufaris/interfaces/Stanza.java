package main.aboufaris.interfaces;

import java.util.List;
import main.giuseppetti.classes.NPC;
import main.neri.classe.OggettoGioco;

public interface Stanza{
    /* */
    public String getNomeStanza();
    /* */
    public List<OggettoGioco> getOggettiInStanza();
    /* */
    public List<NPC> getNpcInStanza();
    /* */
    public boolean hasNpc(NPC n);
    /* */
    public boolean hasOggettoStanza(OggettoGioco o);
    /* */
    public void addNpc(NPC n);
    /* */
    public void addOggettoStanza(OggettoGioco o);
    /* */
    public void removeOggettoStanza(OggettoGioco o);
    /* */
    public void removeNpc(NPC n);
    
}