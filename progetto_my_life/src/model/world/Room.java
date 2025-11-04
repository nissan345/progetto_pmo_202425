package model.world;

import java.util.List;
import java.util.Optional;
import model.character.MainCharacter;
import model.character.NPC;
import model.requirement.Requirement;
import model.world.gameItem.GameObject;

public interface Room{
    /* */

    public String getRoomName();

    /* */
    public List<GameObject> getObjectsInRoom();
    /* */
    public Optional<NPC> getNpcInRoom();
    /* */
    public Requirement getEntryRequirement();
    /* */
    public boolean hasNpc(NPC n);
    /* */
    public boolean hasObjectRoom(GameObject o);
    /* */
    public void setNpc(NPC npc);
    /* */
    public void addObjectRoom(GameObject o);
    /* */
    public void removeObjectRoom(GameObject o);
    /* */
    public boolean canEnter(MainCharacter character);
    /*  */
    public List<String> getEntryFailureReasons(MainCharacter character);
}