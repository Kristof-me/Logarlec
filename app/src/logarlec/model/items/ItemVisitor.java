package logarlec.model.items;

import logarlec.model.items.impl.*;

public abstract class ItemVisitor {
    public void visit(Sponge sponge) {}

    public void visit(Tvsz tvsz) {}

    public void visit(SlideRule slideRule) {}

    public void visit(Beer beer) {}

    public void visit(Cocktail cocktail) {}

    public void visit(GasMask gasMask) {}

    public void visit(Transistor transistor) {}

    public void visit(Camembert camembert) {}
}
