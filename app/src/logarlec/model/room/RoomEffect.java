package logarlec.model.room;

import logarlec.model.actor.Actor;
import logarlec.model.actor.Professor;
import logarlec.model.actor.Student;
import logarlec.model.logger.State;
import logarlec.model.logger.Uses;

public abstract class RoomEffect {
    @State(name = "timeLeft", min = 0, max = Integer.MAX_VALUE)
    protected Integer timeLeft;

    @Uses(fields = {"timeLeft"})
    public boolean tick() {
        // Decrease the time left for the effect
        timeLeft--;

        // If time left is zero or less, return false indicating the effect has expired
        if (timeLeft <= 0) {
            return false;
        }

        // Otherwise, return true indicating the effect is still active
        return true;
    }

    public abstract void addEffect(Actor actor);

    public abstract void applyEffect(Professor professor);

    public abstract void applyEffect(Student student);
}
