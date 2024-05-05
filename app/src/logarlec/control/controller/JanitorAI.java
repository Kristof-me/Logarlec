package logarlec.control.controller;

import logarlec.model.actor.Janitor;

public class JanitorAI extends Controller<Janitor> {
    public JanitorAI() {
        super(new Janitor());
    }
    
    @Override
    public void takeTurn() {
        
    }

    @Override
    public Janitor getActor() {
        return actor;
    }
    
}
