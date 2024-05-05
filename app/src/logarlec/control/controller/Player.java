package logarlec.control.controller;

import logarlec.control.GameManager;
import logarlec.model.actor.Actor;
import logarlec.model.actor.Student;
import logarlec.model.items.Item;
import logarlec.model.room.Door;
import logarlec.view.frames.GameFrame;
import logarlec.view.observerviews.View;
import logarlec.view.panels.PlayerPanel;

public class Player extends Controller {
    Student actor;
    public Player() {
        super();
        actor = new Student();
    }
    public void skipTurn() {
        for (long i = 0; i < GameManager.getInstance().getStepCount(); i++) {
            GameManager.getInstance().takeStep();
        }
    }
    @Override
    public void takeTurn() {
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
    @Override
    public boolean move(Door door) {
        boolean re = super.move(door);
        if (re) GameManager.getInstance().takeStep();
        return re;
	}
    @Override
	public void use(Item item) {
        super.use(item);
        GameManager.getInstance().takeStep();
	}
    @Override
	public boolean pickUp(Item item) {
        boolean re = getActor().pickUp(item);
        if (re) GameManager.getInstance().takeStep();
        return re;
	}
    @Override
	public void drop(Item item) {
        super.drop(item);
        GameManager.getInstance().takeStep();
    }
}
