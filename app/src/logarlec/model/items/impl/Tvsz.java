package logarlec.model.items.impl;

import logarlec.model.characters.Actor;
import logarlec.model.enums.Event;
import logarlec.model.items.IItem;

public class Tvsz implements IItem {
    private int usesLeft = 3;

    @Override
    public int getUsesLeft() {
        return usesLeft;
    }

    public boolean use(IItem invoker, Event event) {
        return false;
    }

    public boolean use(Actor invoker, Event event) {
        if (event == Event.CONTROLLER_ACTIVATED) {
            // ? lehet ez nem az az event
            invoker.deleteItem(this);
            usesLeft--;
            return true;
        }

        return false;
    }
}
