package logarlec.model.room;

import java.util.HashSet;

import logarlec.control.rendering.EffectViewFactory;
import logarlec.model.actor.Actor;
import logarlec.model.actor.Janitor;
import logarlec.model.actor.Professor;
import logarlec.model.actor.Student;
import logarlec.view.panels.EffectPanel;

public class StickyEffect extends RoomEffect {
    private Integer stickyLimit;
    private HashSet<Actor> uniqueActors = new HashSet<>();

    /**
     * Creates a new StickyEffect
     */
    public StickyEffect(Room room) {
        super(room);
        timeLeft = Integer.MAX_VALUE;
        stickyLimit = 5;
    }

    private void checkLimit(Actor actor) {
        uniqueActors.add(actor);
        if (uniqueActors.size() >= stickyLimit) {
            room.setIsSticky(true);
            room.removeEffect(this);
        }
    }
    

    @Override
    public void applyEffect(Professor professor) {
        checkLimit(professor);
    }

    @Override
    public void applyEffect(Student student) {
        checkLimit(student);
    }

    @Override
    public void applyEffect(Janitor janitor) {
        checkLimit(janitor);
    }

    /**
     * The sticky effect does not expire
     * @return true, since the sticky effect does not expire
     */
    @Override
    public boolean tick(){
        return true;
    }

    @Override
    public RoomEffect copy() {
        return new StickyEffect(room);
    }

    @Override
    public EffectPanel createOwnView() {
        return new EffectViewFactory().createPanel(this);
    }
}
