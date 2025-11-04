package model.world.gameItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Inventory class — manages the list of items that a player can carry.
 */
public class Inventory {

    private final int capacity;        // maximum space available
    private int usedSpace = 0;         // currently used space
    private final List<GameObject> items = new ArrayList<>();  // list of carried items

    public Inventory(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Adds an item to the inventory if there is enough space.
     * @param item The item to add.
     * @return true if added successfully, false if not enough space.
     */
    public boolean addItem(GameObject item) {
        if (usedSpace + item.getSize() > capacity) {
            return false;
        }
        items.add(item);
        usedSpace += item.getSize();
        return true;
    }

    /**
     * Removes an item by its name.
     * @param name The name of the item.
     * @return The removed item (if found).
     */
    public Optional<GameObject> removeItem(String name) {
        Optional<GameObject> found = items.stream()
                .filter(i -> i.getName().equals(name))
                .findFirst();
        found.ifPresent(i -> {
            items.remove(i);
            usedSpace -= i.getSize();
        });
        return found;
    }

    /** Checks if an item is present in the inventory. */
    public boolean hasItem(String name) {
        return items.stream().anyMatch(i -> i.getName().equals(name));
    }

    /** Returns a read-only list of items. */
    public List<GameObject> getItems() {
        return List.copyOf(items);
    }

    /** Returns the remaining capacity. */
    public int getRemainingCapacity() {
        return capacity - usedSpace;
    }

    public int getUsedSpace() {
        return usedSpace;
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "capacity=" + capacity +
                ", usedSpace=" + usedSpace +
                ", items=" + items.size() +
                '}';
    }
}
