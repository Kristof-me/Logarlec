package logarlec.model.characters.visitor;

import logarlec.model.characters.*;

public class AttackVisitor implements IVisitor {
    public void visit(Student student) {
        System.out.println("Chuckles, I'm in danger");
    }

    public void visit(Professor professor) {
        System.out.println("*Professor stuff*");
    }
}
