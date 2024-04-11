package logarlec.model.actor.strategy;

import logarlec.model.items.Inventory;



import logarlec.model.actor.Actor;

/**
 * A defense strategy that gets applied after the player uses a beer item.
 */
public class BeerDefense extends DefenseStrategy {
    private Integer remaining = null;
    
    /**
     * Creates a new beer defense strategy.
     * @param actor the actor that will use this strategy
     */
    public BeerDefense(Actor actor) {
        super(actor);
    }

    /**
     * Blocks the attack.
     * @param inventory the inventory of the actor
     * @return true if the attack was blocked, false otherwise
     */
    @Override
    public boolean defend(Inventory inventory) {
        return false;
    }

    /**
     * @return true if the defense is still active, false otherwise
     */
    @Override
    public boolean tick() {
        if (remaining > 0) {
            remaining--;
        }
        return false;
    }
}
