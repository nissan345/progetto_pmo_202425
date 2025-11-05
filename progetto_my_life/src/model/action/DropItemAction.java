package model.action;

import model.character.MainCharacter;
import model.world.Room;
import model.world.gameItem.GameObject;

public class DropItemAction {

    public ActionResult execute(MainCharacter character, GameObject item) {
        Room room = character.getCurrentRoom();

        boolean removed = character.getInventory()
                                   .removeItem(item.getName())
                                   .isPresent();

        if (!removed) {
            return new ActionResult("Non hai " + item.getName() + " nell'inventario!");
        }

        room.addOggettoRoom(item);
        return new ActionResult("Hai lasciato " + item.getName() + " nella stanza.");
    }
}

