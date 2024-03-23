package logarlec.model.items;

import logarlec.model.items.impl.GasMask;

public class BestGasMaskFinder extends ItemFinder<GasMask> {

    @Override
    public GasMask findIn(Inventory inventory) {
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
        return best;
    }

    @Override
    public void visit(GasMask gasMask) {
        potentialItems.add(gasMask);
    }
}
