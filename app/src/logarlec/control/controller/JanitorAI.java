package logarlec.control.controller;

import logarlec.model.actor.Janitor;
import logarlec.model.actor.Professor;
import logarlec.model.items.Item;
import logarlec.model.room.Door;

public class JanitorAI extends Controller{
    Janitor actor;
    public JanitorAI() {
        super();
        name = "Janitor Placeholder";
        actor = new Janitor();
    }
    @Override
    public void takeTurn() {
        
    }

    public Janitor getActor() {
        return actor;
    }
    
}
