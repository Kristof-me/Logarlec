package logarlec.model.items.impl;

import logarlec.model.characters.Actor;
import logarlec.model.effects.Event;
import logarlec.model.items.Item;

public class Sponge extends Item {
    @Override
    public boolean use(Actor invoker, Event event) {

        return false;
    }
}
