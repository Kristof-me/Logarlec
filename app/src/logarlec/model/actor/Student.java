package logarlec.model.actor;

import java.util.List;
import logarlec.model.actor.actions.StudentActions;
import logarlec.model.items.Item;
import logarlec.model.items.ItemFinder;
import logarlec.model.logger.Logger;
import logarlec.model.room.RoomEffect;

public class Student extends Actor {
    public Student() {
        super();
        this.actionState = new StudentActions(this);
        Logger.postConstructor(this);
    }

    @Override
    public void attacked() {
        Logger.preExecute(this, "attacked");
        alive = defenseStrategy.defend(inventory);
        Logger.postExecute();
    }

    @Override
    public boolean revive() {
        Logger.preExecute(this, "revive");
        if (alive) {
            return Logger.postExecute(false);
        }

        alive = true;
        return Logger.postExecute(true);
    }

    @Override
    public void acceptEffect(RoomEffect effect, List<ItemFinder<? extends Item>> unless) {
        Logger.preExecute(this, "acceptEffect", effect, unless);

        if (unless != null) {
            for (ItemFinder<? extends Item> itemFinder : unless) {
                var protector = itemFinder.findIn(inventory);
                if (protector != null) {
                    actionState.use(protector);
                    Logger.postExecute();
                    return;
                }
            }
        }

        effect.applyEffect(this);
        Logger.postExecute();
    }

    @Override
    public void setDefaultActionState() {
        Logger.preExecute(this, "setDefaultActionState");
        actionState = new StudentActions(this);
        Logger.postExecute();
    }
}
