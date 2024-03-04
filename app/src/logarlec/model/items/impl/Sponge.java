package logarlec.model.items.impl;

import logarlec.model.characters.Actor;
import logarlec.model.enums.Event;
import logarlec.model.enums.RoomEffect;
import logarlec.model.items.IItem;

public class Sponge implements IItem {
    public boolean use(Actor invoker, Event event) {
        if (event == Event.CONTROLLER_ACTIVATED) {
            invoker.getLocation().addEffect(RoomEffect.WET, 69);
        }
        return false;
    }

    public boolean use(IItem invoker, Event event) {
        return false;
    }
}
