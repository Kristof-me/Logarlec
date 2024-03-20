package logarlec.model.items.impl;

import org.w3c.dom.events.Event;
import logarlec.model.actor.Actor;
import logarlec.model.items.Item;
import logarlec.model.items.ItemVisitor;
import logarlec.model.room.Room;
import logarlec.model.room.RoomEffect;

public class Camembert extends Item {

    @Override
    public void use(Actor invoker) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'use'");
    }

    @Override
    public int getUsesLeft() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUsesLeft'");
    }

    @Override
    public void onPickup(Actor actor) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onPickup'");
    }

    @Override
    public void onDrop(Room room) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onDrop'");
    }

    @Override
    public void accept(ItemVisitor visitor) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'accept'");
    }

}
