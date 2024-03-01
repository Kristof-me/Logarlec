package logarlec.model.labyrinth;

public class Door {
    private int remainingInvisibility;
    private boolean isOneway;

    private Room[] rooms;

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
}
