package logarlec.model.characters.visitor;

import java.util.List;
import logarlec.model.characters.Professor;
import logarlec.model.characters.Student;

public class InteractionVisitor implements IActorVisitor {
    private List<Student> students;
    private List<Professor> professors;

    @Override
    public void visit(Student student) {
        students.add(student);
    }

    @Override
    public void visit(Professor professor) {
        professors.add(professor);
    }

    public void interact() {
        int capableProfessors = 0;

        for (Professor professor : professors) {
            if (professor.attack()) {
                capableProfessors++;
            }
        }

        if (capableProfessors == 0) {
            return;
        }

        for (Student student : students) {
            student.attacked();
        }
    }

}
