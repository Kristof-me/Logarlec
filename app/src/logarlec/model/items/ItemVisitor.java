public abstract class ItemVisitor {
    public abstract void visit(Sponge sponge);
    public abstract void visit(Tvsz tvsz);
    public abstract void visit(SlideRule slideRule);
    public abstract void visit(Beer beer);
    public abstract void visit(Cocktail cocktail);
    public abstract void visit(GasMask gasMask);
    public abstract void visit(Transistor transistor);
    public abstract void visit(Camembert camembert);
}