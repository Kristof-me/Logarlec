package logarlec.model.items.impl;

import logarlec.model.characters.Actor;
import logarlec.model.enums.ActorEffect;
import logarlec.model.enums.Event;
import logarlec.model.items.IItem;

public class Beer implements IItem {
    public boolean use(Actor invoker, Event event) {
        invoker.addEffect(ActorEffect.BEER, 69);
        return true;
    }

    public boolean use(IItem invoker, Event event) {
        return false;
    }
}
