package logarlec.model.room;

import logarlec.model.actor.Actor;
import logarlec.model.actor.Janitor;
import logarlec.model.actor.Professor;
import logarlec.model.actor.Student;
import logarlec.model.actor.actions.StunnedStep;
import logarlec.model.items.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Gas effect that removes all items from the actor<br>
 * drops them in the current room<br>
 * and applies stun for a certain amount of time
 */
public class GasEffect extends RoomEffect {
    List<ItemFinder<? extends Item>> itemFinders = new ArrayList<>();

    /**
     * Constructor for GasEffect
     */
    public GasEffect(Room room) {
        super(room);
        itemFinders.add(new BestGasMaskFinder());
        // ! PLEASE DON'T MAKE THIS A CONSTRUCTOR PARAMETER
        timeLeft = 5; // TODO change this value later 
    }

    /**
     * Apply the effect to the professor.<br>
     * Based on visitor pattern
     */
    @Override
    public void applyEffect(Professor professor) {
        professor.dropAllTo(professor.getLocation());
        StunnedStep stunnedStep = new StunnedStep(professor);
        professor.setActionState(stunnedStep);
    }

    /**
     * Apply the effect to the student.<br>
     * Based on visitor pattern
     */
    @Override
    public void applyEffect(Student student) {
        student.dropAllTo(student.getLocation());
        StunnedStep stunnedStep = new StunnedStep(student);
        student.setActionState(stunnedStep);
    }

    /**
     * Apply the effect to the janitor, which removes this effect.
     * Based on visitor pattern
     */
    @Override
    public void applyEffect(Janitor janitor) {
        room.removeEffect(this);
    }

    /**
     * Add effect on an actor.
     */
    @Override
    public void addEffect(Actor actor) {
        actor.acceptEffect(this, itemFinders);   
    }

    @Override
    public boolean clean(){
        room.removeEffect(this);
        return true; // cleanable
    }

    @Override
    public RoomEffect copy() {
        return new GasEffect(room);
    }
}
