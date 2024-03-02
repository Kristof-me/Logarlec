package logarlec.model.characters.visitor;

public interface IElement {
    public void accepts(IVisitor v);
}