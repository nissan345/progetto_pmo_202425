import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import main.model.world.gameItem.GameItem;
import main.model.world.gameItem.Inventory;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {

    private Inventory inventory;

    @BeforeEach
    void setup() {
        inventory = new Inventory(10); // capacità massima 10
    }

    @Test
    void testAddItemSuccess() {
        GameItem apple = new GameItem.Builder("Mela", "Cucina", 2).build();
        boolean added = inventory.addItem(apple);

        assertTrue(added);
        assertEquals(2, inventory.getUsedSpace());
        assertTrue(inventory.hasItem("Mela"));
        assertEquals(8, inventory.getRemainingCapacity());
    }

    @Test
    void testAddItemFailDueToCapacity() {
        GameItem bigBox = new GameItem.Builder("Scatola", "Sgabuzzino", 15).build();
        boolean added = inventory.addItem(bigBox);

        assertFalse(added);
        assertEquals(0, inventory.getUsedSpace());
        assertFalse(inventory.hasItem("Scatola"));
    }

    @Test
    void testRemoveItemSuccess() {
        GameItem apple = new GameItem.Builder("Mela", "Cucina", 2).build();
        inventory.addItem(apple);

        Optional<GameItem> removed = inventory.removeItem("Mela");

        assertTrue(removed.isPresent());
        assertEquals("Mela", removed.get().getName());
        assertFalse(inventory.hasItem("Mela"));
        assertEquals(0, inventory.getUsedSpace());
    }

    @Test
    void testRemoveItemNotFound() {
        Optional<GameItem> removed = inventory.removeItem("Arancia");
        assertFalse(removed.isPresent());
    }

    @Test
    void testHasItem() {
        GameItem bread = new GameItem.Builder("Pane", "Cucina", 3).build();
        inventory.addItem(bread);

        assertTrue(inventory.hasItem("Pane"));
        assertFalse(inventory.hasItem("Latte"));
    }

    @Test
    void testGetItemsReturnsImmutableList() {
        GameItem bread = new GameItem.Builder("Pane", "Cucina", 3).build();
        inventory.addItem(bread);

        var items = inventory.getItems();

        assertEquals(1, items.size());
        assertThrows(UnsupportedOperationException.class, () -> items.add(new GameItem.Builder("Formaggio", "Cucina", 1).build()));
    }

    @Test
    void testToStringContainsUsefulInfo() {
        GameItem apple = new GameItem.Builder("Mela", "Cucina", 2).build();
        inventory.addItem(apple);
        String result = inventory.toString();

        assertTrue(result.contains("capacity=10"));
        assertTrue(result.contains("usedSpace=2"));
    }
}

