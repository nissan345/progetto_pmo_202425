package main.aboufaris.interfaces;

<<<<<<< Updated upstream:progetto_my_life/src/main/aboufaris/interfaces/Stanza.java
public interface Stanza{
=======
import java.util.List;
import java.util.Optional;
import main.giuseppetti.classes.NPC;
import main.neri.classes.OggettoGioco;

public interface Room{
    /* */
    public String getNameRoom();
    /* */
    public List<OggettoGioco> getOggettiInRoom();
    /* */
    public Optional<NPC> getNpcInRoom();
    /* */
    public boolean hasNpc(NPC n);
    /* */
    public boolean hasOggettoRoom(OggettoGioco o);
    /* */
    public void setNpc(NPC npc);
    /* */
    public void addOggettoRoom(OggettoGioco o);
    /* */
    public void removeOggettoRoom(OggettoGioco o);
>>>>>>> Stashed changes:progetto_my_life/src/main/aboufaris/interfaces/Room.java

}