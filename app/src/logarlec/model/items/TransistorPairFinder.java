package logarlec.model.items;

import logarlec.model.items.impl.Transistor;

import logarlec.model.logger.*;

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
        Logger.preConstructor(this, caller);
        this.caller = caller;
        Logger.postConstructor(this);
    }

    @Override
    public void visit(Transistor transistor) {
        Logger.preExecute(this, "visit", transistor);
        if (transistor == caller) {
            return;
        }
        potentialItems.add(transistor);
        Logger.postExecute();
    }

    @Override
    public Transistor findIn(Inventory inventory) {
        Logger.preExecute(this, "findIn", inventory);
        potentialItems.clear();
        inventory.acceptVisitor(this);
        if (!potentialItems.isEmpty()) {
            return Logger.postExecute(potentialItems.get(0));
        } else {
            Logger.postExecute();
            return Logger.postExecute(null);
        }
    }
}
