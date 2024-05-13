package logarlec.control.controller;

import logarlec.model.actor.Actor;
import logarlec.model.actor.actions.IActions;
import logarlec.model.items.Item;
import logarlec.model.room.Door;

public abstract class Controller<T extends Actor> implements IActions {
    protected T actor;
    
    protected Controller(T actor) {
        this.actor = actor;
    }

    public abstract Actor getActor();

    public abstract void takeTurn();

    public void attack() { }

    public boolean move(Door door) {
        return actor.move(door);
    }

    public void use(Item item) {
        actor.use(item);
    }

    public boolean pickUp(Item item) {
        return actor.pickUp(item);
    }

    public void drop(Item item) {
        actor.drop(item);
    }
}
