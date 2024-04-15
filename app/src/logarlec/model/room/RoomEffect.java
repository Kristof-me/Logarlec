package logarlec.model.room;

import logarlec.model.actor.Actor;
import logarlec.model.actor.Professor;
import logarlec.model.actor.Janitor;
import logarlec.model.actor.Student;




/**
 * Represents an effect that can be applied to a room. <br>
 * These effects will modify the behavior of actors in the room <br>
 * if applied.<br>
 * After a while these effects expire
 */
public abstract class RoomEffect {

    protected RoomEffect(Room room){
        this.room = room;
    }
    protected Integer timeLeft = 10;
    protected Room room;

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
        return timeLeft > 0;
    }

    /**
     * Sets the time left for the effect
     * @param timeLeft the time left for the effect
     */
    public void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
    }

    /**
     * Gets the time left for the effect
     * @return the time left for the effect
     */
    public Integer getTimeLeft() {
        return timeLeft;
    }

    /**
     * Adds the effect to the actor using the visitor pattern
     * By default, no item protects the actor from the effect
     * @param actor the actor to add the effect to
     */
    public void addEffect(Actor actor) {
        actor.acceptEffect(this, null);
    }

    /**
     * Tries to clean the effect from the room
     * By default, the effect cannot be cleaned
     * @return true if the effect was cleaned, false otherwise
     */
    public boolean clean() {
        return false;
    }

    /**
     * Apply the effect to a Professor (visitor pattern)
     * @param professor the Professor to apply the effect to
     */
    public abstract void applyEffect(Professor professor);

    /**
     * Apply the effect to a Student (visitor pattern)
     * @param student the Student to apply the effect to
     */
    public abstract void applyEffect(Student student);

    /**
     * Apply the effect to a Janitor (visitor pattern)
     * @param janitor the Janitor to apply the effect to
     */
    public abstract void applyEffect(Janitor janitor);

    /**
     * Sets the room of the effect
     * @param room the room of the effect
     */
    public void setRoom(Room room) {
        this.room = room;
    }

    /**
     * 
     * @return Deep copy of the effect
     */
    public abstract RoomEffect copy();
}
