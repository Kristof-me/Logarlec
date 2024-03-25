package logarlec.model.actor.actions;

import logarlec.model.actor.Actor;
import logarlec.model.items.Item;
import logarlec.model.room.Door;
import logarlec.model.logger.*;

/**
 * The stunned step state of an actor. The actor is unable to perform any actions, and 
 * after a while the state will expire.
 */
public class StunnedStep extends ActionsState {
    @State(name = "Remaining", min = 1, max = 100)
    private Integer remaining;
    public StunnedStep(Actor owner) {

    /**
     * The actor is unable to attack while stunned.
     */        super(owner);
    }

    @Override
    public void attack() {
        Logger.preExecute(this, "attack");

    /**
     * The actor is unable to move while stunned.
     */        Logger.postExecute();
    }

    @Override
    public boolean move(Door door) {
        Logger.preExecute(this, "move", door);
        Logger.postExecute(false);

    /**
     * The actor is unable to use items while stunned.
     */        return false;
    }

    @Override
    public void use(Item item) {
        Logger.preExecute(this, "use", item);

    /**
     * The actor is unable to pick up items while stunned.
     */        Logger.postExecute();
    }

    @Override
    public boolean pickUp(Item item) {
        Logger.preExecute(this, "pickUp", item);
        Logger.postExecute(false);

    /**
     * The actor is unable to drop items while stunned.
     */        return false;
    }

    @Override
    public void drop(Item item) {
        Logger.preExecute(this, "drop", item);

    /**
     * The stunned effect persists, it cannot be changed, so it just returns itself.
     */        Logger.postExecute();
    }

    @Override
    public ActionsState setNextState(ActionsState state) {
        Logger.preExecute(this, "setNextState", state);
        Logger.postExecute(this);
        return this;
    }

    @Override
    public boolean tick() {
        Logger.preExecute(this, "tick");
        remaining--;
        if (remaining == 0) {
            Logger.postExecute(false);
            return false;
        }
        Logger.postExecute(true);
        return true;
    }
}
