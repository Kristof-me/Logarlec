package logarlec.model.items.impl;

import logarlec.model.actor.Actor;
import logarlec.model.items.Item;
import logarlec.model.items.ItemVisitor;
import logarlec.model.room.Room;
import logarlec.model.room.RoomEffect;
import logarlec.model.room.WetEffect;

public class Sponge extends Item {

    public Sponge() {
        usesLeft = 1;
    }

    @Override
    public void use(Actor invoker) {
        Room loc = invoker.getLocation();
        RoomEffect effect = new WetEffect();
        loc.addEffect(effect);
        usesLeft--;
    }

    @Override
    public void accept(ItemVisitor visitor) {
        visitor.visit(this);
    }
}
