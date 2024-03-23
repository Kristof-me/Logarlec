package logarlec.model.items;

import logarlec.model.items.impl.Transistor;

public class TransistorPairFinder extends ItemFinder<Transistor> {
    @Override
    public void visit(Transistor transistor) {
        potentialItems.add(transistor);
    }

    @Override
    public Transistor findIn(Inventory inventory) {
        potentialItems.clear();
        inventory.acceptVisitor(this);
        if (potentialItems.size() > 0) {
            return potentialItems.get(0);
        } else {
            return null;
        }
    }
}
