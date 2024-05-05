package logarlec.model.items.impl;

import logarlec.model.room.GasEffect;
import logarlec.model.room.Room;
import logarlec.model.room.RoomEffect;
import logarlec.view.panels.ItemPanel;
import logarlec.model.actor.Actor;
import logarlec.model.items.Item;
import logarlec.model.items.ItemVisitor;



/**
 * Camembert is a type of item that can be used to create a gas effect in a
 * room.
 */
public class Camembert extends Item {

    public Camembert() { }

    public Camembert(Integer usesLeft) {
        super(usesLeft);
    }

    /**
     * Uses the camembert to create a gas effect in the room,
     * which will stun every actor in the room without gas mask.
     * 
     * @param invoker The actor that uses the camembert.
     */
    @Override
    public void use(Actor invoker) {

        Room room = invoker.getLocation();
        RoomEffect effect = new GasEffect(room);
        room.addEffect(effect);
        usesLeft--;

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
        return new ItemPanel(this, "camembert.png");
    }
}
