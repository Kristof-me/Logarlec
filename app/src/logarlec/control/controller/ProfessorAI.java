package logarlec.control.controller;

import logarlec.model.actor.Professor;

public class ProfessorAI extends Controller<Professor> {    
    public ProfessorAI() {
        super(new Professor());
    }

    @Override
    public void takeTurn() {

    }

    @Override
    public Professor getActor() {
        return actor;
    }
    
}