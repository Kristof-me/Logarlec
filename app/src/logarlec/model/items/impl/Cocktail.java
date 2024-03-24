package logarlec.model.items.impl;

import logarlec.model.actor.Actor;
import logarlec.model.items.Item;
import logarlec.model.items.ItemVisitor;
import logarlec.model.room.Room;

public class Cocktail extends Item {

    @Override
    public void use(Actor invoker) {
        Room loc = invoker.getLocation();
        loc.revive();
    }

    @Override
    public void accept(ItemVisitor visitor) {
        visitor.visit(this);
    }

}
