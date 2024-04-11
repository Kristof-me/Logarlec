package logarlec.model.actor;

import java.util.List;
import logarlec.model.actor.actions.ProfessorActions;
import logarlec.model.items.Item;
import logarlec.model.items.ItemFinder;

import logarlec.model.room.RoomEffect;

public class Professor extends Actor {
    public Professor() {
        super();

        this.actionState = new ProfessorActions(this);
    }

    @Override
    public void attacked() {
    }

    @Override
    public boolean revive() {
        return false;
    }

    @Override
    public void acceptEffect(RoomEffect effect, List<ItemFinder<? extends Item>> unless) {

        if (unless != null) {
            for (ItemFinder<? extends Item> itemFinder : unless) {
                var protector = itemFinder.findIn(inventory);

                if (protector != null) {
                    actionState.use(protector);
                    return;
                }
            }
        }

        effect.applyEffect(this);
    }

    @Override
    public void setDefaultActionState() {
        actionState = new ProfessorActions(this);
    }
}
