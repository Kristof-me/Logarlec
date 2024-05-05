package logarlec.control.controller;

import logarlec.model.actor.Professor;

public class ProfessorAI extends Controller{
    Professor actor;
    public ProfessorAI() {
        super();
        name = "Professor Placeholder";
        actor = new Professor();
    }
    @Override
    public void takeTurn() {

    }

    public Professor getActor() {
        return actor;
    }
    
}
