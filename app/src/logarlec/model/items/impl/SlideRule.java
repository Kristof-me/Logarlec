package logarlec.model.items.impl;

import logarlec.model.characters.Actor;
import logarlec.model.enums.Event;
import logarlec.model.items.IItem;

public class SlideRule implements IItem {
    @Override
    public void onPickup(Actor newOwner) {
        // TODO end of the game
    }

    @Override
    public boolean use(Actor invoker, Event event) {
        return false;
    }

    @Override
    public boolean use(IItem invoker, Event event) {
        return false;
    }
}
