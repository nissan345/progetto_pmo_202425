package ActionTest;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.model.action.ActionResult;
import main.model.action.PickItemAction;
import main.model.character.MainCharacter;
import main.model.world.Room;
import main.model.world.gameItem.GameItem;
import main.model.requirement.requirementImpl.*;

class PickItemActionTest {

    private MainCharacter character;
    private Room room;
    private PickItemAction pickAction;
    private GameItem item;

    @BeforeEach
    void setup() {
        room = new Room("TestRoom", new ArrayList<>(), new AlwaysTrueRequirement());
        character = new MainCharacter("Test", null, null);

     // Set the current room
        character.pickCurrentRoom(room);

        pickAction = new PickItemAction();
        item = new GameItem.Builder("Mela", "TestRoom", 2).build();
    }

    @Test
    void testPickItemSuccess() {
        room.addItemRoom(item);

        ActionResult result = pickAction.execute(character, item);

        assertEquals("Hai raccolto Mela!", result.getMessage());
        assertTrue(character.getInventory().hasItem("Mela"));
        assertFalse(room.hasItemRoom(item));
    }

    @Test
    void testPickItemNotInRoom() {
        ActionResult result = pickAction.execute(character, item);

        assertEquals("L'oggetto non è presente nella stanza!", result.getMessage());
        assertFalse(character.getInventory().hasItem("Mela"));
    }

    @Test
    void testPickItemInventoryFull() {
        room.addItemRoom(item);
        //we fill the inventory beyond capacity
        for (int i = 0; i < 10; i++) {
            character.getInventory().addItem(new GameItem.Builder("Oggetto" + i, "TestRoom", 1).build());
        }

        ActionResult result = pickAction.execute(character, item);

        assertEquals("Inventario pieno! Non puoi raccogliere Mela", result.getMessage());
        assertTrue(room.hasItemRoom(item));
        assertFalse(character.getInventory().hasItem("Mela"));
    }
}
