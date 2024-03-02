package logarlec.model.labyrinth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import logarlec.model.characters.Actor;
import logarlec.model.enums.*;
import logarlec.model.items.Item;

public class Room implements IHasLocation {
    private List<Door> doors;
    private List<Item> items;

    private int capacity;
    private HashMap<RoomEffect, Integer> effects;
    public List<Actor> actors;

    // private List<Student> students;
    // private List<Professor> professors;

    public Room(int capacity) {
        this.capacity = capacity;
        items = new ArrayList();
        doors = new ArrayList<>();

        effects = new HashMap<>();
        actors = new ArrayList<>();
    }

    public void droppedItem(Item item) {
        items.add(item);
    }

    public List<Item> getItems() {
        return items;
    }

    public Item takeItem(Item item) {
        if (items.remove(item)) {
            return item;
        }

        return null;
    }

    public int itemCount() {
        return items.size();
    }

    public int actorCount() {
        int n = 0;
        for (Actor actor : actors) {
            if (actor.isAlive()) {
                n++;
            }
        }

        return n;
    }

    public void Tick() {}

    public boolean Move(Actor actor, boolean forced) {
        if (actorCount() == capacity && !forced) {
            return false;
        }
        // Ha meg forced volt akkor meg adjuk hozzá és öljük meg xd
        // TODO - szerintem ez a Room manager dolga. Nem a szoba feladata megölni
        actors.add(actor);
        return true;
    }

    public void AddEffect(RoomEffect effect, Integer time) {
        // Add the effect
        effects.put(effect, time);

        for (Actor actor : actors) {
            // notify the actors
            // TODO - ezt tudnom kéne, hogy hogyan kezeli az actor
            actor.HandleRoomEffect(effect);
        }
    }

    public Boolean AreStudensInvincible() {
        return false; // true if room is WET
    }

    @Override
    public Room getLocation() {
        return this;
    }

    /*
     * public List<Stud return students; } public List<Profes return professors; } public void moveOut(Professor
     * professors.remove(professor); } public void moveOut(Stude students.remove(student); }
     */

    public List<Door> getDoors() {
        return doors;
    }

    public void hideDoors(Integer[] idxs) {
        for (Integer idx : idxs) {
            doors.get(idx).hide(10); // set custom duration
        }
    }
}
