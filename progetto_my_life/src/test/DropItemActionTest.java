import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.model.action.ActionResult;
import main.model.action.DropItemAction;
import main.model.character.MainCharacter;
import main.model.world.Room;
import main.model.world.gameItem.GameItem;
import main.model.world.gameItem.Inventory;
import main.model.requirement.AlwaysTrueRequirement;

class DropItemActionTest {

    private MainCharacter character;
    private Room room;
    private DropItemAction dropAction;
    private GameItem item;

    @BeforeEach
    void setup() {
    	// Create an empty room with AlwaysTrueRequirement
        room = new Room("TestRoom", new ArrayList<>(), new AlwaysTrueRequirement());

     // Character with empty inventory
        character = new MainCharacter("Test", null, null);
        character.getInventory().addItem(new GameItem.Builder("Dummy", "TestRoom", 1).build()); // serve perché l'inventario deve essere inizializzato
        character.getInventory().removeItem("Dummy"); // rimuoviamo subito

        // Set the current room
        character.pickCurrentRoom(room);

        dropAction = new DropItemAction();

        item = new GameItem.Builder("Mela", "TestRoom", 2).build();
    }

    @Test
    void testDropItemSuccess() {
        character.getInventory().addItem(item);

        ActionResult result = dropAction.execute(character, item);

        assertEquals("Hai lasciato Mela nella stanza.", result.getMessage());
        assertFalse(character.getInventory().hasItem("Mela"));
        assertTrue(room.hasItemRoom(item));
    }

    @Test
    void testDropItemNotInInventory() {
        ActionResult result = dropAction.execute(character, item);

        assertEquals("Non hai Mela nell'inventario!", result.getMessage());
        assertFalse(room.hasItemRoom(item));
    }
}

