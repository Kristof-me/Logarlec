package logarlec.model.actor.strategy;

import logarlec.model.items.Inventory;
import logarlec.model.logger.Logger;
import logarlec.model.logger.State;
import logarlec.model.logger.Uses;
import logarlec.model.actor.Actor;

/**
 * A defense strategy that gets applied after the player uses a beer item.
 */
public class BeerDefense extends DefenseStrategy {
    @State(name = "remaining", min = 0, max = Integer.MAX_VALUE)
    private Integer remaining = null;
    
    /**
     * Creates a new beer defense strategy.
     * @param actor the actor that will use this strategy
     */
    public BeerDefense(Actor actor) {
        super(actor);
        Logger.preConstructor(this, actor);
        Logger.postConstructor(this);
    }

    /**
     * Blocks the attack.
     * @param inventory the inventory of the actor
     * @return true if the attack was blocked, false otherwise
     */
    @Override
    public boolean defend(Inventory inventory) {
        Logger.preExecute(this, "defend", inventory);
        return Logger.postExecute(true);
    }

    /**
     * @return true if the defense is still active, false otherwise
     */
    @Uses(fields = {"remaining"})
    @Override
    public boolean tick() {
        Logger.preExecute(this, "tick");
        if (remaining > 0) {
            remaining--;
            return Logger.postExecute(true);
        }
        return Logger.postExecute(false);
    }
}
