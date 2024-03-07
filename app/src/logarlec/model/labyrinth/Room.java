package logarlec.model.labyrinth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import logarlec.model.characters.Actor;
import logarlec.model.characters.Inventory;
import logarlec.model.characters.visitor.InteractionVisitor;
import logarlec.model.characters.visitor.ReviveVisitor;
import logarlec.model.enums.*;
import logarlec.model.items.IItem;

/**
 * A labirintus egy szobáját reprezentáló osztály
 */
public class Room implements IHasLocation {
    private List<Door> doors;
    private Inventory items;

    private int capacity;
    private HashMap<RoomEffect, Integer> effects;
    private List<Actor> actors;

    public Room(int capacity) {
        this.capacity = capacity;
        items = new Inventory();
        doors = new ArrayList<>();

        effects = new HashMap<>();
        actors = new ArrayList<>();
    }

    /**
     * Hozzáad egy eldobott tárgyat a szoba tárgyaihoz
     */
    public void onDroppedItem(IItem item) {
        items.addItem(item);
    }

    /**
     * Visszaadja a szoba tárgyait
     */
    public List<IItem> getItems() {
        return items.getItems();
    }

    /**
     * Kivesz egy tárgyat a szoba tárgyai közül
     */
    public IItem takeItem(int index) {
        return items.removeItem(index);
    }

    /**
     * Visszaadja a szoba tárgyainak számát
     */
    public int itemCount() {
        return items.size();
    }

    /**
     * Visszaadja a szobában található élő karakterek számát
     */
    public int liveCount() {
        int n = 0;
        for (Actor actor : actors) {
            if (actor.isAlive()) {
                n++;
            }
        }
        return n;
    }

    /**
     * Visszaadja a szobában található halott karakterek számát
     */
    public int deadCount() {
        int n = 0;
        for (Actor actor : actors) {
            if (!actor.isAlive()) {
                n++;
            }
        }
        return n;
    }

    /**
     * Eltelik egy időegység
     */
    public void tick() {
        // az effektek idejét csökkenti
        // door-ok ideje is telik
        //
        interact();
    }

    /**
     * Egy karakter próbál a szobába lépni, visszaadja, hogy sikerült-e
     */
    public boolean move(Actor actor) {
        if (liveCount() == capacity && actor.isAlive()) {
            return false;
        }

        actors.add(actor);
        actor.handleRoomEffects(effects);
        interact();

        return true;
    }

    private void interact() {
        InteractionVisitor visitor = new InteractionVisitor();

        for (Actor entity : actors) {
            entity.accepts(visitor);
        }

        visitor.interact();
    }

    public boolean tryMoveTo(Room destination, Actor actor) {
        if (!actors.contains(actor)) {
            return false;
        }

        Door neighbourDoor = null;

        for (Door door : doors) {
            if (door.getOtherRoom(this) == destination && door.isVisible()) {
                neighbourDoor = door;
                break;
            }
        }

        if (neighbourDoor == null) {
            return false;
        }

        leave(actor);
        return true;
    }

    public void leave(Actor actor) {
        actors.remove(actor);
    }

    public void addEffect(RoomEffect effect, Integer time) {
        // Add the effect
        effects.put(effect, time);

        // notify the actors
        for (Actor actor : actors) {
            actor.handleRoomEffects(effects);
        }
    }

    public List<Actor> getActors() {
        return actors;
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public Room getLocation() {
        return this;
    }

    public void setDoors(List<Door> doors) {
        this.doors = doors;
    }

    public List<Door> getDoors() {
        return doors;
    }

    public Room copyRoom() {
        return null;
    }

    public void hideDoor(int index) {
        doors.get(index).hide(10); // set custom duration
    }

    public void revive() {
        if (liveCount() == capacity) {
            return;
        }

        ReviveVisitor visitor = new ReviveVisitor();

        for (Actor actor : actors) {
            actor.accepts(visitor);
        }
    }
}
