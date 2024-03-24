package logarlec.model.items;

import logarlec.model.items.impl.Transistor;

import logarlec.model.logger.*;

public class TransistorPairFinder extends ItemFinder<Transistor> {
    private Transistor caller;

    public TransistorPairFinder(Transistor caller) {
        Logger.preConstructor(this, caller);
        this.caller = caller;
        Logger.postConstructor(this);
    }

    @Uses(fields = {"caller", "potentialItems"})
    @Override
    public void visit(Transistor transistor) {
        Logger.preExecute(this, "visit", transistor);
        if (transistor == caller) {
            return;
        }
        potentialItems.add(transistor);
        Logger.postExecute();
    }

    @Uses(fields = {"potentialItems"})
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
