package logarlec.model.actor.actions;

import logarlec.model.actor.Professor;
import logarlec.model.logger.Logger;

public class ProfessorActions extends ActionsState {
    public ProfessorActions(Professor professor) {
        super(professor);
    }

    @Override
    public void attack() {
        Logger.preExecute(this, "attack");
        // Implementation goes here
        Logger.postExecute();
    }
    
    public ActionsState setNextState(ActionsState state) {
        Logger.preExecute(this, "setNextState", state);
        Logger.postExecute(state);
        return state;
    }
}
