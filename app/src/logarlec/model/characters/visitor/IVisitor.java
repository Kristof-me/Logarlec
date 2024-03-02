package logarlec.model.characters.visitor;

import logarlec.model.characters.*;

public interface IVisitor {
    public void visit(Student student);

    public void visit(Professor professor);
}