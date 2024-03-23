package logarlec.model.items;

import logarlec.model.items.impl.Beer;
import logarlec.model.items.impl.Camembert;
import logarlec.model.items.impl.Cocktail;
import logarlec.model.items.impl.Tvsz;
import logarlec.model.items.impl.SlideRule;
import logarlec.model.items.impl.Sponge;
import logarlec.model.items.impl.Transistor;
import logarlec.model.items.impl.Tvsz;

public class BestTvszFinder extends ItemFinder<Tvsz> {

    @Override
    public Tvsz findIn(Inventory inventory) {
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
        return best;
    }

    @Override
    public void visit(Tvsz tvsz) {
        potentialItems.add(tvsz);
    }
}
