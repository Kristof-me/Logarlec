package logarlec.model.actor;

import java.util.List;
import logarlec.model.actor.actions.ProfessorActions;
import logarlec.model.items.Item;
import logarlec.model.items.ItemFinder;
import logarlec.model.logger.Logger;
import logarlec.model.room.RoomEffect;

public class Professor extends Actor {
    public Professor() {
        super();
        Logger.preConstructor(this);
        this.actionState = new ProfessorActions(this);
        Logger.postConstructor(this);
    }
    @Override
    public void attacked() {
        Logger.preExecute(this, "attacked");
        
        Logger.postExecute();
    }

    @Override
    public boolean revive() {
        Logger.preExecute(this, "revive");
        Logger.postExecute(false);
        return false;
    }

    @Override
    public void acceptEffect(RoomEffect effect, List<ItemFinder<? extends Item>> unless) {
       Logger.preExecute(this, "acceptEffect", effect, unless);

       for (ItemFinder<? extends Item> itemFinder : unless) {
            var protector = itemFinder.findIn(inventory);
            
            if (protector != null) {
                actionState.use(protector);
                Logger.postExecute();
                return;
            }
       }

       effect.applyEffect(this);
       Logger.postExecute();
    }

    @Override
    public void setDefaultActionState() {
        Logger.preExecute(this, "setDefaultActionState");
        actionState = new ProfessorActions(this);
        Logger.postExecute();
    }
}
