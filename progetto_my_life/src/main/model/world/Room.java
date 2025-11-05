package main.model.world;

import java.util.List;
import java.util.Optional;

import main.model.character.NPC;
import main.model.world.gameItem.GameItem;

public interface Room{
    /* */

    public String getRoomName();

    /* */
    public List<GameItem> getOggettiInRoom();
    /* */
    public Optional<NPC> getNpcInRoom();
    /* */
    public boolean hasNpc(NPC n);
    /* */
    public boolean hasOggettoRoom(GameItem o);
    /* */
    public void setNpc(NPC npc);
    /* */
    public void addOggettoRoom(GameItem o);
    /* */
    public void removeOggettoRoom(GameItem o);

}