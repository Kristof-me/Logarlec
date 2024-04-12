package logarlec.control.commands;

import logarlec.model.actor.*;
import logarlec.model.items.*;

public class Drop extends ItemCommand {
    @Override
    boolean handleItem(Actor actor, Item item, String[] names) {
        // No need for validation, cause we can't drop an item that we don't have
        try {
            actor.drop(item);
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
}
