package logarlec.control.controller;

import logarlec.control.GameManager;
import logarlec.control.rendering.ItemHolderViewFactory;
import logarlec.model.actor.Student;
import logarlec.model.items.Item;
import logarlec.model.room.Door;
import logarlec.view.frames.GameFrame;
import logarlec.view.panels.PlayerPanel;

public class Player extends Controller<Student> {
    public Player() {
        super(new Student());
    }

    public Player(Student student) {
        super(student);
    }

    public void skipTurn() {
        long count = GameManager.getInstance().getStepCount();
        for (long i = 0; i < count; i++) {
            System.out.println("Skipping turn");
            GameManager.getInstance().takeStep();
        }
    }

    @Override
    public void takeTurn() {
        // creates a new playerPanel if needed
        PlayerPanel playerFrame = new ItemHolderViewFactory().createMenuPanel(actor);        

        GameFrame.getInstance().setPlayerPanel(playerFrame);
    }

    @Override
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
        boolean re = actor.pickUp(item);
        if (re) GameManager.getInstance().takeStep();
        return re;
	}

    @Override
	public void drop(Item item) {
        super.drop(item);
        GameManager.getInstance().takeStep();
    }
}
