package logarlec.model.actor;

import java.util.List;
import logarlec.model.actor.actions.ActionsState;
import logarlec.model.actor.actions.StudentActions;
import logarlec.model.actor.strategy.DefenseStrategy;
import logarlec.model.items.Inventory;
import logarlec.model.items.Item;
import logarlec.model.items.ItemFinder;
import logarlec.model.logger.Logger;
import logarlec.model.room.Door;
import logarlec.model.room.Room;
import logarlec.model.room.RoomEffect;

public class Student extends Actor {
    @Override
    public void attacked() {
        Logger.preExecute(this);
        alive = defenseStrategy.defend(inventory);
        Logger.postExecute();
    }

    @Override
    public boolean revive() {
        Logger.preExecute(this);
        if (alive) {
            Logger.postExecute(false);
            return false;
        } else {
            alive = true;
            Logger.postExecute(true);
            return true;
        }
    }

    @Override
    public void acceptEffect(RoomEffect effect, List<ItemFinder<Item>> unless) {
        Logger.preExecute(this, effect, unless);

        for (ItemFinder<Item> itemFinder : unless) {
            if (itemFinder.findIn(inventory) != null) {
                Logger.postExecute();
                return;
            }
        }

        effect.applyEffect(this);
        Logger.postExecute();
    }

    @Override
    public void setDefaultActionState() {
        Logger.preExecute(this);
        actionState = new StudentActions();
        Logger.postExecute();
    }
}
