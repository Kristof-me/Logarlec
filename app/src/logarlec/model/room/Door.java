package logarlec.model.room;

import logarlec.model.actor.Actor;
import logarlec.model.logger.Logger;

public class Door {
    private Room room1;
    private Room room2;
    private int remainingInvisibility;
    private boolean isOneway;

    public Door(Room room1, Room room2, boolean isOneway) {
        Logger.preConstructor(this, room1, room2);
        this.room1 = room1;
        this.room2 = room2;
        this.remainingInvisibility = 0;
        this.isOneway = isOneway;
        Logger.postConstructor(this);
    }

    public void hide(int duration) {
        Logger.preExecute(this, "hide", duration);
        remainingInvisibility = duration;
        Logger.postExecute();
    }

    public Room leadsTo(Room from) {
        Logger.preExecute(this, "leadsTo", from);
        if (from == room1) {
            Logger.postExecute(room2);
            return room2;
        }

        if (from == room2 && !isOneway) {
            Logger.postExecute(room1);
            return room1;
        }

        Logger.postExecute(null);
        return null;
    }

    public boolean move(Actor actor, Room target) {
        Logger.preExecute(this, "move", actor, target);

        // if Door is invisible, we can't use it
        if (remainingInvisibility > 0) {
            Logger.postExecute(false);
            return false;
        }

        boolean isSuccesful = target.enter(actor, false);
        Logger.postExecute(isSuccesful);

        return isSuccesful;
    }

    public void tick() {
        Logger.preExecute(this, "tick");
        // Implementation goes here
        Logger.postExecute();
    }
}
