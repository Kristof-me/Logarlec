public abstract class ActionState implements IActions {
    public abstract boolean tick();
    public abstract ActionState setNextState(ActionState state);
}