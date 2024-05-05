package logarlec.model.items.impl;

import logarlec.model.actor.Actor;
import logarlec.model.items.Item;
import logarlec.model.items.ItemVisitor;
import logarlec.view.panels.ItemPanel;

/**
 * Camembert is a type of item that can be used to create a gas effect in a
 * room.
 */
public class FakeItem extends Item {

    private Item itemToFake; // the fake item requires another item to fake

    public FakeItem(Item item) {
        itemToFake = item; 
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

    @Override
    public ItemPanel createOwnView() {
        ItemPanel original = itemToFake.createOwnView();
        return new ItemPanel(this, original.getIcon());
    }
}
