package logarlec.model.actor;

import java.util.List;
import logarlec.model.actor.actions.ActionsState;
import logarlec.model.actor.actions.IActions;
import logarlec.model.actor.strategy.DefenseStrategy;
import logarlec.model.items.Inventory;
import logarlec.model.items.Item;
import logarlec.model.items.ItemFinder;
import logarlec.model.proxy.LogFunction;
import logarlec.model.proxy.Logger;
import logarlec.model.room.Door;
import logarlec.model.room.IHasLocation;
import logarlec.model.room.Room;
import logarlec.model.room.RoomEffect;

public abstract class Actor implements IHasLocation, IActions {
    private boolean alive;

    public abstract void attacked();

    public abstract boolean revive();

    public abstract void acceptEffect(RoomEffect effect, List<ItemFinder<Item>> unless);

    public abstract void setDefaultActionState();

    public void setActionState(ActionsState state) {
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

    @LogFunction
    public int mockFunction(int a, String b) {
        Logger.preExecute(this, "mockFunction", a, b);
        return Logger.postExecute(65);
    }
}
