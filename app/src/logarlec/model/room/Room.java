package logarlec.model.room;

import java.util.ArrayList;
import java.util.List;
import logarlec.model.actor.Actor;
import logarlec.model.items.Inventory;
import logarlec.model.items.Item;
import logarlec.model.logger.*;

public class Room implements IHasLocation {
    @State(name = "capacity", min = 1, max = Integer.MAX_VALUE)
    private Integer capacity = null;
    
    private List<Actor> actors = new ArrayList<>();
    private List<Door> doors = new ArrayList<>();
    private List<RoomEffect> roomEffects = new ArrayList<>();
    private Inventory inventory;


    public Room(Integer capacity) {
        Logger.preConstructor(this, capacity);
        this.capacity = capacity;

        inventory = new Inventory();
        Logger.postConstructor(this);
    }

    @Uses(fields = {"capacity"})
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
        room.capacity = Math.max(capacity, room.capacity);
        for (int i = actors.size() - 1; i >= 0; i++) {
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

    public void attack(Actor attacker) {
        Logger.preExecute(this, "attack", attacker);

        for (Actor actor : actors) {
            if(actor != attacker && actor.isAlive()) {
                actor.attacked();
            }
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

        boolean wasFull = this.isFull();

        if (!wasFull || isForced) {
            actors.add(actor);

            // newcomer accept effect
            for (RoomEffect effect : roomEffects) {
                effect.addEffect(actor);
            }

            // everyone attacks everyone
            for (Actor actor_ : actors) {
                actor_.attack();
            }
        }
        return Logger.postExecute(wasFull);
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
                return Logger.postExecute(true);
            }
        }
        return Logger.postExecute(false);
    }

    @Uses(fields = {"capacity"})
    private boolean isFull() {
        Logger.preExecute(this, "isFull");

        boolean isFull = actors.stream().filter(actor -> actor.isAlive()).count() >= capacity;
        return Logger.postExecute(isFull);
    }

    public void addItem(Item item) {
        Logger.preExecute(this, "addItem", item);
        item.onDrop(this);
        inventory.addItem(item);
        Logger.postExecute();
    }

    public Item removeItem(Item item) {
        Logger.preExecute(this, "removeItem", item);
        return Logger.postExecute(inventory.removeItem(item));
    }

    public void tick() {
        Logger.preExecute(this, "tick");
        
        // tick the doors
        for (Door door : doors) {
            door.tick();
        }

        // tick the effects 
        for (RoomEffect effect : roomEffects) {
            effect.tick();
        }

        Logger.postExecute();
    }

    public Room getLocation() {
        Logger.preExecute(this, "getLocation");
        return Logger.postExecute(this);
    }

    public Inventory getInventory() {
        // ! hidden getter, not logged
        return inventory;
    }
}
