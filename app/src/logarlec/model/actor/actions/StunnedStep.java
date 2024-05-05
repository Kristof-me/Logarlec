package logarlec.model.actor.actions;

import logarlec.model.room.Door;
import logarlec.view.panels.StunnedStrategyPanel;
import logarlec.model.actor.Actor;
import logarlec.model.items.Item;


/**
 * The stunned step state of an actor. The actor is unable to perform any
 * actions, and
 * after a while the state will expire.
 */
public class StunnedStep extends ActionState {
    private Integer remaining;

    /**
     * The actor is unable to attack while stunned.
     */  
    public StunnedStep(Actor owner) {
        super(owner);
        remaining = 3;
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
    public ActionState setNextState(ActionState state) {
        //Stunned effect takes priority over any other state
        return this;
    }

    /**
     * Sets the remaining time of the stunned effect.
     * @param remaining the remaining time of the stunned effect
     */
    public void setRemaining(int remaining) {
        this.remaining = remaining;
    }

    @Override
    public boolean tick() {
        remaining--;
        update();
        System.out.println("Stunned step remaining: " + remaining);
        return remaining > 0;
    }

    public Integer getRemaining() {
        return remaining;
    }

    @Override
    public StunnedStrategyPanel createOwnView() {
        StunnedStrategyPanel panel = new StunnedStrategyPanel(this, "stunned", "stun.png");
        addListener(panel);
        return panel;
    }
}
