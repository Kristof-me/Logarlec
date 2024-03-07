package logarlec.model.items.impl;

import logarlec.model.characters.Actor;
import logarlec.model.enums.Event;
import logarlec.model.items.IItem;

public class Cocktail implements IItem {
    public boolean use(Actor invoker, Event event) {
        invoker.getLocation().revive();
        return false;
    }

    public boolean use(IItem invoker, Event event) {
        return false;
    }
}
