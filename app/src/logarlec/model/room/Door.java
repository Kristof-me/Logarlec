package logarlec.model.room;

import logarlec.model.actor.Actor;
import logarlec.model.logger.Logger;

public class Door {
    private Room room1;
    private Room room2;
    private int remainingInvisibility;
    private boolean isOneway;

    public void hide(int duration) {
        Logger.preExecute(this, duration);
        remainingInvisibility = duration;
        Logger.postExecute();
    }

    public Room leadsTo(Room from) {
        Logger.preExecute(this, from);
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
        Logger.preExecute(this, actor, target);

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
        Logger.preExecute(this);
        // Implementation goes here
        Logger.postExecute();
    }
}
