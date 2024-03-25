package logarlec.model.actor.actions;

import logarlec.model.actor.Professor;
import logarlec.model.logger.Logger;

/**
 * The actions that a professor can take.
 */
public class ProfessorActions extends ActionsState {

    /**
     * Creates a new set of actions for the given professor.
     * 
     * @param professor The professor to create actions for.
     */
    public ProfessorActions(Professor professor) {
        super(professor);
        Logger.preConstructor(this, professor);
        Logger.postConstructor(this);
    }

    /**
     * Makes the professor attack every actor in the same room.
     */
    @Override
    public void attack() {
        Logger.preExecute(this, "attack");
        actor.getLocation().attack(actor);
        Logger.postExecute();
    }

    /**
     * Sets the next state of the professor.
     */
    public ActionsState setNextState(ActionsState state) {
        Logger.preExecute(this, "setNextState", state);
        Logger.postExecute(state);
        return state;
    }
}
