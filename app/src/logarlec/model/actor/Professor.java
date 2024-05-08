package logarlec.model.actor;

import java.util.List;

import logarlec.model.room.RoomEffect;
import logarlec.view.panels.ActorPanel;
import logarlec.control.rendering.ItemHolderViewFactory;
import logarlec.model.actor.actions.ProfessorActions;
import logarlec.model.items.Item;
import logarlec.model.items.ItemFinder;

/**
 * The professor actor in the game. The professor can attack other actors in the same room, but does not die when attacked by another professor.
 */
public class Professor extends Actor {
    /**
     * Creates a new professor with the ProfessorActions action state
     */
    public Professor() {
        super();
        this.actionState = new ProfessorActions(this);
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
     * The professor has the ProfessorActions action state by default.
     */
    @Override
    public void setDefaultActionState() {
        actionState = new ProfessorActions(this);
    }


    @Override
    public ActorPanel createOwnView() {
        return new ItemHolderViewFactory().createPanel(this);
    }
}
