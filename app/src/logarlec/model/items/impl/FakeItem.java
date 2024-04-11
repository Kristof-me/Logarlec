package logarlec.model.items.impl;

import logarlec.model.actor.Actor;
import logarlec.model.items.Item;
import logarlec.model.items.ItemVisitor;

/**
 * Camembert is a type of item that can be used to create a gas effect in a
 * room.
 */
public class FakeItem extends Item {

    private Item itemToFake;

    public FakeItem(Item item) {
        // the fake item requires another item to fake
        itemToFake = item; 
    }

    @Override
    public void use(Actor invoker) {

        // no implementation

        super.use(invoker); // ? is this supposed to be here?
        
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
