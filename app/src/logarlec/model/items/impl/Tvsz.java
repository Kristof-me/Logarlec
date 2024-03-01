package logarlec.model.items.impl;

import logarlec.model.effects.Event;
import logarlec.model.items.Item;

public class Tvsz extends Item {
    private int usesLeft = 3;

    @Override
    public Integer getUsesLeft(Event event) {
        if (event == Event.ATTACK) {
            return usesLeft;
        }

        return super.getUsesLeft(event);
    }

    @Override
    public boolean use(Item invoker, Event event) {
        if (event == Event.CONTROLLER_ACTIVATED) {
            // ? lehet ez nem az az event
            usesLeft--;
            return true;
        }

        return super.use(invoker, event);
    }
}
