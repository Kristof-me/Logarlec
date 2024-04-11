package logarlec.model.items.impl;

import logarlec.model.actor.Actor;
import logarlec.model.items.Item;
import logarlec.model.items.ItemVisitor;
import logarlec.model.actor.strategy.BeerDefense;



/**
 * The beer item defends the player from attacks from a certain amount of turns.
 */
public class Beer extends Item {

    public Beer() {
    }

    /**
     * Uses the beer item. It switches the defense strategy for a BeerDefense
     * strategy.
     * 
     * @param invoker The actor that uses the item.
     */
    @Override
    public void use(Actor invoker) {

        BeerDefense beerDefense = new BeerDefense(invoker);
        invoker.setDefenseStrategy(beerDefense);
        usesLeft--;

        super.use(invoker);
        
    }

    /**
     * Accepts a visitor.
     * 
     * @param visitor The visitor to accept.
     */
    @Override
    public void accept(ItemVisitor visitor) {
        visitor.visit(this);
        
    }
}
