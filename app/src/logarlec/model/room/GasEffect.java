package logarlec.model.room;

import logarlec.model.actor.Actor;
import logarlec.model.actor.Janitor;
import logarlec.model.actor.Professor;
import logarlec.model.actor.Student;
import logarlec.model.logger.Logger;
import logarlec.model.logger.Uses;
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
     * 
     * @param timeLeft effect for the gas to be active
     */
    public GasEffect(Integer timeLeft) {
        Logger.preConstructor(this, timeLeft);
        itemFinders.add(new BestGasMaskFinder());
        Logger.postConstructor(this);
    }

    /**
     * Apply the effect to the professor.<br>
     * Based on visitor pattern
     */
    @Override
    public void applyEffect(Professor professor) {
        Logger.preExecute(this, "applyEffect", professor);
        professor.dropAllTo(professor.getLocation());
        Logger.postExecute();
    }

    /**
     * Apply the effect to the student.<br>
     * Based on visitor pattern
     */
    @Override
    public void applyEffect(Student student) {
        Logger.preExecute(this, "applyEffect", student);
        student.dropAllTo(student.getLocation());
        Logger.postExecute();
    }

    /**
     * Apply the effect to the janitor.<br>
     * Based on visitor pattern
     */
    @Override
    public void applyEffect(Janitor janitor) {
        Logger.preExecute(this, "applyEffect", janitor);
        janitor.dropAllTo(janitor.getLocation());
        Logger.postExecute();
    }

    /**
     * Add effect on an actor.
     */
    @Override
    public void addEffect(Actor actor) {
        Logger.preExecute(this, "addEffect", actor);
        actor.acceptEffect(this, itemFinders);
        Logger.postExecute();
    }

    @Override
    @Uses(fields = { "timeLeft" })
    public boolean tick() {
        Logger.preExecute(this, "tick");
        return Logger.postExecute(super.tick());
    }

    @Override
    public boolean clean(){
        Logger.preExecute(this, "clean");
        return Logger.postExecute(true); // cleanable
    }
}
