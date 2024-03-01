package logarlec.model.labyrinth;

import java.util.List;
import logarlec.model.characters.Actor;
import logarlec.model.effects.Effect;
import logarlec.model.items.Item;

public class Room {
    private List<Door> doors;
    private List<Item> items;
    private List<Actor> actors;
    private List<Effect> effects;
    private int capacity;

    public Room(int capacity) {
        this.capacity = capacity;
    }

    public Item takeItem(Item item) {
        boolean success = items.remove(item);

        if (success) {
            return item;
        } else {
            return null;
        }
    }

    public void addDroppedItem(Item item) {
        items.add(item);
    }

    public int peekActorCount() {
        return actors.size();
    }

    public int peekItemCount() {
        return items.size();
    }

    public void Tick() {}

    public boolean Move(Actor actor) {
        if (actors.size() < capacity) {
            actors.add(actor);
            return true;
        } else {
            return false;
        }
    }

}
