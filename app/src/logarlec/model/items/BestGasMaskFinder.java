package logarlec.model.items;

import logarlec.model.items.impl.GasMask;

import logarlec.model.logger.*;

public class BestGasMaskFinder extends ItemFinder<GasMask> {

    @Override
    public GasMask findIn(Inventory inventory) {
        Logger.preExecute(this, "findIn", inventory);
        potentialItems.clear();
        inventory.acceptVisitor(this);

        int lowest = Integer.MAX_VALUE;
        GasMask best = null;
        for (GasMask gasMask : potentialItems) {
            if (gasMask.getUsesLeft() < lowest) {
                best = gasMask;
                lowest = gasMask.getUsesLeft();
            }
        }
        return Logger.postExecute(best);
    }

    @Override
    public void visit(GasMask gasMask) {
        Logger.preExecute(this, "visit", gasMask);
        potentialItems.add(gasMask);
        Logger.postExecute();
    }
}
