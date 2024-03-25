package logarlec.model.items.impl;

import logarlec.model.actor.Actor;
import logarlec.model.items.Item;
import logarlec.model.items.ItemVisitor;
import logarlec.model.actor.strategy.BeerDefense;

import logarlec.model.logger.*;

/**
 * The beer item defends the player from attacks from a certain amount of turns.
 */
public class Beer extends Item {

    public Beer() {
        Logger.preConstructor(this);
        Logger.postConstructor(this);
    }

    /**
     * Uses the beer item. It switches the defense strategy for a BeerDefense strategy.
     * 
     * @param invoker The actor that uses the item.
     */
    @Uses(fields = {"usesLeft"})
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
        Logger.preExecute(this, "accept", visitor);
        visitor.visit(this);
        Logger.postExecute();
    }
}
