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

    private List<Actor> actors = new ArrayList<>();
    private List<RoomEffect> roomEffects;
    private Inventory inventory;

    public Room() {
        // Implementation goes here
    }

    public void split() {
        // Implementation goes here
    }

    public void merge(Room room) {
        // Implementation goes here
    }

    public void attack(Actor attacker) {
        // Implementation goes here
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
}
