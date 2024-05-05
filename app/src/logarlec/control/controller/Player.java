package logarlec.control.controller;

import logarlec.control.GameManager;
import logarlec.model.actor.Actor;
import logarlec.model.actor.Student;
import logarlec.view.frames.GameFrame;
import logarlec.view.observerviews.View;
import logarlec.view.panels.PlayerPanel;

public class Player extends Controller {
    Student actor;
    public Player() {
        super();
        actor = new Student();
    }

    @Override
    public void prepareTurn() {
        PlayerPanel playerFrame = actor.createOwnView();
        playerFrame.bindPlayer(this);
        GameFrame.getInstance().setPlayerPanel(playerFrame);
    }

    public Student getActor() {
        return actor;
    }
    public void setActor(Student actor) {
        this.actor = actor;
    }
    
}
