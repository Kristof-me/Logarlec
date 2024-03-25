package logarlec.model.items.impl;

import logarlec.model.actor.Actor;
import logarlec.model.items.Item;
import logarlec.model.items.ItemVisitor;

import logarlec.model.logger.*;

/**
 * A slide rule item, which needs to be picked up to win the game
 */
public class SlideRule extends Item {

    public SlideRule() {
        Logger.preConstructor(this);
        Logger.postConstructor(this);
    }

    /**
     * Called when the slide rule is picked up,
     * triggers the end of the game
     */
    @Override
    public void onPickup(Actor actor) {
        Logger.preExecute(this, "onPickup", actor);
        // signal game over
        System.out.println("Logarlec has been picked up 4!4!!!");
        super.onPickup(actor);
        Logger.postExecute();
    }

    /**
     * Accepts a visitor
     * 
     * @param visitor the visitor
     */
    @Override
    public void accept(ItemVisitor visitor) {
        Logger.preExecute(this, "accept", visitor);
        visitor.visit(this);
        Logger.postExecute();
    }

}
