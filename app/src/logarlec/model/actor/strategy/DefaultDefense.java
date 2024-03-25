package logarlec.model.actor.strategy;

import logarlec.model.actor.Actor;
import logarlec.model.items.BestTvszFinder;
import logarlec.model.items.Inventory;
import logarlec.model.items.impl.Tvsz;
import logarlec.model.logger.Logger;

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
        Logger.preConstructor(this, actor);
        Logger.postConstructor(this);
    }

    /**
     * Defends the actor by using the best TVSZ in the inventory, otherwise dies.
     * @param inventory Inventory to search for TVSZ.
     * 
     * @return True if the actor was defended, false otherwise.
     */
    @Override
    public boolean defend(Inventory inventory) {
        Logger.preExecute(this, "defend", inventory);
        BestTvszFinder finder = new BestTvszFinder();

        Tvsz tvsz = finder.findIn(inventory);
        if (tvsz != null) {
            tvsz.use(actor);
            Logger.postExecute(true);
            return true;
        }
        Logger.postExecute(false);
        return false;
    }
}
