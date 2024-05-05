package logarlec.control.controller;

import java.awt.Color;

import logarlec.model.actor.Actor;
import logarlec.model.actor.actions.IActions;
import logarlec.model.items.Item;
import logarlec.model.room.Door;
import logarlec.view.utility.ColorGenerator;

public abstract class Controller implements IActions {
    protected String name;
    protected Color color;

    protected Controller() {
        this.name = "";
        //random color
        this.color = ColorGenerator.getInstance().random();
    }
    
    public abstract Actor getActor();

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public Color getColor() {
        return this.color;
    }

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
