package logarlec.model.items.impl;

import org.w3c.dom.events.Event;
import logarlec.model.actor.Actor;
import logarlec.model.items.Item;
import logarlec.model.items.ItemVisitor;
import logarlec.model.room.GasEffect;
import logarlec.model.room.Room;
import logarlec.model.room.RoomEffect;

public class Camembert extends Item {

    public Camembert() {
        usesLeft = 1;
    }

    @Override
    public void use(Actor invoker) {
        Room loc = invoker.getLocation();
        RoomEffect effect = new GasEffect();
        loc.addEffect(effect);
        usesLeft--;
    }

    @Override
    public void accept(ItemVisitor visitor) {
        visitor.visit(this);
    }

}
