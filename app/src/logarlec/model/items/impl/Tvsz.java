package logarlec.model.items.impl;

import logarlec.model.actor.Actor;
import logarlec.model.items.Item;
import logarlec.model.items.ItemVisitor;

public class Tvsz extends Item {

    public Tvsz() {
        usesLeft = 3;
    }

    @Override
    public void use(Actor invoker) {
        usesLeft--;
    }

    @Override
    public void accept(ItemVisitor visitor) {
        visitor.visit(this);
    }
}
