package logarlec.model.characters;

import java.util.List;
import java.util.ArrayList;
import logarlec.model.enums.Event;
import logarlec.model.items.IItem;
import logarlec.model.items.impl.Transistor;

/**
 * Egy karakter tárgyainak kezelésére szolgáló osztály
 */
public class InventoryManager extends Inventory {
    Actor owner;
    int size;

    public InventoryManager(Actor owner, int size) {
        super();
        this.owner = owner;
        this.size = size;
    }

    /**
     * Megpróbál találni egy tárgyat, amely megvéd az adott eseménytől
     */
    public boolean autoUse(Event event) {
        List<Integer> orderedMasks = new ArrayList<>();

        Integer i = 0;

        for (IItem item : items) {
            if (item.use(owner, event)) {
                return true;
            }
            // Order the indexes by the uses left
        }

        return false;
    }

    /**
     * Megpróbálja megtalálni a tranzisztor párját
     */
    public boolean getPair(Transistor transistor) {
        for (IItem item : items) {
            if (item.use(transistor, Event.TRANSISTOR_PAIR_REQUEST)) {
                transistor.use(item, Event.TRANSISTOR_PAIR_REQUEST);
                return true;
            }
        }

        return false;
    }

    /**
     * Megpróbálja használni a tárgyat
     */
    public boolean use(int index) {
        if (0 <= index && index < size) {
            return get(index).use(owner, Event.CONTROLLER_ACTIVATED); // vagy valami hasonló
        }

        return false;
    }

    @Override
    public boolean isFull() {
        return items.size() >= size;
    }
}
