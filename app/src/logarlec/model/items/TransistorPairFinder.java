package logarlec.model.items;

import logarlec.model.items.impl.Transistor;



/**
 * A class that finds a transistor in the inventory that is not the same as the
 * caller.
 * The pair is found by visiting all transistors in the inventory.
 * The first transistor that is not the same as the caller is returned.
 * If no such transistor is found, null is returned.
 */
public class TransistorPairFinder extends ItemFinder<Transistor> {
    private Transistor caller;

    public TransistorPairFinder(Transistor caller) {
        this.caller = caller;
    }

    @Override
    public void visit(Transistor transistor) {
        if (transistor == caller || transistor.getPair() != null) {
            System.out.println("already has pair");
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
        }

        return null;
    }
}
