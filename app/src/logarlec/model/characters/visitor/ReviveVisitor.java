package logarlec.model.characters.visitor;

import logarlec.model.characters.Professor;
import logarlec.model.characters.Student;

public class ReviveVisitor implements IActorVisitor {
    private boolean revived = false;

    @Override
    public void visit(Student student) {
        if (revived) {
            return;
        }

        student.revive();
        revived = true;
    }

    @Override
    public void visit(Professor professor) {}
}
