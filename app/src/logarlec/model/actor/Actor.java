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
    protected boolean alive;
    private Room room;
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

    public abstract void acceptEffect(RoomEffect effect, List<ItemFinder<Item>> unless);

    public abstract void setDefaultActionState();

    public void setActionState(ActionsState state) {
        Logger.preExecute(this,"setActionState" ,state);

        actionState = state;

        Logger.postExecute();
    }

    public void setDefenseStrategy(DefenseStrategy strategy) {
        Logger.preExecute(this,"setDefenseStrategy" ,strategy);

        defenseStrategy = strategy;

        Logger.postExecute();
    }

    public boolean teleport(Room room, boolean isForced) {
        Logger.preExecute(this,"teleport" ,room, isForced);

        
        boolean res = room.enter(this, isForced);
        if (isForced && !res) {
            alive = false;
        }
        if (res || isForced) {
            this.room = room;
        }

        Logger.postExecute(res);
        return res;
    }

    public Inventory getInventory() {
        Logger.preExecute(this, "getInventory");
        Logger.postExecute(inventory);
        return inventory;
    }

    public void dropAllTo(Room room) {
        Logger.preExecute(this, "dropAllTo", room);

        inventory.dropAll(room);

        Logger.postExecute();
    }

    public void tick() {
        Logger.preExecute(this, "tick");
        if (actionState.tick()) {
            this.setDefaultActionState();
        }
        
        if (defenseStrategy.tick()) {
            setDefenseStrategy(new DefaultDefense(this));
        }

        Logger.postExecute();
    }

    @Override
    public void attack() {
        Logger.preExecute(this, "attack");
        actionState.attack();
        Logger.postExecute();
    }

    @Override
    public boolean move(Door door) {
        Logger.preExecute(this, "move", door);

        boolean res = actionState.move(door);
        
        Logger.postExecute(res);
        return res;
    }

    @Override
    public void use(Item item) {
        Logger.preExecute(this, "use", item);

        actionState.use(item);

        Logger.postExecute();
    }

    @Override
    public boolean pickUp(Item item) {
        Logger.preExecute(this, "pickUp", item);

        boolean res = actionState.pickUp(item);

        Logger.postExecute(res);
        return res;
    }

    @Override
    public void drop(Item item) {
        Logger.preExecute(this, "drop", item);

        actionState.drop(item);

        Logger.postExecute();
    }

    @Override
    public Room getLocation() { 
        Logger.preExecute(this, "getLocation");
        Logger.postExecute(room);
        return room;
    }

    public int mockFunction(int a, String b) {
        Logger.preExecute(this, "mockFunction", a, b);
        return Logger.postExecute(65);
    }
}
