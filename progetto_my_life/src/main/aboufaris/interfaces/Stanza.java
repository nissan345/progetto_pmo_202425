package main.aboufaris.interfaces;

import java.util.List;
import java.util.Optional;
import main.giuseppetti.classes.NPC;
import main.neri.classe.OggettoGioco;

public interface Stanza{
    /* */
    public String getNomeStanza();
    /* */
    public List<OggettoGioco> getOggettiInStanza();
    /* */
    public Optional<NPC> getNpcInStanza();
    /* */
    public boolean hasNpc(NPC n);
    /* */
    public boolean hasOggettoStanza(OggettoGioco o);
    /* */
    public void setNpc(NPC npc);
    /* */
    public void addOggettoStanza(OggettoGioco o);
    /* */
    public void removeOggettoStanza(OggettoGioco o);

    
}