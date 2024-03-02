package logarlec.model.characters;

import logarlec.model.characters.visitor.IVisitor;
import logarlec.model.labyrinth.Room;

public class Professor extends Actor {

    public Professor(Room spawnRoom) {
        super(spawnRoom, 2);
    }

    public void Attack() {

    }

    public void accepts(IVisitor v) {
        v.visit(this);
    }
}
