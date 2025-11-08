package java.main.model.action;

import java.main.model.character.MainCharacter;
import java.main.model.world.Room;
import java.main.model.world.gameItem.GameItem;

public class DropItemAction {

	public ActionResult execute(MainCharacter character, GameItem item) {
        Room room = character.getCurrentRoom();

        boolean removed = character.getInventory()
                                   .removeItem(item.getName())
                                   .isPresent();

        if (!removed) {
            return new ActionResult("Non hai " + item.getName() + " nell'inventario!");
        }

        room.addItemRoom(item);
        return new ActionResult("Hai lasciato " + item.getName() + " nella stanza.");
    }
}

