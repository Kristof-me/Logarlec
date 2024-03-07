package logarlec.model.items.impl;

import logarlec.model.characters.Actor;
import logarlec.model.enums.Event;
import logarlec.model.items.IItem;

public class GasMask implements IItem {
    private int usesLeft = 3;

    public boolean use(Actor invoker, Event event) {
        if (event == Event.GAS) {
            usesLeft--;

            return true;
        }
        return false;
    }

    public boolean use(IItem invoker, Event event) {
        return false;
    }

    @Override
    public int getUsesLeft() {
        return usesLeft;
    }

}
