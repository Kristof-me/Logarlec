package logarlec.model.actor.actions;

import logarlec.model.items.Item;
import logarlec.model.room.Door;

/**
 * <p>
 * Interface for the actions an actor can perform. <br>
 * Every action all actors can perform is defined in this class.
 * 
 * Implemented by:
 * @see ActionsState
 * @see Actor
 */
public interface IActions {
    /**
     * Attack every other actor in the room.
     */
    public void attack();

    /**
     * Move the actor through the specified door.<br>
     * This action is not forced (so the actor won't be killed if failed).
     * 
     * @param door Door to move through.
     * @return True if the actor was moved, false otherwise.
     */
    public boolean move(Door door);

    /**
     * Use the specified item
     * 
     * @param item Item to use
     */
    public void use(Item item);

    /**
     * The actor tries to pick up the specified item.
     * 
     * @param item Item to pick up
     * @return If the item was picked up or not
     */
    public boolean pickUp(Item item);

    /**
     * The actor tries to drop the specified item.
     * 
     * @param item Item to drop
     */
    public void drop(Item item);
}
