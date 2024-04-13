package logarlec.model.actor.actions;

import logarlec.model.items.Inventory;
import logarlec.model.items.Item;
import logarlec.model.room.Door;
import logarlec.model.actor.Actor;
import logarlec.model.room.Room;

/**
 * <p>
 * Abstract class for the state of the actor's actions. <br>
 * Every action the actor can perform is defined in this class.
 * <p>
 * This class is part of the State pattern.
 * 
 * @see IActions
 * @see Actor
 */
public abstract class ActionsState implements IActions {
    protected Actor actor;

    /**
     * Constructor for the ActionsState class.
     * 
     * @param actor Owner of the state.
     */
    public ActionsState(Actor actor) {
        this.actor = actor;
    }

    /**
     * Tick the state of the actor.
     * 
     * @return True if the state is still active, false if it has expired.
     */
    public boolean tick() {
        return true;
    }

    /**
     * Attack every other actor in the room.<br>
     * This is an emtpy implementation, as students or janitors shouldn't be able to attack.
     */
    @Override
    public void attack() { }

    /**
     * Move the actor through the specified door.<br>
     * This action is not forced (so the actor won't be killed if failed).
     * 
     * @param door Door to move through.
     * @return True if the actor was moved, false otherwise.
     */
    @Override
    public boolean move(Door door) {
        Room oldRoom = actor.getLocation();
        Room newRoom = door.leadsTo(oldRoom);
        //Set room to new, even though not entered yet, otherwise actor would attack old room
        actor.setLocation(newRoom);
        if (door.move(actor, newRoom) && newRoom.enter(actor, false)) {
            if (oldRoom != null) {
                oldRoom.leave(actor);
            }
            return true;
        }
        else {
            actor.setLocation(oldRoom);
        }
        return false;
    }

    /**
     * Use the specified item
     * 
     * @param item Item to use
     */
    @Override
    public void use(Item item) {
        item.use(actor);
    }

    /**
     * The actor tries to pick up the specified item.
     * 
     * @param item Item to pick up
     * @return If the item was picked up or not
     */
    @Override
    public boolean pickUp(Item item) {
        Room room = actor.getLocation();
        Item targetItem = room.removeItem(item);

        if (targetItem == null) {
            return false;
        }

        Inventory inventory = actor.getInventory();
        boolean success = inventory.addItem(targetItem);
        if (success) {
            // if the item was added to the inventory
            item.onPickup(actor);
        } else {
            // put it back in the room
            room.addItem(targetItem);
        }
        return success;
    }

    /**
     * The actor tries to drop the specified item.
     * 
     * @param item Item to drop
     */
    @Override
    public void drop(Item item) {
        Inventory inventory = actor.getInventory();
        inventory.removeItem(item);
        Room room = actor.getLocation();
        room.addItem(item);
    }

    /**
     * Returns itself if the state is stunned, otherwise returns the next state.
     * 
     * @param state Next state
     */
    public abstract ActionsState setNextState(ActionsState state);
}
