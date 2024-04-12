package logarlec.model.room;

import logarlec.model.actor.Actor;




/**
 * A door between two rooms.
 * Helps the actor navigate between the two rooms it connects.
 * Can also be invisible for a certain amount of time.
 * Can be one-way.
 */
public class Door {
    private Room[] rooms = new Room[2];

    private Integer remainingInvisibility = 0;

    private Boolean isOneway = false;

    /**
     * Creates a new door between two rooms.
     * 
     * @param room1    The first room.
     * @param room2    The second room.
     * @param isOneway Whether the door is one-way.
     */
    public Door(Room room1, Room room2, boolean isOneway) {
        this.rooms[0] = room1;
        this.rooms[1] = room2;
    }

    /**
     * Hides the door for a certain amount of time.
     * 
     * @param duration The duration of the invisibility.
     * @implNote used to have - @Uses(fields = {"remainingInvisibility"})
     */
    public void hide(Integer duration) {
        remainingInvisibility = duration;
        
    }

    /**
     * Returns the room that the door leads to from the given room.
     * 
     * @param from The room to start from.
     * @return The room that the door leads to.
     */
    public Room leadsTo(Room from) {
        if (from == rooms[0]) {
            return rooms[1];
        }

        if (from == rooms[1] && !isOneway) {
            return rooms[0];
        }

        return null;
    }

    /**
     * Moves the actor to the target room. This move is NOT forced, so if the room
     * is full it will fail.
     * 
     * @param actor  The actor to move.
     * @param target The room to move to.
     * @return Whether the move was successful.
     */
    public boolean move(Actor actor, Room target) {

        // if Door is invisible, we can't use it
        if (remainingInvisibility > 0 || (isOneway && target != rooms[1])
                || (target != rooms[0] && target != rooms[1])) {
            // oneway doors can only be used to go to the second room
            return false;
        }

        Room currLocation = actor.getLocation();
        actor.setLocation(target);
        boolean isSuccesful = target.enter(actor, false);

        if (!isSuccesful) {
            actor.setLocation(currLocation);
        }
        return isSuccesful;
    }

    /**
     * Ticks the door, decreasing the remaining invisibility time.
     */
    public void tick() {
        if (remainingInvisibility > 0) {
            // KÉT SZOBA HÍVJA A TICKET !!! VAGYIS CSAK MINDEN MÁSODIKRA FUTHAT LE
            remainingInvisibility--;
        }
        
    }

    /**
     * Updates the door to a new room.
     * 
     * @param original The room to replace.
     * @param current  The new room.
     */
    public void updateRoom(Room original, Room current) {
        if (rooms[0] == original) {
            rooms[0] = current;
        } else if (rooms[1] == original) {
            rooms[1] = current;
        }
    }
}
