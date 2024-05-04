package logarlec.control.controller;

import logarlec.model.actor.Janitor;
import logarlec.model.actor.Professor;

public class JanitorAI extends Controller{
    Janitor actor;
    public JanitorAI() {
        super();
        name = "Janitor Placeholder";
        actor = new Janitor();
    }
    @Override
    public void prepareTurn() {
        
    }

    public Janitor getActor() {
        return actor;
    }
    
}
