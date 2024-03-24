package logarlec.model.actor.actions;

import logarlec.model.actor.Actor;
import logarlec.model.items.Item;
import logarlec.model.room.Door;
import logarlec.model.logger.*;

public class StunnedStep extends ActionsState {
    public StunnedStep(Actor owner) {
        super(owner);
    }

    @Override
    public void attack() {
        Logger.preExecute(this, "attack");
        Logger.postExecute();
    }

    @Override
    public boolean move(Door door) {
        Logger.preExecute(this, "move", door);
        Logger.postExecute(false);
        return false;
    }

    @Override
    public void use(Item item) {
        Logger.preExecute(this, "use", item);
        Logger.postExecute();
    }

    @Override
    public boolean pickUp(Item item) {
        Logger.preExecute(this, "pickUp", item);
        Logger.postExecute(false);
        return false;
    }

    @Override
    public void drop(Item item) {
        Logger.preExecute(this, "drop", item);
        Logger.postExecute();
    }

    @Override
    public ActionsState setNextState(ActionsState state) {
        Logger.preExecute(this, "setNextState", state);
        Logger.postExecute(this);
        return this;
    }
}
