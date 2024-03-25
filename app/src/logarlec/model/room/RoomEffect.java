package logarlec.model.room;

import logarlec.model.actor.Actor;
import logarlec.model.actor.Professor;
import logarlec.model.actor.Student;
import logarlec.model.logger.Logger;
import logarlec.model.logger.State;
import logarlec.model.logger.Uses;

/**
 * Represents an effect that can be applied to a room. <br>
 * These effects will modify the behavior of actors in the room <br>
 * if applied.<br>
 * After a while these effects expire
 */
public abstract class RoomEffect {
    @State(name = "timeLeft", min = 0, max = Integer.MAX_VALUE)
    protected Integer timeLeft = null;

    /**
     * Decreases the time left for the effect. <br>
     * If the time left is zero or less, the effect has expired and <br>
     * the method returns false. Otherwise, the method returns true.
     *
     * @return true if the effect is still active, false if the effect has expired
     */
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
