package logarlec.model.actor;

import java.util.List;
import logarlec.model.actor.actions.JanitorActions;
import logarlec.model.items.Item;
import logarlec.model.items.ItemFinder;
import logarlec.model.logger.Logger;
import logarlec.model.room.RoomEffect;

public class Janitor extends Actor {
    public Janitor() {
        super();
        this.actionState = new JanitorActions(this);
        Logger.postConstructor(this);
    }

    @Override
    public void attacked() {
        Logger.preExecute(this, "attacked");
        // cannot be attacked
        Logger.postExecute();
    }

    @Override
    public boolean revive() {
        Logger.preExecute(this, "revive");
        return Logger.postExecute(false);
    }

    @Override
    public void acceptEffect(RoomEffect effect, List<ItemFinder<? extends Item>> unless) {
        Logger.preExecute(this, "acceptEffect", effect, unless);

        // Janitor does not care about effects

        // Todo: remove gas effect on this apply?

        Logger.postExecute();
    }

    @Override
    public void setDefaultActionState() {
        Logger.preExecute(this, "setDefaultActionState");
        actionState = new JanitorActions(this);
        Logger.postExecute();
    }
}
