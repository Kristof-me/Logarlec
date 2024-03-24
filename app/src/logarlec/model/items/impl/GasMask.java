package logarlec.model.items.impl;

import logarlec.model.actor.Actor;
import logarlec.model.items.Item;
import logarlec.model.items.ItemVisitor;

public class GasMask extends Item {

    public GasMask() {
        usesLeft = 3; // this should be in ticks
    }

    @Override
    public void use(Actor invoker) {
        usesLeft--;
        super.use(invoker);
    }

    @Override
    public void accept(ItemVisitor visitor) {
        visitor.visit(this);
    }

}
