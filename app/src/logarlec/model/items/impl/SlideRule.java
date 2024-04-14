package logarlec.model.items.impl;

import logarlec.control.GameManager;
import logarlec.model.actor.Actor;
import logarlec.model.items.Item;
import logarlec.model.items.ItemVisitor;



/**
 * A slide rule item, which needs to be picked up to win the game
 */
public class SlideRule extends Item {

    public static boolean anyPickedUp = false;

    /**
     * Called when the slide rule is picked up,
     * triggers the end of the game
     */
    @Override
    public void onPickup(Actor actor) {
        GameManager.getInstance().slideRulePickedUp();
        super.onPickup(actor);
        
    }

    /**
     * Accepts a visitor
     * 
     * @param visitor the visitor
     */
    @Override
    public void accept(ItemVisitor visitor) {
        visitor.visit(this);
        
    }

}
