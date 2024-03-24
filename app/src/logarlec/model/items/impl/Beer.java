package logarlec.model.items.impl;

import logarlec.model.actor.Actor;
import logarlec.model.items.Item;
import logarlec.model.items.ItemVisitor;

import logarlec.model.logger.*;

public class Beer extends Item {

    public Beer() {
        Logger.preConstructor(this);
        Logger.postConstructor(this);
    }

    @Uses(fields = {"usesLeft"})
    @Override
    public void use(Actor invoker) {
        Logger.preExecute(this, "use", invoker);
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
