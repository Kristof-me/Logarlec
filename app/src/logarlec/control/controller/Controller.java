package logarlec.control.controller;

import java.awt.Color;

import logarlec.model.actor.Actor;

public abstract class Controller {
    protected String name;
    protected Color color;

    protected Controller() {
        this.name = "";
        //random color
        generateColor();
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }
    public void generateColor() {
        this.color = new Color((int)(Math.random() * 0x1000000));
    }
    public Color getColor() {
        return this.color;
    }

    public abstract void prepareTurn();
    
}
