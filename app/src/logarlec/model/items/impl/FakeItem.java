package logarlec.model.items.impl;

import logarlec.model.actor.Actor;
import logarlec.model.items.Item;
import logarlec.model.items.ItemVisitor;
import logarlec.model.room.GasEffect;
import logarlec.model.room.Room;
import logarlec.model.room.RoomEffect;



/**
 * Camembert is a type of item that can be used to create a gas effect in a
 * room.
 */
public class FakeItem extends Item {

    private Item itemToFake; // ToDo: set this

    public FakeItem() {
        
        
    }

    @Override
    public void use(Actor invoker) {

        // no implementation

        super.use(invoker);
        
    }

    /**
     * Accepts a visitor
     * 
     * @param visitor The visitor
     */
    @Override
    public void accept(ItemVisitor visitor) {
        visitor.visit(this);
        
    }
}
