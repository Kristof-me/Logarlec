package logarlec.model.items.impl;

import logarlec.model.actor.Actor;
import logarlec.model.items.Item;
import logarlec.model.items.ItemVisitor;
import logarlec.model.actor.strategy.BeerDefense;

import logarlec.model.logger.*;

/**
 * The beer item defends the player from attacks from a certain amount of turns.
 */
public class AirFreshener extends Item {

    public AirFreshener() {
        Logger.preConstructor(this);
        Logger.postConstructor(this);
    }

    /**
     * Uses the beer item. It switches the defense strategy for a BeerDefense
     * strategy.
     * 
     * @param invoker The actor that uses the item.
     */
    @Uses(fields = { "usesLeft" })
    @Override
    public void use(Actor invoker) {
        Logger.preExecute(this, "use", invoker);

        if(invoker.getLocation().clean()){
            usesLeft--;
        }

        super.use(invoker);
        Logger.postExecute();
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
