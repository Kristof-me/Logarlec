package logarlec.model.room;

import logarlec.model.actor.Actor;

public class Door {
    private Room room1;
    private Room room2;
    private int remainingInvisibility;
    private boolean isOneway;

    public void hide(int duration) {
        // Implementation goes here
    }

    public Room leadsTo(Room from) {
        // Implementation goes here
        return null;
    }

    public boolean move(Actor actor, Room target) {
        // Implementation goes here
        return false;
    }

    public void tick() {
        // Implementation goes here
    }
}
