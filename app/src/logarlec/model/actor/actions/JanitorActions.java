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
     * Sets the next state of the Janitor.
     */
    public ActionsState setNextState(ActionsState state) {
        return state;
    }

    /**
     * Move the actor through the specified door.<br>
     * This action is not forced (so the actor won't be killed if failed).
     * If successful, the janitor moves all other actors out of the target room.
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
            newRoom.close(actor);
            return true;
        }
        else {
            actor.setLocation(oldRoom);
        }
        return false;
    }
}
