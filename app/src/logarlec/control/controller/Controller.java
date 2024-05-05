package logarlec.control.controller;

import java.awt.Color;

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
        return getActor().move(door);
	}

	public void use(Item item) {
        getActor().use(item);
	}

	public boolean pickUp(Item item) {
        return getActor().pickUp(item);
	}

	public void drop(Item item) {
        getActor().drop(item);
	}
}
