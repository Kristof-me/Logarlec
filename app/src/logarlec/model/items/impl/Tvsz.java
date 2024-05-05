package logarlec.model.items.impl;

import logarlec.model.actor.Actor;
import logarlec.model.items.Item;
import logarlec.model.items.ItemVisitor;
import logarlec.control.GameManager;
import logarlec.model.room.Room;
import logarlec.view.panels.ItemPanel;



/**
 * A passive item (automatically used when attacked and no beer effect)<br>
 * that protects the player while there is a professor in the room. After it<br>
 * the item gets used once.
 */
public class Tvsz extends Item {
    /**
     * Set the default usesLeft to 3
     */
    public Tvsz(){
        super(3);
    }

    public Tvsz(Integer usesLeft){
        super(usesLeft);
    }

    /**
     * Use the item
     * 
     * @param invoker the actor who's inventory the item is in.
     */
    @Override
    public void use(Actor invoker) {
        usesLeft--;
        super.use(invoker);
    }

    /**
     * Accept a visitor
     * 
     * @param visitor the visitor
     */
    @Override
    public void accept(ItemVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public ItemPanel createOwnView() {
        return new ItemPanel(this, "tvsz.png");
    }
}
