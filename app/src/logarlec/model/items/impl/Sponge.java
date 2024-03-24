package logarlec.model.items.impl;

import logarlec.model.actor.Actor;
import logarlec.model.items.Item;
import logarlec.model.items.ItemVisitor;
import logarlec.model.room.Room;
import logarlec.model.room.RoomEffect;
import logarlec.model.room.WetEffect;

import logarlec.model.logger.*;

public class Sponge extends Item {

    public Sponge() {
        Logger.preConstructor(this);
        Logger.postConstructor(this);
    }

    @Uses(fields = {"usesLeft"})
    @Override
    public void use(Actor invoker) {
        Logger.preExecute(this, "use", invoker);
        Room loc = invoker.getLocation();
        RoomEffect effect = new WetEffect();
        loc.addEffect(effect);
        usesLeft--;
        super.use(invoker);
        Logger.postExecute();
    }

    @Override
    public void accept(ItemVisitor visitor) {
        Logger.preExecute(this, "accept", visitor);
        visitor.visit(this);
        Logger.postExecute();
    }
}
