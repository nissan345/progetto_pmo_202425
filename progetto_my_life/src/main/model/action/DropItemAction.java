package main.model.action;

import main.model.character.MainCharacter;
import main.model.world.Room;
import main.model.world.gameItem.GameItem;

public class DropItemAction {

	public ActionResult execute(MainCharacter character, GameItem item) {
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

