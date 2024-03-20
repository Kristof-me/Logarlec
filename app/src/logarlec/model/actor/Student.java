package logarlec.model.actor;

import java.util.List;
import logarlec.model.actor.actions.ActionsState;
import logarlec.model.actor.strategy.DefenseStrategy;
import logarlec.model.items.Inventory;
import logarlec.model.items.Item;
import logarlec.model.items.ItemFinder;
import logarlec.model.room.Door;
import logarlec.model.room.Room;
import logarlec.model.room.RoomEffect;

public class Student extends Actor {
    @Override
    public void attacked() {
        // Implementation goes here
    }

    @Override
    public boolean revive() {
        // Implementation goes here
        return false;
    }

    @Override
    public void acceptEffect(RoomEffect effect, List<ItemFinder<Item>> unless) {
        // Implementation goes here
    }

    @Override
    public void setDefaultActionState() {
        // Implementation goes here
    }

    @Override
    public void setActionState(ActionsState state) {
        // Implementation goes here
    }

    @Override
    public void setDefenseStrategy(DefenseStrategy defenseStrategy) {
        // Implementation goes here
    }

    @Override
    public boolean teleport(Room room, boolean isForced) {
        // Implementation goes here
        return false;
    }

    @Override
    public Inventory getInventory() {
        // Implementation goes here
        return null;
    }

    @Override
    public void dropAllTo(Room room) {
        // Implementation goes here
    }

    @Override
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

    @Override
    public Room getLocation() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getLocation'");
    }
}
