package logarlec.model.actor.strategy;

import logarlec.model.items.Inventory;
import logarlec.model.logger.Logger;

public class BeerDefense extends DefenseStrategy {
    private int remaining;
    public BeerDefense(int remaining) {
        this.remaining = remaining;
    }

    @Override
    public boolean defend(Inventory inventory) {
        Logger.preExecute(this, inventory);
        Logger.postExecute(true);
        return true;
    }

    @Override
    public boolean tick() {
        Logger.preExecute(this);
        if (remaining > 0) {
            remaining--;
            Logger.postExecute(true);
            return true;
        }
        Logger.postExecute(false);
        return false;
    }
}
