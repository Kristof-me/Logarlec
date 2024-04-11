package logarlec.model.actor;

import java.util.List;
import logarlec.model.actor.actions.ActionsState;
import logarlec.model.actor.actions.IActions;
import logarlec.model.actor.strategy.DefaultDefense;
import logarlec.model.actor.strategy.DefenseStrategy;
import logarlec.model.items.Inventory;
import logarlec.model.items.Item;
import logarlec.model.items.ItemFinder;

import logarlec.model.room.Door;
import logarlec.model.room.IHasLocation;
import logarlec.model.room.Room;
import logarlec.model.room.RoomEffect;

public abstract class Actor implements IHasLocation, IActions {
    protected Room room;
    protected boolean alive;
    protected ActionsState actionState;
    protected DefenseStrategy defenseStrategy;
    protected Inventory inventory;

    protected Actor() {
        this.alive = true;
        this.inventory = new Inventory(null);
        this.defenseStrategy = new DefaultDefense(this);
    }

    public abstract void attacked();

    public abstract boolean revive();

    public abstract void acceptEffect(RoomEffect effect, List<ItemFinder<? extends Item>> unless);

    public abstract void setDefaultActionState();

    public void setActionState(ActionsState state) {

        actionState = actionState.setNextState(state);

    }

    public void setDefenseStrategy(DefenseStrategy strategy) {

        defenseStrategy = strategy;

    }

    public boolean teleport(Room room, boolean isForced) {

        Room prevRoom = this.room;
        this.room = room;

        boolean staysAlive = room.enter(this, isForced);
        if (isForced && !staysAlive) {
            alive = false;
        }

        if (!staysAlive && !isForced) {
            this.room = prevRoom;
        } else {
            if (prevRoom != null) {
                prevRoom.leave(this);
            }
        }
        return staysAlive;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void dropAllTo(Room room) {

        if (!alive)
            return;
        inventory.dropAll(room);

    }

    public void tick() {
        if (actionState.tick() == false) {
            this.setDefaultActionState();
        }

        if (defenseStrategy.tick() == false) {
            setDefenseStrategy(new DefaultDefense(this));
        }

    }

    @Override
    public void attack() {

        if (!alive)
            return;
        actionState.attack();

    }

    @Override
    public boolean move(Door door) {

        if (!alive)
            return false;
        boolean success = actionState.move(door);

        return success;
    }

    @Override
    public void use(Item item) {

        if (!alive)
            return;
        actionState.use(item);

    }

    @Override
    public boolean pickUp(Item item) {

        if (!alive)
            return false;
        boolean res = actionState.pickUp(item);

        return res;
    }

    @Override
    public void drop(Item item) {

        if (!alive)
            return;
        actionState.drop(item);

    }

    @Override
    public Room getLocation() {
        return room;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setLocation(Room location) {
        // hidden set, not logged
        this.room = location;
    }
}
