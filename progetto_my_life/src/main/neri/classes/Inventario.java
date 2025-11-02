package main.neri.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Inventory class — manages the list of items that a player can carry.
 * Author: Ali 🌸
 */
public class Inventario {

    private final int capacity;       // maximum space available
    private int usedSpace = 0;        // current used space
    private final List<OggettoGioco> items = new ArrayList<>();  // list of carried items

    public Inventario(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Adds an item to the inventory if there is enough space.
     * @param item The item to add.
     * @return true if added successfully, false if not enough space.
     */
    public boolean addItem(OggettoGioco item) {
        if (usedSpace + item.getDimensione() > capacity) {
            return false;
        }
        items.add(item);
        usedSpace += item.getDimensione();
        return true;
    }

    /**
     * Removes an item by its ID.
     * @param id The ID of the item.
     * @return The removed item (if found).
     */
    public Optional<OggettoGioco> removeItem(String id) {
        Optional<OggettoGioco> found = items.stream()
                .filter(i -> i.getNome().equals(id))
                .findFirst();
        found.ifPresent(i -> {
            items.remove(i);
            usedSpace -= i.getDimensione();
        });
        return found;
    }

    /** Checks if an item is present in the inventory. */
    public boolean hasItem(String id) {
        return items.stream().anyMatch(i -> i.getNome().equals(id));
    }

    /** Returns a read-only list of items. */
    public List<OggettoGioco> getItems() {
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

