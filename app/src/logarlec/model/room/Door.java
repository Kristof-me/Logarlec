package logarlec.model.room;

import logarlec.model.actor.Actor;
import logarlec.model.logger.Logger;
import logarlec.model.logger.State;
import logarlec.model.logger.Uses;

public class Door {
    private Room[] rooms = new Room[2];

    @State(name = "remainingInvisibility", min = 0, max = Integer.MAX_VALUE)
    private Integer remainingInvisibility = null;

    @State(name = "isOneway")
    private Boolean isOneway = null;

    public Door(Room room1, Room room2, boolean isOneway) {
        Logger.preConstructor(this, rooms[0], rooms[1]);
        this.rooms[0] = room1;
        this.rooms[1] = room2;
        Logger.postConstructor(this);
    }

    @Uses(fields = {"remainingInvisibility"})
    public void hide(int duration) {
        Logger.preExecute(this, "hide", duration);
        remainingInvisibility = duration;
        Logger.postExecute();
    }

    @Uses(fields = {"isOneway"})
    public Room leadsTo(Room from) {
        Logger.preExecute(this, "leadsTo", from);
        if (from == rooms[0]) {
            Logger.postExecute(rooms[1]);
            return rooms[1];
        }

        if (from == rooms[1] && !isOneway) {
            Logger.postExecute(rooms[0]);
            return rooms[0];
        }

        return Logger.postExecute(null);
    }

    @Uses(fields = {"remainingInvisibility"})
    public boolean move(Actor actor, Room target) {
        Logger.preExecute(this, "move", actor, target);

        // if Door is invisible, we can't use it
        if (remainingInvisibility > 0 || (isOneway && target != rooms[1]) || (target != rooms[0] && target != rooms[1])) {
            // oneway doors can only be used to go to the second room
            return Logger.postExecute(false);
        }

        boolean isSuccesful = target.enter(actor, false);
        return Logger.postExecute(isSuccesful);
    }

    @Uses(fields = {"remainingInvisibility"})
    public void tick() {
        Logger.preExecute(this, "tick");
        if(remainingInvisibility > 0) {
            remainingInvisibility--;
        }
        Logger.postExecute();
    }

    public void updateRoom(Room original, Room current) {
        // ! setter, hidden
        if(rooms[0] == original) {
            rooms[0] = current;
        } else if(rooms[1] == original) {
            rooms[1] = current;
        }
    }
}
