package logarlec.model.actor;

import java.util.List;
import logarlec.model.actor.actions.StudentActions;
import logarlec.model.items.Item;
import logarlec.model.items.ItemFinder;

import logarlec.model.room.RoomEffect;

public class Student extends Actor {
    public Student() {
        super();
        this.actionState = new StudentActions(this);
        
    }

    @Override
    public void attacked() {
        alive = defenseStrategy.defend(inventory);
        
    }

    @Override
    public boolean revive() {
        if (alive) {
        }

        alive = true;
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
        actionState = new StudentActions(this);
        
    }
}
