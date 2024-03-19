import java.util.List;

public abstract class Actor implements IHasLocation, IActions {
    private boolean alive;

    public abstract void attacked();
    public abstract boolean revive();
    public abstract void acceptEffect(RoomEffect effect, List<ItemFinder> unless);

    public abstract void setDefaultActionState();
    public void setActionState(ActionState state) {
        // Implementation goes here
    }
    public void setDefenseStrategy(DefenseStrategy defenseStrategy) {
        // Implementation goes here
    }

    public boolean teleport(Room room, boolean isForced) {
        // Implementation goes here
        return false;
    }
    public Inventory getInventory() {
        // Implementation goes here
        return null;
    }
    public void dropAllTo(Room room) {
        // Implementation goes here
    }
    public void tick() {
        // Implementation goes here
    }

    @Override
    public void attack() {
        // Implementation goes here
    }

    @Override
    public boolean move(Door door) {
        // Implementation goes here
        return false;
    }

    @Override
    public void use(Item item) {
        // Implementation goes here
    }

    @Override
    public boolean pickUp(Item item) {
        // Implementation goes here
        return false;
    }

    @Override
    public void drop(Item item) {
        // Implementation goes here
    }
}