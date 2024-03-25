package logarlec.model.items;

import logarlec.model.items.impl.Tvsz;

import logarlec.model.logger.*;

/**
 * Find the TVSZ with the most uses left.
 */
public class BestTvszFinder extends ItemFinder<Tvsz> {
    public BestTvszFinder() {
        Logger.preConstructor(this);
        Logger.postConstructor(this);
    }

    /**
     * Find the TVSZ with the most uses left in the inventory.
     * 
     * @param inventory The inventory to search in.
     * @return The TVSZ with the most uses left.
     */
    @Override
    public Tvsz findIn(Inventory inventory) {
        Logger.preExecute(this, "findIn", inventory);
        potentialItems.clear();
        inventory.acceptVisitor(this);

        int lowest = Integer.MAX_VALUE;
        Tvsz best = null;
        for (Tvsz tvsz : potentialItems) {
            int usesLeft = tvsz.getUsesLeft();
            if (usesLeft <= lowest) {
                best = tvsz;
                lowest = usesLeft;
            }
        }
        return Logger.postExecute(best);
    }

    /**
     * Visit a TVSZ and add it to a list of potential items.
     * 
     * @param tvsz The TVSZ to visit.
     */
    @Override
    public void visit(Tvsz tvsz) {
        Logger.preExecute(this, "visit", tvsz);
        potentialItems.add(tvsz);
        Logger.postExecute();
    }
}
