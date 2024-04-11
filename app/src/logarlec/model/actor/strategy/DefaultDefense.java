package logarlec.model.actor.strategy;

import logarlec.model.actor.Actor;
import logarlec.model.items.BestTvszFinder;
import logarlec.model.items.Inventory;
import logarlec.model.items.impl.Tvsz;


/**
 * Default defense strategy. It tries to use the best TVSZ in the inventory, otherwise dies.
 */
public class DefaultDefense extends DefenseStrategy {
    /**
     * Constructor.
     * @param actor Actor to defend.
     */
    public DefaultDefense(Actor actor) {
        super(actor);
    }

    /**
     * Defends the actor by using the best TVSZ in the inventory, otherwise dies.
     * @param inventory Inventory to search for TVSZ.
     * 
     * @return True if the actor was defended, false otherwise.
     */
    @Override
    public boolean defend(Inventory inventory) {
        BestTvszFinder finder = new BestTvszFinder();

        Tvsz tvsz = finder.findIn(inventory);
        if (tvsz != null) {
            tvsz.use(actor);
            return true;
        }
        return false;
    }
}
