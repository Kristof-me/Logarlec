package logarlec.model.actor;

import java.util.List;

import logarlec.model.room.RoomEffect;
import logarlec.view.observerviews.View;
import logarlec.model.actor.actions.JanitorActions;
import logarlec.model.items.Item;
import logarlec.model.items.ItemFinder;

/**
 * The janitor actor in the game. The janitor can clean up rooms (removes effects) and removes other actors from them.
 * The janitor does not die when attacked.
 */
public class Janitor extends Actor {
    /**
     * Creates a new janitor with the JanitorActions action state
     */
    public Janitor() {
        super();
        this.actionState = new JanitorActions(this);
    }


    /**
     * The janitor does not get affected by room effects, but might certain ones up
     */
    @Override
    public void acceptEffect(RoomEffect effect, List<ItemFinder<? extends Item>> unless) {
        effect.applyEffect(this);
    }

    /**
     * Sets the default action state of the actor in case another one expires.
     * The janitor has the JanitorActions action state by default.
     */
    @Override
    public void setDefaultActionState() {
        actionState = new JanitorActions(this);
    }


    @Override
    public View createOwnView() {
        return null;
    }
}
