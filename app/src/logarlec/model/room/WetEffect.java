package logarlec.model.room;

import logarlec.model.actor.Actor;
import logarlec.model.actor.Janitor;
import logarlec.model.actor.Professor;
import logarlec.model.actor.Student;
import logarlec.model.actor.actions.StunnedStep;



/**
 * WetEffect is a RoomEffect that applies a StunnedStep to a Professor <br>
 * so it can't move or attack students.
 */
public class WetEffect extends RoomEffect {
    /**
     * Constructor for WetEffect
     */
    public WetEffect() {
        
        
    }

    /**
     * Apply the effect to a Professor (visitor pattern)
     * 
     * @param professor the Professor to apply the effect to
     */
    @Override
    public void applyEffect(Professor professor) {
        StunnedStep step = new StunnedStep(professor);
        professor.setActionState(step);
        
    }

    /**
     * Apply the effect to a Student (visitor pattern, so this is an empty
     * implementation)
     * 
     * @param student the Student to apply the effect to
     */
    @Override
    public void applyEffect(Student student) {
        

    }

    /**
     * Apply the effect to a Jantitor (visitor pattern, so this is an empty
     * implementation)
     * 
     * @param student the Janitor to apply the effect to
     */
    @Override
    public void applyEffect(Janitor janitor) {
        

    }

    /**
     * Add the effect to an Actor (visitor pattern)
     * 
     * @param actor the Actor to add the effect to
     */
    @Override
    public void addEffect(Actor actor) {
        actor.acceptEffect(this, null);
        
    }

    @Override
    public boolean tick() {
        return super.tick();
    }

    @Override
    public boolean clean(){
        return false; // no cleanable
    }
}
