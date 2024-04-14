package logarlec.model.actor.actions;

import logarlec.model.actor.Janitor;
import logarlec.model.room.Door;
import logarlec.model.room.Room;
import logarlec.model.room.StickyEffect;

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
                Room currentRoom = actor.getLocation();
                if(!roomHasDoor(currentRoom, door)){
                    throw new RuntimeException("Can not move through a door that your rooms does not have!");
                }

                Room newRoom = door.leadsTo(currentRoom);
                if (door.move(actor, newRoom)) {
                    if (currentRoom != null) {
                        currentRoom.leave(actor);
                    }
                    newRoom.close(actor);
                    newRoom.addEffect(new StickyEffect(newRoom));
                    return true;
                }
        return false;
    }
}
