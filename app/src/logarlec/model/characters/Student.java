package logarlec.model.characters;

import logarlec.model.labyrinth.Room;
import logarlec.model.characters.visitor.*;

public class Student extends Actor {

    public Student(Room spawnRoom) {
        super(spawnRoom, 5);
    }

    public void Attacked() {}

    public void accepts(IVisitor v) {
        v.visit(this);
    }
}
