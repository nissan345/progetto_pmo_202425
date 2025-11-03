package main.aboufaris.interfaces;

<<<<<<< HEAD
=======
<<<<<<< Updated upstream:progetto_my_life/src/main/aboufaris/interfaces/Stanza.java
public interface Stanza{
=======
>>>>>>> nicxole
import java.util.List;
import java.util.Optional;
import main.giuseppetti.classes.NPC;
import main.neri.classes.OggettoGioco;

public interface Room{
    /* */
<<<<<<< HEAD
    public String getRoomName();
=======
    public String getNameRoom();
>>>>>>> nicxole
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
<<<<<<< HEAD

    
=======
>>>>>>> Stashed changes:progetto_my_life/src/main/aboufaris/interfaces/Room.java

>>>>>>> nicxole
}