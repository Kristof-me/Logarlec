package logarlec.model.actor;

import java.util.List;
import logarlec.model.actor.actions.JanitorActions;
import logarlec.model.items.Item;
import logarlec.model.items.ItemFinder;

import logarlec.model.room.RoomEffect;

public class Janitor extends Actor {
    public Janitor() {
        super();
        this.actionState = new JanitorActions(this);
    }

    @Override
    public void attacked() {
        // cannot be attacked
    }

    @Override
    public boolean revive() {
        return false;
    }

    @Override
    public void acceptEffect(RoomEffect effect, List<ItemFinder<? extends Item>> unless) {

        // Janitor does not care about effects

        // Todo: remove gas effect on this apply?

    }

    @Override
    public void setDefaultActionState() {
        actionState = new JanitorActions(this);
    }
}
