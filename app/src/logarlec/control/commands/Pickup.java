package logarlec.control.commands;

import logarlec.model.actor.*;
import logarlec.model.items.*;

public class Pickup extends ItemCommand {
    @Override
    boolean handleItem(Actor actor, Item item, String[] names) {
        try {
            actor.pickUp(item);
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
