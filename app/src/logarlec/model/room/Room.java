public class Room {
    private int capacity;
    private List<Actor> actors;
    private List<RoomEffect> roomEffects;
    private Invetory inventory;

    public void split() {
        // Implementation goes here
    }

    public void merge(Room room) {
        // Implementation goes here
    }

    public void attack(Actor attacker) {
        // Implementation goes here
    }

    public void addEffect(RoomEffect effect) {
        // Implementation goes here
    }

    public boolean enter(Actor actor, boolean isForced) {
        // Implementation goes here
        return false;
    }

    public void leave(Actor actor) {
        // Implementation goes here
    }

    public boolean revive() {
        // Implementation goes here
        return false;
    }

    private boolean isFull() {
        // Implementation goes here
        return false;
    }

    public void addItem(Item item) {
        // Implementation goes here
    }

    public Item removeItem(Item item) {
        // Implementation goes here
        return null;
    }

    public void tick() {
        // Implementation goes here
    }
}