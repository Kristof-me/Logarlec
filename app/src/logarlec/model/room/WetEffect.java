package logarlec.model.room;

import logarlec.model.actor.Actor;
import logarlec.model.actor.Professor;
import logarlec.model.actor.Student;
import logarlec.model.actor.actions.StunnedStep;
import logarlec.model.logger.Logger;
import logarlec.model.logger.Uses;

/**
 * WetEffect is a RoomEffect that applies a StunnedStep to a Professor <br>
 * so it can't move or attack students.
 */
public class WetEffect extends RoomEffect {
    /**
     * Constructor for WetEffect
     */
    public WetEffect() {
        Logger.preConstructor(this);
        Logger.postConstructor(this);
    }

    /**
     * Apply the effect to a Professor (visitor pattern)
     * 
     * @param professor the Professor to apply the effect to
     */
    @Override
    public void applyEffect(Professor professor) {
        Logger.preExecute(this, "applyEffect", professor);
        StunnedStep step = new StunnedStep(professor);
        professor.setActionState(step);
        Logger.postExecute();
    }

    /**
     * Apply the effect to a Student (visitor pattern, so this is an empty
     * implementation)
     * 
     * @param student the Student to apply the effect to
     */
    @Override
    public void applyEffect(Student student) {
        Logger.preExecute(this, "applyEffect", student);
        Logger.postExecute();

    }

    /**
     * Add the effect to an Actor (visitor pattern)
     * 
     * @param actor the Actor to add the effect to
     */
    @Override
    public void addEffect(Actor actor) {
        Logger.preExecute(this, "addEffect", actor);
        actor.acceptEffect(this, null);
        Logger.postExecute();
    }

    @Override
    @Uses(fields = { "timeLeft" })
    public boolean tick() {
        Logger.preExecute(this, "tick");
        return Logger.postExecute(super.tick());
    }
}
