package logarlec.model.labyrinth;

public class Door {
    private int remainingInvisibility;
    private boolean isOneway;

    private Room[] rooms; // [0] = from, [1] = to

    public Door(Room from, Room to, boolean isOneway) {
        this.remainingInvisibility = 0;
        this.isOneway = isOneway;

        this.rooms = new Room[] {from, to};
    }

    public Door(Room from, Room to) {
        this(from, to, false);
    }

    public void hide(int duration) {
        this.remainingInvisibility = duration;
    }

    public boolean getIsOneWay() {
        return isOneway;
    }

    public Room[] getRooms() {
        return rooms;
    }
}
