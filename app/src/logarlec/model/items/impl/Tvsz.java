package logarlec.model.items.impl;

import logarlec.model.actor.Actor;
import logarlec.model.items.Item;
import logarlec.model.items.ItemVisitor;

import logarlec.model.logger.*;

/**
 * A passive item (automatically used when attacked and no beer effect)<br>
 * that protects the player while there is a professor in the room. After it<br>
 * the item gets used once.
 */
public class Tvsz extends Item {

    public Tvsz() {
        Logger.preConstructor(this);
        Logger.postConstructor(this);
    }

    /**
     * Use the item
     * @param invoker the actor who's inventory the item is in.
     */
    @Uses(fields = {"usesLeft"})
    @Override
    public void use(Actor invoker) {
        usesLeft--;
        super.use(invoker);
    }

    /**
     * Accept a visitor
     * @param visitor the visitor
     */
    @Override
    public void accept(ItemVisitor visitor) {
        Logger.preExecute(this, "accept", visitor);
        visitor.visit(this);
        Logger.postExecute();
    }
}
