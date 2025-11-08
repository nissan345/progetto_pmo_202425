package main.model.action;

import main.model.character.MainCharacter;
import main.model.world.*;
import main.model.world.gameItem.GameItem;

public class PickItemAction {

	public ActionResult execute(MainCharacter character, GameItem item) {
        Room room = character.getCurrentRoom();

        if (!room.hasItemRoom(item)) {
            return new ActionResult("L'oggetto non è presente nella stanza!");
        }

        boolean added = character.getInventory().addItem(item);
        if (!added) {
            return new ActionResult("Inventario pieno! Non puoi raccogliere " + item.getName());
        }

        room.removeItemRoom(item);
        return new ActionResult("Hai raccolto " + item.getName() + "!");
    }
}

