package logarlec.model.items.impl;

import logarlec.model.room.Room;
import logarlec.model.room.RoomEffect;
import logarlec.model.room.WetEffect;
import logarlec.view.panels.ItemPanel;
import logarlec.model.actor.Actor;
import logarlec.model.items.Item;
import logarlec.model.items.ItemVisitor;



/**
 * A sponge is an item that can be used to make a room wet,
 * which then stuns the professors.
 */
public class Sponge extends Item {

    public Sponge() { }

    public Sponge(Integer usesLeft) {
        super(usesLeft);
    }

    /**
     * Uses the sponge to make the room wet.
     * 
     * @param invoker The actor that uses the sponge.
     */
    @Override
    public void use(Actor invoker) {

        Room room = invoker.getLocation();
        RoomEffect effect = new WetEffect(room);
        room.addEffect(effect);

        usesLeft--;
        super.use(invoker);
        
    }

    /**
     * Accepts a visitor to visit the sponge.
     * 
     * @param visitor The visitor
     */
    @Override
    public void accept(ItemVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public ItemPanel createOwnView() {
        ItemPanel itemPanel = new ItemPanel(this, "sponge.png");
        addListener(itemPanel);
        return itemPanel;
    }
}
