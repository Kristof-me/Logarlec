package logarlec.model.room;

import java.util.ArrayList;
import java.util.List;
import logarlec.model.actor.Actor;
import logarlec.model.items.Inventory;
import logarlec.model.items.Item;
import logarlec.model.proxy.*;

public class Room {
    private int capacity;
    private List<Actor> actors = new ArrayList<>();
    private List<RoomEffect> roomEffects;
    private Inventory inventory;

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

    @LogFunction
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
}
