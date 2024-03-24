package logarlec.model.items.impl;

import logarlec.model.actor.Actor;
import logarlec.model.items.Item;
import logarlec.model.items.ItemVisitor;
import logarlec.model.room.Room;

import logarlec.model.logger.*;

public class Cocktail extends Item {

    @Uses(fields = {"usesLeft"})
    @Override
    public void use(Actor invoker) {
        Logger.preExecute(this, "use", invoker);
        Room loc = invoker.getLocation();
        loc.revive();
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
