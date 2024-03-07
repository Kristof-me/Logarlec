package logarlec.model.characters.visitor;

public interface IVisitable {
    public void accepts(IActorVisitor v);
}
