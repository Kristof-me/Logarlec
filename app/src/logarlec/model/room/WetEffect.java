package logarlec.model.room;

import logarlec.control.rendering.EffectViewFactory;
import logarlec.model.actor.Actor;
import logarlec.model.actor.Janitor;
import logarlec.model.actor.Professor;
import logarlec.model.actor.Student;
import logarlec.model.actor.actions.StunnedStep;
import logarlec.view.observerviews.View;
import logarlec.view.panels.EffectPanel;



/**
 * WetEffect is a RoomEffect that applies a StunnedStep to a Professor <br>
 * so it can't move or attack students.
 */
public class WetEffect extends RoomEffect {

    /**
     * Constructor for WetEffect
     */
    public WetEffect(Room room) {
        super(room);
        timeLeft = 10;
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
        // Student is not affected by the wet effect
    }

    /**
     * Apply the effect to a Jantitor (visitor pattern, so this is an empty
     * implementation)
     * 
     * @param student the Janitor to apply the effect to
     */
    @Override
    public void applyEffect(Janitor janitor) {
        // Janitor is not affected by the wet effect
    }

    @Override
    public RoomEffect copy() {
        return new WetEffect(room);
    }

    @Override
    public EffectPanel createOwnView() {
        return new EffectViewFactory().createPanel(this);
    }
}
