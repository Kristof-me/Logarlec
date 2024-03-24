package logarlec.model.actor.actions;

import logarlec.model.items.Inventory;
import logarlec.model.items.Item;
import logarlec.model.room.Door;
import logarlec.model.actor.Actor;

import logarlec.model.logger.*;

public abstract class ActionsState implements IActions {
    protected Actor actor;
    
    public ActionsState(Actor actor) {
        Logger.preConstructor(this);
        this.actor = actor;
        Logger.postConstructor(this);
    }

    public boolean tick() {
        return false;
    }

    @Override
    public void attack() {

    }

    @Override
    public boolean move(Door door) {
        return false;
    }

    @Uses(fields = {"actor"})
    @Override
    public void use(Item item) {
        Logger.preExecute(this, "use", item);
        item.use(actor);
        Logger.postExecute();
    }

    @Override
    public boolean pickUp(Item item) {
        Logger.preExecute(this, "pickUp", item);
        Inventory inventory = actor.getInventory();
        return Logger.postExecute(inventory.addItem(item));
    }

    @Override
    public void drop(Item item) {
        Logger.preExecute(this, "pickUp", item);
        Inventory inventory = actor.getInventory();
        inventory.removeItem(item);
        Logger.postExecute();
    }

    public abstract ActionsState setNextState(ActionsState state);
}
