package logarlec.model.room;

import java.util.ArrayList;
import java.util.List;
import logarlec.model.actor.Actor;
import logarlec.model.items.Inventory;
import logarlec.model.items.Item;
import logarlec.model.logger.*;

public class Room implements IHasLocation {
    @State(name = "capacity", min = 1, max = Integer.MAX_VALUE)
    private Integer capacity;

    private List<Door> doors = new ArrayList<>();
    private List<Actor> actors = new ArrayList<>();
    private List<RoomEffect> roomEffects;
    private Inventory inventory;

    public Room() {
        Logger.preConstructor(this);
        inventory = new Inventory(capacity);
        Logger.postConstructor(this);
    }

    public Room(int capacity) {
        Logger.preConstructor(this, capacity);
        this.capacity = capacity;
        inventory = new Inventory(capacity);
        Logger.postConstructor(this);
    }

    @Uses(fields = {"capacity"})
    public void split() {
        Logger.preExecute(this, "split");

        Room room2 = new Room(capacity);
        Door door = new Door(this, room2, false);
        doors.add(door);
        room2.getDoors().add(door);

        for (RoomEffect effect : roomEffects) {
            room2.addEffect(effect);
        }
        Logger.postExecute();
    }

    @Uses(fields = {"capacity"})
    public void merge(Room room) {
        Logger.preExecute(this, "merge", room);

        // move effects
        for (RoomEffect effect : roomEffects) {
            room.addEffect(effect);
        }

        // move items
        inventory.dropAll(room);

        // move actors
        room.setCapacity(Math.max(capacity, room.getCapacity()));
        for (Actor actor : actors) {
            actor.teleport(room, true);
        }

        // delete this, Door
        for (Door door : doors) {
            if (door.leadsTo(room) == this) {
                doors.remove(door);
                room.getDoors().remove(door);
                // TODO: does the door needs to nullify its room1 and room2?
            }
        }
        Logger.postExecute();
    }

    public void attack(Actor attacker) {
        Logger.preExecute(this, "attack", attacker);

        for (Actor actor : actors) {
            actor.attacked();
        }

        Logger.postExecute();
    }

    public void addEffect(RoomEffect effect) {
        Logger.preExecute(this, "addEffect", effect);
        for (Actor actor : actors) {
            effect.addEffect(actor);
        }
        Logger.postExecute();
    }

    public boolean enter(Actor actor, boolean isForced) {
        Logger.preExecute(this, "enter", actor, isForced);

        if (!this.isFull() && actor.isAlive()) {
            // newcomer accept effect
            for (RoomEffect effect : roomEffects) {
                effect.addEffect(actor);
            }

            actors.add(actor);

            // everyone attacks everyone
            for (Actor actor_ : actors) {
                actor_.attack();
            }

            Logger.postExecute(true);
            return true;
        }
        Logger.postExecute(false);
        return false;
    }

    public void leave(Actor actor) {
        Logger.preExecute(this, "leave", actor);
        actors.remove(actor);
        Logger.postExecute();
    }

    public boolean revive() {
        Logger.preExecute(this, "revive");
        for (Actor actor : actors) {
            if (actor.revive()) {
                Logger.postExecute(true);
                return true;
            }
        }
        Logger.postExecute(false);
        return false;
    }

    @Uses(fields = {"capacity"})
    private boolean isFull() {
        Logger.preExecute(this, "isFull");
        boolean isFull = actors.size() < capacity && actors.size() >= 0;
        Logger.postExecute(isFull);
        return isFull;
    }

    public void addItem(Item item) {
        Logger.preExecute(this, "addItem", item);
        inventory.addItem(item);
        item.onDrop(this);
        Logger.postExecute();
    }

    public Item removeItem(Item item) {
        Logger.preExecute(this, "removeItem", item);
        Item removedItem = inventory.removeItem(item);
        Logger.postExecute(removedItem);
        return removedItem;
    }

    public void tick() {
        // Implementation goes here
    }

    public Room getLocation() {
        Logger.preExecute(this, "getLocation");
        Logger.postExecute(this);
        return this;
    }

    public List<Door> getDoors() {
        return doors;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public Inventory getInventory() {
        return inventory;
    }
}
