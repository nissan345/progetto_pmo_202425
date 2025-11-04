package model.world;

import java.util.List;
import java.util.Optional;

import model.character.NPC;
import model.world.gameItem.GameObject;

public interface Room{
    /* */

    public String getRoomName();

    /* */
    public List<GameObject> getOggettiInRoom();
    /* */
    public Optional<NPC> getNpcInRoom();
    /* */
    public boolean hasNpc(NPC n);
    /* */
    public boolean hasOggettoRoom(GameObject o);
    /* */
    public void setNpc(NPC npc);
    /* */
    public void addOggettoRoom(GameObject o);
    /* */
    public void removeOggettoRoom(GameObject o);

}