package logarlec.model.actor;

import java.util.List;
import logarlec.model.actor.actions.ActionsState;
import logarlec.model.actor.actions.IActions;
import logarlec.model.actor.strategy.DefaultDefense;
import logarlec.model.actor.strategy.DefenseStrategy;
import logarlec.model.items.Inventory;
import logarlec.model.items.Item;
import logarlec.model.items.ItemFinder;
import logarlec.model.logger.Logger;
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
        Logger.preConstructor(this);
        this.alive = true;
        this.inventory = new Inventory();
        this.defenseStrategy = new DefaultDefense(this);
        Logger.postConstructor(this);
    }

    public abstract void attacked();

    public abstract boolean revive();

    public abstract void acceptEffect(RoomEffect effect, List<ItemFinder<? extends Item>> unless);

    public abstract void setDefaultActionState();

    public void setActionState(ActionsState state) {
        Logger.preExecute(this, "setActionState", state);

        actionState = actionState.setNextState(state);

        Logger.postExecute();
    }

    public void setDefenseStrategy(DefenseStrategy strategy) {
        Logger.preExecute(this, "setDefenseStrategy", strategy);

        defenseStrategy = strategy;

        Logger.postExecute();
    }

    public boolean teleport(Room room, boolean isForced) {
        Logger.preExecute(this, "teleport", room, isForced);

        boolean success = room.enter(this, isForced);
        if (isForced && !success) {
            alive = false;
        }
        if (success || isForced) {
            this.room = room;
        }

        return Logger.postExecute(success);
    }

    public Inventory getInventory() {
        Logger.preExecute(this, "getInventory");
        Logger.postExecute(inventory);
        return inventory;
    }

    public void dropAllTo(Room room) {
        Logger.preExecute(this, "dropAllTo", room);

        if(!alive) return; 
        inventory.dropAll(room);

        Logger.postExecute();
    }

    public void tick() {
        Logger.preExecute(this, "tick");
        if (actionState.tick() == false) {
            this.setDefaultActionState();
        }
        
        if (defenseStrategy.tick() == false) {
            setDefenseStrategy(new DefaultDefense(this));
        }

        Logger.postExecute();
    }

    @Override
    public void attack() {
        Logger.preExecute(this, "attack");

        if(!alive) return; 
        actionState.attack();

        Logger.postExecute();
    }

    @Override
    public boolean move(Door door) {
        Logger.preExecute(this, "move", door);

        if(!alive) return false; 

        boolean res = actionState.move(door);

        Logger.postExecute(res);
        return res;
    }

    @Override
    public void use(Item item) {
        Logger.preExecute(this, "use", item);

        if(!alive) return; 
        actionState.use(item);

        Logger.postExecute();
    }

    @Override
    public boolean pickUp(Item item) {
        Logger.preExecute(this, "pickUp", item);

        if(!alive) return false;
        boolean res = actionState.pickUp(item);

        Logger.postExecute(res);
        return res;
    }

    @Override
    public void drop(Item item) {
        Logger.preExecute(this, "drop", item);

        if(!alive) return; 
        actionState.drop(item);

        Logger.postExecute();
    }

    @Override
    public Room getLocation() {
        Logger.preExecute(this, "getLocation");
        Logger.postExecute(room);
        return room;
    }

    public boolean isAlive() {
        return alive;
    }
}
