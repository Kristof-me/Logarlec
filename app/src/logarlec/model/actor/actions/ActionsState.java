package logarlec.model.actor.actions;

public abstract class ActionsState implements IActions {
    public abstract boolean tick();

    public abstract ActionsState setNextState(ActionsState state);
}
