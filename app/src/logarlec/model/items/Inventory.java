package logarlec.model.items;

import java.util.List;
import logarlec.model.room.Room;

public class Inventory {
    private int size;
    private List<Item> items;

    private boolean isFull() {
        // Implementation goes here
        return false;
    }

    public boolean addItem(Item item) {
        // Implementation goes here
        return false;
    }

    public Item removeItem(Item item) {
        // Implementation goes here
        return null;
    }

    public void dropAll(Room target) {
        // Implementation goes here
    }

    public void acceptVisitor(ItemVisitor visitor) {
        // Implementation goes here
    }
}
