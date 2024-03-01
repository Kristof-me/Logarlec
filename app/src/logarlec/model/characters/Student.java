package logarlec.model.characters;

import logarlec.model.labyrinth.Room;

public class Student extends Actor {
    private int invincibilityRemaining = 0;

    public Student(Room spawnRoom) {
        super(spawnRoom);
    }

    public void Attacked() {}
}
