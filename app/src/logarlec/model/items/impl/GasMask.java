package logarlec.model.items.impl;

import logarlec.model.items.Item;
import logarlec.model.effects.Event;

public class GasMask extends Item {
    private int usesLeft = 3;

    @Override
    public Integer getUsesLeft(Event event) {
        if (event == Event.GAS) {
            return usesLeft;
        }

        return super.getUsesLeft(event);
    }

}
