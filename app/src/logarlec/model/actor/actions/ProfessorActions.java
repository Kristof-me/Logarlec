package logarlec.model.actor.actions;

import logarlec.model.actor.Professor;

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
    }

    /**
     * Makes the professor attack every actor in the same room.
     */
    @Override
    public void attack() {
        actor.getLocation().attack(actor);
    }

    /**
     * Sets the next state of the professor.
     */
    public ActionsState setNextState(ActionsState state) {
        return state;
    }
}
