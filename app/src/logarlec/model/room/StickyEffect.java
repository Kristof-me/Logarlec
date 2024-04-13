package logarlec.model.room;

import java.util.HashSet;

import logarlec.model.actor.Actor;
import logarlec.model.actor.Janitor;
import logarlec.model.actor.Professor;
import logarlec.model.actor.Student;

public class StickyEffect extends RoomEffect {
    private Integer stickyLimit;
    private HashSet<Actor> uniqueActors = new HashSet<>();

    /**
     * Creates a new StickyEffect
     */
    public StickyEffect(Room room) {
        super(room);
        stickyLimit = 5; // TODO change this value later
    }
    

    @Override
    public void applyEffect(Professor professor) {
        uniqueActors.add(professor);
        if (uniqueActors.size() >= stickyLimit) {
            room.setIsSticky(true);
        }
    }

    @Override
    public void applyEffect(Student student) {
        uniqueActors.add(student);
        if (uniqueActors.size() >= stickyLimit) {
            room.setIsSticky(true);
        }
    }

    @Override
    public void applyEffect(Janitor janitor) {
        uniqueActors.add(janitor);
        if (uniqueActors.size() >= stickyLimit) {
            room.setIsSticky(true);
        }
    }

    /**
     * The sticky effect does not expire
     * @return true, since the sticky effect does not expire
     */
    @Override
    public boolean tick(){
        return true;
    }
    
}
