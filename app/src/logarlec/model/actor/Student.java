package logarlec.model.actor;

import java.util.List;
import logarlec.model.actor.actions.StudentActions;
import logarlec.model.items.Item;
import logarlec.model.items.ItemFinder;

import logarlec.model.room.RoomEffect;

/**
 * The student actor in the game. The student is controlled by the player can die when attacked, and may be revived.
 */
public class Student extends Actor {
    /**
     * Creates a new student with the StudentActions action state.
     */
    public Student() {
        super();
        this.actionState = new StudentActions(this);
    }

    /**
     * Called when the student is attacked, the student dies if the current defense strategy fails.
     */
    @Override
    public void attacked() {
        alive = defenseStrategy.defend(inventory);
    }

    /**
     * Revives the student, but only if they are dead.
     */
    @Override
    public boolean revive() {
        if (alive)  return false;
        alive = true;
        return true;
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

    /**
     * Sets the default action state of the actor in case another one expires.
     * The student has the StudentActions action state by default.
     */
    @Override
    public void setDefaultActionState() {
        actionState = new StudentActions(this);
    }
}
