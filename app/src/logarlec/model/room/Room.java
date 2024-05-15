package logarlec.model.room;

import java.util.ArrayList;
import java.util.List;

import logarlec.control.rendering.ItemHolderViewFactory;
import logarlec.model.GameObject;
import logarlec.model.actor.Actor;
import logarlec.model.items.Inventory;
import logarlec.model.items.Item;
import logarlec.view.observerviews.View;
import logarlec.view.panels.RoomPanel;


/**
 * Represents a room in the game.<br>
 * A room has a capacity, a list of actors, a list of doors, a list of room
 * effects and an inventory.
 * 
 * @see IHasLocation
 */
public class Room extends GameObject implements IHasLocation {
    private int id;
    private static int nextId = 1111;
    private Integer capacity = 10;

    private List<Actor> actors = new ArrayList<>();
    private ArrayList<Door> doors = new ArrayList<>();
    private List<RoomEffect> roomEffects = new ArrayList<>();
    private Inventory inventory;

    private boolean isSticky = false;

    /**
     * Creates a new room with the given capacity (allowed players in the room at
     * the same time)
     * 
     * @param capacity the capacity of the room
     */
    public Room(Integer capacity) {
        this.capacity = capacity;
        this.id = nextId++;
        System.out.println("Room created with id: " + id);
        inventory = new Inventory(this);
    }

    public int getId() {
        return id;
    }

    /**
     * Splits a room into two rooms with a door between them
     * 
     * @return the new room created
     */
    public Room split() {
        Room room2 = new Room(capacity);
        new Door(this, room2, false);

        for (RoomEffect effect : roomEffects) {
            RoomEffect effectCopy = effect.copy();
            room2.addEffect(effectCopy);
            effectCopy.setRoom(room2);
        }
        return room2;
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
    public void merge(Room room) {
        // move effects
        for (RoomEffect effect : roomEffects) {
            room.addEffect(effect);
            effect.setRoom(room);
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
        for (int i = 0; i < doors.size(); i++) {
            Door door = doors.get(i);

            if (door.leadsTo(room) == this) {
                doors.remove(door);
                room.doors.remove(door);
                i--;
            } else {
                room.doors.add(door);
                door.updateRoom(this, room);
            }
        }
    }

    /**
     * Signals all the actors in the room if someone is attacking
     * 
     * @param attacker the actor that is attacking
     */
    public void attack(Actor attacker) {
        for (Actor actor : actors) {
            if (actor != attacker && actor.isAlive()) {
                actor.attacked();
            }
        }
    }

    /**
     * Adds a room effect to the room
     * 
     * @param effect the effect to add
     */
    public void addEffect(RoomEffect effect) {
        roomEffects.add(effect);
        for (Actor actor : actors) {
            effect.addEffect(actor);
        }
    }

    /**
     * Removes a room effect from the room
     * 
     * @param effect the effect to remove
     */
    public void removeEffect(RoomEffect effect) {
        roomEffects.remove(effect);
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

        boolean wasFull = this.isFull();

        if (!wasFull || isForced) {
            actors.add(actor);

            if (!wasFull) {
                // newcomer accept effect

                // cause janitor removes effects this can't be done in the loop
                for (int i = 0; i < roomEffects.size(); i++) {
                    RoomEffect effect = roomEffects.get(i);
                    effect.addEffect(actor); 

                    if(roomEffects.size() > 0 && effect != roomEffects.get(i)){
                        i--; // if the effect was removed, decrease the index
                    }
                }

                // everyone attacks everyone
                for (Actor others : actors) {
                    others.attack();
                }
            }
        }
        return !wasFull;
    }

    /**
     * Handles a player leaving a room
     * 
     * @param actor the actor leaving the room
     */
    public void leave(Actor actor) {
        actors.remove(actor);
    }

    /**
     * Tries to revive a student in the room.<br>
     * Only revives the first dead actor found<br>
     * Only revives if the room is not full
     * 
     * @return true if a student was revived, false otherwise
     */
    public boolean revive() {
        if (isFull()) { // not reviving if the room is full
            return false;
        }

        for (Actor actor : actors) {
            if (actor.revive()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the room is full
     * 
     * @return true if the room is full, false otherwise
     */
    private boolean isFull() {
        return actors.stream().filter(actor -> actor.isAlive()).count() >= capacity;
    }

    /**
     * Drops an item in the room
     * 
     * @param item the item to drop
     */
    public void addItem(Item item) {
        item.onDrop(this);
        inventory.addItem(item);
        
    }

    /**
     * Removes an item from the room<br>
     * Mainly used for picking up items
     * 
     * @param item the item to remove
     * @return the removed item
     */
    public Item removeItem(Item item) {
        if(!isSticky){
            return inventory.removeItem(item);
        }
        return null;
    }

    /**
     * Destroys all the cleanable effects
     * 
     * @return whether at least one effect was cleaned
     */
    public boolean clean(){
        boolean cleanedAtLeastOne = false;
        int i = 0;
        while (i < roomEffects.size()){
            if(roomEffects.get(i).clean()){
                cleanedAtLeastOne = true;
            }
            else{
                i++;
            }
        }
        return cleanedAtLeastOne;
    }

    /**
     * Ticks the room<br>
     * Ticks the doors and the room effects
     * Ticks the doors and the room effects
     */
    public void tick() {
        // tick the doors
        for (Door door : doors) {
            door.tick();
        }

        // tick the effects
        for (int i = 0; i < roomEffects.size(); i++) {
            RoomEffect effect = roomEffects.get(i);
            if (!effect.tick()) {
                roomEffects.remove(i);
                i--;
            } else {
                for (Actor actor : actors) {
                    effect.addEffect(actor);
                }
            }
        }
    }

    /**
     * Removes all the actors from the room,
     * except the initiating actor
     * @param initiatingActor the actor that initiated it
     */
    public void close(Actor initiatingActor){
        for (Door door : doors) {
            for (int i = 0; i < actors.size(); i++) {
                Actor actor = actors.get(i);
                if (actor != initiatingActor &&  actor.move(door)){
                    //Decrease the index, because a successful move will remove the actor from the room
                    i--;   
                }
            }
        }
    }

    /**
     * Gets the location of the room<br>
     * Used by transistor
     * 
     * @return the location of the room
     */
    public Room getLocation() {
        return this;
    }

    /**
     * Gets the inventory of the room
     * 
     * @return the inventory of the room
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Sets the capacity of the room
     * @param capacity the new capacity of the room
     */
    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    /**
     * Gets the doors of the room
     * @return the doors of the room
     */
    public ArrayList<Door> getDoors() {
        return doors;
    }

    /**
     * Gets the room effects of the room
     * @return the room effects of the room
     */
    public List<RoomEffect> getRoomEffects() {
        return roomEffects;
    }

    /**
     * Gets the actors in the room
     * @return the actors in the room
     */
    public List<Actor> getActors() {
        return actors;
    }

    /**
     * Returns the capacity of the room
     * @return the capacity of the room
     */ 
    public Integer getCapacity() {
        return capacity;
    }

    /**
     * Returns whether the room is sticky
     * @return whether the room is sticky
     */
    public boolean getIsSticky() {
        return isSticky;
    }

    /**
     * Sets the room to be sticky
     * @param isSticky whether the room is sticky
     */
    public void setIsSticky(boolean isSticky) {
        this.isSticky = isSticky;
    }

    @Override
    public RoomPanel createOwnView() {
        return new ItemHolderViewFactory().createPanel(this);
    }
}
