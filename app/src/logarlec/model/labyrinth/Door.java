package logarlec.model.labyrinth;

public class Door {
    private int remainingInvisibility;
    private boolean oneWay;

    private Room[] rooms; // if oneway [0] = from, [1] = to

    public Door(Room from, Room to, boolean isOneway) {
        this.remainingInvisibility = 0;
        this.oneWay = isOneway;

        this.rooms = new Room[] {from, to};
    }

    public Door(Room from, Room to) {
        this(from, to, false);
    }

    public void hide(int duration) {
        this.remainingInvisibility = duration;
    }

    public boolean isOneWay() {
        return oneWay;
    }

    public Room getOtherRoom(Room room) {
        if (room == rooms[0]) {
            return rooms[1];
        } else if (room == rooms[1]) {
            return rooms[0];
        } else {
            return null;
        }
    }

    public void tick() {

    }

    public boolean isVisible() {
        return remainingInvisibility == 0;
    }
}
