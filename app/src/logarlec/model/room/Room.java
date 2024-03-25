package logarlec.model.room;

import java.util.ArrayList;
import java.util.List;
import logarlec.model.actor.Actor;
import logarlec.model.items.Inventory;
import logarlec.model.items.Item;
import logarlec.model.logger.*;

/**
 * Represents a room in the game.<br>
 * A room has a capacity, a list of actors, a list of doors, a list of room
 * effects and an inventory.
 * 
 * @see IHasLocation
 */
public class Room implements IHasLocation {
    @State(name = "capacity", min = 1, max = Integer.MAX_VALUE)
    private Integer capacity = null;

    private List<Actor> actors = new ArrayList<>();
    private List<Door> doors = new ArrayList<>();
    private List<RoomEffect> roomEffects = new ArrayList<>();
    private Inventory inventory;

    /**
     * Creates a new room with the given capacity (allowed players in the room at
     * the same time)
     * 
     * @param capacity the capacity of the room
     */
    public Room(Integer capacity) {
        Logger.preConstructor(this, capacity);
        this.capacity = capacity;

        inventory = new Inventory();
        Logger.postConstructor(this);
    }

    /**
     * Splits a room into two rooms with a door between them
     */
    @Uses(fields = { "capacity" })
    public void split() {
        Logger.preExecute(this, "split");

        Room room2 = new Room(capacity);
        Door door = new Door(this, room2, false);
        doors.add(door);
        room2.doors.add(door);

        for (RoomEffect effect : roomEffects) {
            room2.addEffect(effect);
        }
        Logger.postExecute();
    }

    /**
     * Merges this room with another room.<br>
     * The new room keeps the room effects from both rooms<br>
     * Keeps the items from both rooms<br>
     * Keeps the actors from both rooms<br>
     * Kills the actors if the capacity is not enough<br>
     * 
     * @param room the room to merge with
     */
    @Uses(fields = { "capacity" })
    public void merge(Room room) {
        Logger.preExecute(this, "merge", room);

        // move effects
        for (RoomEffect effect : roomEffects) {
            room.addEffect(effect);
        }

        // move items
        inventory.dropAll(room);

        // move actors
        Integer otherCapacity = room.capacity == null ? 0 : room.capacity;
        room.capacity = Math.max(capacity, otherCapacity);

        for (int i = actors.size() - 1; i >= 0; i--) {
            // this keeps newely added actors alive
            actors.get(i).teleport(room, true);
        }

        // delete this, Door
        for (Door door : doors) {
            if (door.leadsTo(room) == this) {
                doors.remove(door);
                room.doors.remove(door);
            } else {
                room.doors.add(door);
                door.updateRoom(this, room);
            }
        }
        Logger.postExecute();
    }

    /**
     * Signals all the actors in the room if someone is attacking
     * 
     * @param attacker the actor that is attacking
     */
    public void attack(Actor attacker) {
        Logger.preExecute(this, "attack", attacker);

        for (Actor actor : actors) {
            if (actor != attacker && actor.isAlive()) {
                actor.attacked();
            }
        }

        Logger.postExecute();
    }

    /**
     * Adds a room effect to the room
     * 
     * @param effect the effect to add
     */
    public void addEffect(RoomEffect effect) {
        Logger.preExecute(this, "addEffect", effect);

        roomEffects.add(effect);

        for (Actor actor : actors) {
            effect.addEffect(actor);
        }

        Logger.postExecute();
    }

    /**
     * Handles a player entering a room<br>
     * Also applies the room effects to the actor
     * 
     * @param actor    the actor entering the room
     * @param isForced if the actor is forced to enter the room<br>
     *                 If the actor is forced, the room will accept the actor even
     *                 if it is full, but assume the actor is dead
     * @return true if the actor gets to stay alive / enter the room, false if actor
     *         has to die / or no space in the room
     */
    public boolean enter(Actor actor, boolean isForced) {
        Logger.preExecute(this, "enter", actor, isForced);

        boolean wasFull = this.isFull();

        if (!wasFull || isForced) {
            actors.add(actor);

            if (!wasFull) {
                // newcomer accept effect
                for (RoomEffect effect : roomEffects) {
                    effect.addEffect(actor);
                }

                // everyone attacks everyone
                for (Actor others : actors) {
                    others.attack();
                }
            }
        }
        return Logger.postExecute(!wasFull);
    }

    /**
     * Handles a player leaving a room
     * 
     * @param actor the actor leaving the room
     */
    public void leave(Actor actor) {
        Logger.preExecute(this, "leave", actor);
        actors.remove(actor);
        Logger.postExecute();
    }

    /**
     * Tries to revive a student in the room.<br>
     * Only revives the first dead actor found<br>
     * Only revives if the room is not full
     * 
     * @return true if a student was revived, false otherwise
     */
    public boolean revive() {
        Logger.preExecute(this, "revive");
        if (isFull()) { // not reviving if the room is full
            return Logger.postExecute(false);
        }

        for (Actor actor : actors) {
            if (actor.revive()) {
                return Logger.postExecute(true);
            }
        }
        return Logger.postExecute(false);
    }

    /**
     * Checks if the room is full
     * 
     * @return true if the room is full, false otherwise
     */
    @Uses(fields = { "capacity" })
    private boolean isFull() {
        Logger.preExecute(this, "isFull");

        boolean isFull = actors.stream().filter(actor -> actor.isAlive()).count() >= capacity;
        return Logger.postExecute(isFull);
    }

    /**
     * Drops an item in the room
     * 
     * @param item the item to drop
     */
    public void addItem(Item item) {
        Logger.preExecute(this, "addItem", item);
        item.onDrop(this);
        inventory.addItem(item);
        Logger.postExecute();
    }

    /**
     * Removes an item from the room<br>
     * Mainly used for picking up items
     * 
     * @param item the item to remove
     * @return the removed item
     */
    public Item removeItem(Item item) {
        Logger.preExecute(this, "removeItem", item);
        return Logger.postExecute(inventory.removeItem(item));
    }

    /**
     * Ticks the room<br>
     * Ticks the doors and the room effects
     * Ticks the doors and the room effects
     */
    public void tick() {
        Logger.preExecute(this, "tick");

        // tick the doors
        for (Door door : doors) {
            door.tick();
        }

        // tick the effects
        for (int i = 0; i < roomEffects.size(); i++) {
            if (roomEffects.get(i).tick() == false) {
                roomEffects.remove(i);
                i--;
            }
        }

        Logger.postExecute();
    }

    /**
     * Gets the location of the room<br>
     * Used by transistor
     * 
     * @return the location of the room
     */
    public Room getLocation() {
        Logger.preExecute(this, "getLocation");
        return Logger.postExecute(this);
    }

    /**
     * Gets the inventory of the room
     * 
     * @return the inventory of the room
     */
    public Inventory getInventory() {
        // ! hidden getter, not logged
        return inventory;
    }

    public void setCapacity(Integer capacity) {
        // ! hidden setter, not logged
        this.capacity = capacity;
    }
}
