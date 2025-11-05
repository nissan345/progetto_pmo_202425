package model.action;

import model.character.MainCharacter;
import model.world.Room;
import model.world.gameItem.GameObject;

public class PickItemAction {

    public ActionResult execute(MainCharacter character, GameObject item) {
        Room room = character.getCurrentRoom();

        if (!room.hasOggettoRoom(item)) {
            return new ActionResult("L'oggetto non è presente nella stanza!");
        }

        boolean added = character.getInventory().addItem(item);
        if (!added) {
            return new ActionResult("Inventario pieno! Non puoi raccogliere " + item.getName());
        }

        room.removeOggettoRoom(item);
        return new ActionResult("Hai raccolto " + item.getName() + "!");
    }
}

