package logarlec.model.items.impl;

import logarlec.model.actor.Actor;
import logarlec.model.items.Item;
import logarlec.model.items.ItemVisitor;
import logarlec.model.room.Room;

import logarlec.model.logger.*;

/**
 * A cocktail is an item that can be used to revive a student.
 */
public class Cocktail extends Item {

    public Cocktail(){
        Logger.preConstructor(this);
        Logger.postConstructor(this);
    }

    /**
     * Revives a student in the room if there is free space
     * @param invoker the actor that uses the item
     */
    @Uses(fields = {"usesLeft"})
    @Override
    public void use(Actor invoker) {
        Room room = invoker.getLocation();
        boolean success = room.revive();
        if(success) {
            usesLeft--;
        }
        super.use(invoker);
    }

    /**
     * Accepts a visitor
     * @param visitor the visitor
     */
    @Override
    public void accept(ItemVisitor visitor) {
        Logger.preExecute(this, "accept", visitor);
        visitor.visit(this);
        Logger.postExecute();
    }
}
