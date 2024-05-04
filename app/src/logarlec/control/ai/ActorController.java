package logarlec.control.ai;

import logarlec.model.actor.Actor;

public abstract class ActorController<T extends Actor>{
    /*
     * This method is called with an actor, and preforms the next step that the actor should take.
     * @param actor The actor to preform the next step for.
     */
    public abstract void step(T actor);
}
