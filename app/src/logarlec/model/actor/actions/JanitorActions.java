package logarlec.model.actor.actions;

import logarlec.model.actor.Janitor;
import logarlec.model.room.Door;
import logarlec.model.room.Room;

/**
 * The actions that a Janitor can take.
 */
public class JanitorActions extends ActionsState {

    /**
     * Creates a new set of actions for the given Janitor.
     * 
     * @param Janitor The Janitor to create actions for.
     */
    public JanitorActions(Janitor janitor) {
        super(janitor);
    }

     /**
     * The janitor can NOT attack, so this is an empty implementation.
     */
    @Override
    public void attack() {
        // no implementation
    }

    /**
     * Sets the next state of the Janitor.
     */
    public ActionsState setNextState(ActionsState state) {
        return state;
    }

    @Override
    public boolean move(Door door) {
        Room currentRoom = actor.getLocation();

        if (door.move(actor, door.leadsTo(currentRoom))) {
            if (currentRoom != null) {
                currentRoom.leave(actor);
            }
            
            // ToDo: move everyone out of the room to
            // ToDo: clean the room

        }

        return false;
    }
}
