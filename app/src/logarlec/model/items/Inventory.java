package logarlec.model.characters;

import java.util.ArrayList;
import java.util.List;
import logarlec.model.items.IItem;

/**
 * Egy szoba vagy karakter tárgyait tároló segédosztály
 */
public class Inventory {
    protected List<IItem> items;

    public Inventory() {
        items = new ArrayList<>();
    }

    /**
     * Beletesz egy tárgyat a tárolóba
     */
    public boolean addItem(IItem item) {
        if (isFull()) {
            return false;
        }

        items.add(item);
        return true;
    }

    /**
     * Kivesz egy tárgyat a tárolóból
     */
    public IItem removeItem(int index) {
        return items.remove(index);
    }

    /**
     * Kivesz egy tárgyat a tárolóból
     */
    public void deleteItem(IItem item) {
        items.remove(item);
    }

    /**
     * Visszaadja a tárolóban tárolt tárgyakat
     */
    public List<IItem> getItems() {
        return items;
    }

    /**
     * Visszaadja a tárolóban tárolt tárgyak számát
     */
    public int size() {
        return items.size();
    }

    /**
     * Visszaad egy tárgyat a tárolóból
     */
    public IItem get(int index) {
        return items.get(index);
    }

    /**
     * Visszaadja, hogy a tároló tele van-e (by default nincs limit)
     */
    public boolean isFull() {
        return false;
    }
}
