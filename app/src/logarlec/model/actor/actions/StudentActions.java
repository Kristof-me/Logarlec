package logarlec.model.actor.actions;

import logarlec.model.actor.Student;
import logarlec.model.logger.Logger;

/**
 * The StudentActions class represents the actions that a student can perform.
 */
public class StudentActions extends ActionsState {

    /**
     * Constructs a new StudentActions object.
     *
     * @param student the student that will perform the actions
     */
    public StudentActions(Student student) {
        super(student);
        Logger.preConstructor(this, student);
        Logger.postConstructor(this);
    }

    /**
     * The student can NOT attack, so this is an empty implementation.
     */
    @Override
    public void attack() {
        Logger.preExecute(this, "attack");
        // no implementation
        Logger.postExecute();
    }

    /**
     * Sets the next state for the student.
     * 
     * @param state Next state
     */
    @Override
    public ActionsState setNextState(ActionsState state) {
        Logger.preExecute(this, "setNextState", state);
        Logger.postExecute(state);
        return state;
    }
}
