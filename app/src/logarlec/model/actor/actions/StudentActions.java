package logarlec.model.actor.actions;

import logarlec.model.actor.Student;
import logarlec.model.logger.Logger;

public class StudentActions extends ActionsState {
    public StudentActions(Student student) {
        super(student);
    }

    @Override
    public void attack() {
        Logger.preExecute(this, "attack");
        // Implementation goes here
        Logger.postExecute();
    }

    @Override
    public ActionsState setNextState(ActionsState state) {
        Logger.preExecute(this, "setNextState", state);
        Logger.postExecute(state);
        return state;
    }
}
