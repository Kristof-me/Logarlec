package logarlec.model.characters;

import logarlec.model.labyrinth.Room;
import logarlec.model.labyrinth.IHasLocation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import logarlec.model.characters.visitor.*;
import logarlec.model.enums.*;
import logarlec.model.items.IItem;

/**
 * Egy karaktert reprezentáló osztály.
 */
public abstract class Actor implements IHasLocation, IVisitable {
    protected InventoryManager inventoryManager;
    protected boolean isAlive;
    protected Room room;
    protected HashMap<ActorEffect, Integer> effects;

    protected Actor(Room spawnRoom, int inventorySize) {
        room = spawnRoom;
        inventoryManager = new InventoryManager(this, inventorySize);

        isAlive = true;
    }

    /**
     * Visszaadja a karakter tartózkodási helyét
     */
    public Room getLocation() {
        return room;
    }

    /**
     * Eltelt egy időegység
     */
    public void step() {
        // ez tickeli a hatásokat / tárgyakat stb.
    }

    /**
     * Használja az adott tárgyat
     */
    public boolean use(int index) {
        return inventoryManager.use(index);
    }

    /**
     * Eldobja az adott tárgyat
     */
    public void drop(int index) {
        IItem dropped = inventoryManager.removeItem(index);

        if (dropped != null) {
            room.onDroppedItem(dropped);
        }
    }

    /**
     * Megnézi a szobában található tárgyakat
     */
    public List<IItem> search() {
        return room.getItems();
    }

    /**
     * Felvesz egy tárgyat a szobából
     */
    protected boolean pickUp(int index) {
        IItem got = room.takeItem(index);

        if (got == null)
            return false;

        if (!inventoryManager.addItem(got)) { // ha nem sikerül eltenni
            room.onDroppedItem(got); // akkor adjuk vissza
            return false;
        }

        got.onPickup(this);
        return true;
    }

    /**
     * Eltávolít egy tárgyat az inventoryból
     */
    public void deleteItem(IItem item) {
        inventoryManager.deleteItem(item);
    }

    /**
     * Megpróbál átlépni egy másik szobába
     */
    public boolean move(Room destination) {
        Room current = room;

        // lehet-e moveolni tovább
        if (!room.tryMoveTo(destination, this)) {
            return false;
        }

        // megpróbálunk átmenni
        if (destination.move(this)) {
            room = destination;
            return true;
        }

        // ha nem sikerül akkor biztos visszafogadnak
        current.move(this);
        return false;
    }

    /**
     * Teleport
     */
    public boolean teleport(Room destination) {
        if (destination.move(this)) {
            room.leave(this);
            room = destination;
            return true;
        }

        return false;
    }

    /**
     * Rátesz egy hatást a karakterre
     */
    public void addEffect(ActorEffect effect, int duration) {
        effects.put(effect, duration);
    }

    /**
     * Lekezeli a szobában lévő hatásokat
     */
    public abstract void handleRoomEffects(Map<RoomEffect, Integer> effects);

    /**
     * Megöli a karaktert
     */
    public void kill() {
        // public, mert a room manager is meg tudja hívni
        isAlive = false;
    }

    /**
     * Visszaadja, hogy a karakter életben van-e
     */
    public boolean isAlive() {
        return isAlive;
    }

    /**
     * Visszaadja az inventory manager-t
     */
    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }
}
