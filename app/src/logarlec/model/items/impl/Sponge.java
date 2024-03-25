package logarlec.model.items.impl;

import logarlec.model.actor.Actor;
import logarlec.model.items.Item;
import logarlec.model.items.ItemVisitor;
import logarlec.model.room.Room;
import logarlec.model.room.RoomEffect;
import logarlec.model.room.WetEffect;

import logarlec.model.logger.*;

/**
 * A sponge is an item that can be used to make a room wet,
 * which then stuns the professors.
 */
public class Sponge extends Item {

    public Sponge() {
        Logger.preConstructor(this);
        Logger.postConstructor(this);
    }

    /**
     * Uses the sponge to make the room wet.
     * @param invoker The actor that uses the sponge.
     */
    @Uses(fields = {"usesLeft"})
    @Override
    public void use(Actor invoker) {
        Room room = invoker.getLocation();
        RoomEffect effect = new WetEffect();
        room.addEffect(effect);

        usesLeft--;
        super.use(invoker);
    }

    /**
     * Accepts a visitor to visit the sponge.
     * @param visitor The visitor
     */
    @Override
    public void accept(ItemVisitor visitor) {
        Logger.preExecute(this, "accept", visitor);
        visitor.visit(this);
        Logger.postExecute();
    }
}
