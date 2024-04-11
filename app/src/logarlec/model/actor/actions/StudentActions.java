package logarlec.model.actor.actions;

import logarlec.model.actor.Student;


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
    }

    /**
     * The student can NOT attack, so this is an empty implementation.
     */
    @Override
    public void attack() {
        // no implementation
    }

    /**
     * Sets the next state for the student.
     * 
     * @param state Next state
     */
    @Override
    public ActionsState setNextState(ActionsState state) {
        return state;
    }
}
