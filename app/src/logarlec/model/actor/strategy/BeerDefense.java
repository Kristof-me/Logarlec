package logarlec.model.actor.strategy;

import logarlec.model.actor.Actor;
import logarlec.model.items.Inventory;
import logarlec.model.logger.Logger;

public class BeerDefense extends DefenseStrategy {
    private int remaining;
    public BeerDefense(int remaining) {
        Logger.preConstructor(this, remaining);
        this.remaining = remaining;
        Logger.postConstructor(this);
    }

    @Override
    public boolean defend(Inventory inventory) {
        Logger.preExecute(this, "defend", inventory);
        Logger.postExecute(true);
        return true;
    }

    @Override
    public boolean tick() {
        Logger.preExecute(this, "tick");
        if (remaining > 0) {
            remaining--;
            Logger.postExecute(true);
            return true;
        }
        Logger.postExecute(false);
        return false;
    }
}
