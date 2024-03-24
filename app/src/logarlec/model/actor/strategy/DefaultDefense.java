package logarlec.model.actor.strategy;

import logarlec.model.actor.Actor;
import logarlec.model.items.BestTvszFinder;
import logarlec.model.items.Inventory;
import logarlec.model.items.impl.Tvsz;
import logarlec.model.logger.Logger;

public class DefaultDefense extends DefenseStrategy {
    public DefaultDefense(Actor owner) {
        this.owner = owner;
    }
    @Override
    public boolean defend(Inventory inventory) {
        Logger.preExecute(this, inventory);
        BestTvszFinder finder = new BestTvszFinder();
        Tvsz tvsz = finder.findIn(inventory);
        if (tvsz != null) {
            tvsz.use(owner);
            Logger.postExecute(true);
            return true;
        }
        Logger.postExecute(false);
        return false;
    }

    @Override
    public boolean tick() {
        Logger.preExecute(this);
        Logger.postExecute(false);
        return false;
    }
}
