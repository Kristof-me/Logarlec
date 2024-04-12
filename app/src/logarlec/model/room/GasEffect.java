package logarlec.model.room;

import logarlec.model.actor.Actor;
import logarlec.model.actor.Janitor;
import logarlec.model.actor.Professor;
import logarlec.model.actor.Student;


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
    public GasEffect() {
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
        
    }

    /**
     * Apply the effect to the student.<br>
     * Based on visitor pattern
     */
    @Override
    public void applyEffect(Student student) {
        student.dropAllTo(student.getLocation());
        
    }

    /**
     * Apply the effect to the janitor.<br>
     * Based on visitor pattern
     */
    @Override
    public void applyEffect(Janitor janitor) {
        // todo   
    }

    /**
     * Add effect on an actor.
     */
    @Override
    public void addEffect(Actor actor) {
        actor.acceptEffect(this, itemFinders);   
    }

    @Override
    public boolean tick() {
        return super.tick();
    }

    @Override
    public boolean clean(){
        return true; // cleanable
    }
}
