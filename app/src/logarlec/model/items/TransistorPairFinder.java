package logarlec.model.items;

import logarlec.model.items.impl.Beer;
import logarlec.model.items.impl.Camembert;
import logarlec.model.items.impl.Cocktail;
import logarlec.model.items.impl.GasMask;
import logarlec.model.items.impl.SlideRule;
import logarlec.model.items.impl.Sponge;
import logarlec.model.items.impl.Transistor;
import logarlec.model.items.impl.Tvsz;

public class TransistorPairFinder extends ItemFinder<Transistor> {
    @Override
    public void visit(Transistor transistor) {
        // Implementation goes here
    }

    @Override
    public Transistor findIn(Inventory inventory) {
        // Implementation goes here
        return null;
    }

    @Override
    public void visit(Sponge sponge) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public void visit(Tvsz tvsz) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public void visit(SlideRule slideRule) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public void visit(Beer beer) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public void visit(Cocktail cocktail) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public void visit(GasMask gasMask) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public void visit(Camembert camembert) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }
}
