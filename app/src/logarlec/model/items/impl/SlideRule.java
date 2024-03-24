package logarlec.model.items.impl;

import org.w3c.dom.events.Event;
import logarlec.model.actor.Actor;
import logarlec.model.items.Item;
import logarlec.model.items.ItemVisitor;
import logarlec.model.room.Room;

public class SlideRule extends Item {

    @Override
    public void onPickup(Actor actor) {
        // signal game over
        System.out.println("Logarlec has benn picked up 4!4!!!");
        return;
    }

    @Override
    public void accept(ItemVisitor visitor) {
        visitor.visit(this);
    }

}
