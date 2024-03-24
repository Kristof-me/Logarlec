package logarlec.model.items;

import logarlec.model.items.impl.Tvsz;

import logarlec.model.logger.*;

public class BestTvszFinder extends ItemFinder<Tvsz> {

    @Uses(fields = {"potentialItems"})
    @Override
    public Tvsz findIn(Inventory inventory) {
        Logger.preExecute(this, "findIn", inventory);
        potentialItems.clear();
        inventory.acceptVisitor(this);

        int lowest = Integer.MAX_VALUE;
        Tvsz best = null;
        for (Tvsz tvsz : potentialItems) {
            if (tvsz.getUsesLeft() < lowest) {
                best = tvsz;
                lowest = tvsz.getUsesLeft();
            }
        }
        return Logger.postExecute(best);
    }

    @Uses(fields = {"potentialItems"})
    @Override
    public void visit(Tvsz tvsz) {
        Logger.preExecute(this, "visit", tvsz);
        potentialItems.add(tvsz);
        Logger.postExecute();
    }
}
