package logarlec.model.actor.strategy;

import logarlec.model.items.Inventory;
import logarlec.view.panels.BeerDefensePanel;
import logarlec.model.actor.Actor;

/**
 * A defense strategy that gets applied after the player uses a beer item.
 */
public class BeerDefense extends DefenseStrategy {
    private Integer remaining;
    
    /**
     * Creates a new beer defense strategy.
     * @param actor the actor that will use this strategy
     */
    public BeerDefense(Actor actor) {
        super(actor);
        remaining = 3;
    }

    /**
     * Blocks the attack.
     * @param inventory the inventory of the actor
     * @return true if the attack was blocked, false otherwise
     */
    @Override
    public boolean defend(Inventory inventory) {
        return true;
    }

    /**
     * @return true if the defense is still active, false otherwise
     */
    @Override
    public boolean tick() {
        remaining--;
        update();
        System.out.println("Beer defense remaining: " + remaining);
        return remaining > 0;
    }

    public int getRemaining() {
        return remaining;
    }

    @Override
    public BeerDefensePanel createOwnView() {
        BeerDefensePanel panel = new BeerDefensePanel(this, "beer text", "beer.png");
        addListener(panel);
        return new BeerDefensePanel(this, "Beer Defense", "beer.png");
    }
}
