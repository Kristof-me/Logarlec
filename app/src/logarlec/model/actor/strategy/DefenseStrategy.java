package logarlec.model.actor.strategy;

import logarlec.model.actor.Actor;
import logarlec.model.items.Inventory;


/**
 * Abstract class to defend an actor.
 */
public abstract class DefenseStrategy {
    protected Actor actor;

    /**
     * Constructor.
     * @param actor Actor to defend.
     */
    protected DefenseStrategy(Actor actor) {
        this.actor = actor;
    }
    
    /**
     * Tick method to update the defense strategy.
     * @return True if the defense strategy still applied, false otherwise.
     */
    public boolean tick() { 
        // the default defense strategy does not expire
        return true;
    }

    /**
     * Defend the actor.
     * @param inventory Inventory of the actor.
     * @return True if the actor successfully defended, false otherwise.
     */
    public boolean defend(Inventory inventory) {
        return false;
    }
}
