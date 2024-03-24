package logarlec.model.items;

import logarlec.model.items.impl.Transistor;

public class TransistorPairFinder extends ItemFinder<Transistor> {
    private Transistor caller;

    public TransistorPairFinder(Transistor caller) {
        this.caller = caller;
    }

    @Override
    public void visit(Transistor transistor) {
        if (transistor == caller) {
            return;
        }
        potentialItems.add(transistor);
    }

    @Override
    public Transistor findIn(Inventory inventory) {
        potentialItems.clear();
        inventory.acceptVisitor(this);
        if (!potentialItems.isEmpty()) {
            return potentialItems.get(0);
        } else {
            return null;
        }
    }
}
