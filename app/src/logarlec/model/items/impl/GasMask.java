package logarlec.model.items.impl;

import logarlec.model.enums.Event;
import logarlec.model.items.Item;

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
