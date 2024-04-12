package logarlec.model.actor.actions;

import logarlec.model.actor.Actor;
import logarlec.model.items.Item;
import logarlec.model.room.Door;


/**
 * The stunned step state of an actor. The actor is unable to perform any
 * actions, and
 * after a while the state will expire.
 */
public class StunnedStep extends ActionsState {
    private Integer remaining;

    /**
     * The actor is unable to attack while stunned.
     */  
    public StunnedStep(Actor owner) {
        super(owner);
    }

    @Override
    public void attack() {

        /**
         * The actor is unable to move while stunned.
         */
    }

    @Override
    public boolean move(Door door) {

        /**
         * The actor is unable to use items while stunned.
         */
        return false;
    }

    @Override
    public void use(Item item) {

        /**
         * The actor is unable to pick up items while stunned.
         */
    }

    @Override
    public boolean pickUp(Item item) {

        /**
         * The actor is unable to drop items while stunned.
         */
        return false;
    }

    @Override
    public void drop(Item item) {

        /**
         * The stunned effect persists, it cannot be changed, so it just returns itself.
         */
    }

    @Override
    public ActionsState setNextState(ActionsState state) {
        return this;
    }

    public void setRemaining(int remaining) {
        this.remaining = remaining;
    }

    @Override
    public boolean tick() {
        remaining--;
        if (remaining == 0) {
            return false;
        }
        return true;
    }
}
