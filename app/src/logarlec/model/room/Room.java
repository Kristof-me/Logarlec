package logarlec.model.room;

import java.util.ArrayList;
import java.util.List;
import logarlec.model.actor.Actor;
import logarlec.model.items.Inventory;
import logarlec.model.items.Item;
import logarlec.model.logger.*;

public class Room implements IHasLocation {
    @State(name = "capacity", popupQuestion = "Enter the capacity of the room", min = 1, max = 100)
    private Integer capacity;

    private List<Door> doors = new ArrayList<>();
    private List<Actor> actors = new ArrayList<>();
    private List<RoomEffect> roomEffects;
    private Inventory inventory;

    public Room() {
        Logger.preExecute(this);
        inventory = new Inventory(capacity);
        Logger.postExecute();
    }

    public Room(int capacity) {
        Logger.preExecute(this);
        this.capacity = capacity;
        inventory = new Inventory(capacity);
        Logger.postExecute();
    }

    public void split() {
        Logger.preExecute(this);

        Room room2 = new Room(capacity);
        Door door = new Door(this, room2, false);
        doors.add(door);
        room2.getDoors().add(door);

        for (RoomEffect effect : roomEffects) {
            room2.addEffect(effect);
        }
        Logger.postExecute();
    }

    public void merge(Room room) {
        Logger.preExecute(this, room);

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
        Logger.preExecute(this, attacker);

        for (Actor actor : actors) {
            actor.attacked();
        }

        Logger.postExecute();
    }

    public void addEffect(RoomEffect effect) {
        // Implementation goes here
    }

    @Uses(fields = {"capacity"})
    public boolean enter(Actor actor, boolean isForced) {
        Logger.preExecute(this, "enter", actor, isForced);
        // Implementation goes here
        actors.add(actor);
        actor.mockFunction(13, "alma");
        return Logger.postExecute(true);
    }

    public void leave(Actor actor) {
        // Implementation goes here
    }

    public boolean revive() {
        // Implementation goes here
        return false;
    }

    private boolean isFull() {
        // Implementation goes here
        return false;
    }

    public void addItem(Item item) {
        // Implementation goes here
    }

    public Item removeItem(Item item) {
        // Implementation goes here
        return null;
    }

    public void tick() {
        // Implementation goes here
    }

    public Room getLocation() {
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
}
