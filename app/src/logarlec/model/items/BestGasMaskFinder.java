package logarlec.model.items;

import logarlec.model.items.impl.GasMask;

import logarlec.model.logger.*;

/**
 * A class that finds the best gas mask with the most uses left in an inventory.
 */
public class BestGasMaskFinder extends ItemFinder<GasMask> {

    /**
     * Finds the gas mask with the most uses left in the inventory.
     * @param inventory The inventory to search in.
     * @return The gas mask with the most uses left.
     */
    @Override
    public GasMask findIn(Inventory inventory) {
        Logger.preExecute(this, "findIn", inventory);
        potentialItems.clear();
        inventory.acceptVisitor(this);

        int lowest = Integer.MAX_VALUE;
        GasMask best = null;
        for (GasMask gasMask : potentialItems) {
            if (gasMask.getUsesLeft() <= lowest) {
                best = gasMask;
                lowest = gasMask.getUsesLeft();
            }
        }
        return Logger.postExecute(best);
    }

    /**
     * Visits a gas mask and adds it to the list of potential items.
     * @param gasMask The gas mask to visit.
     */
    @Override
    public void visit(GasMask gasMask) {
        Logger.preExecute(this, "visit", gasMask);
        potentialItems.add(gasMask);
        Logger.postExecute();
    }
}
