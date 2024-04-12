package logarlec.control.commands;

import logarlec.control.Interpreter;
import logarlec.model.actor.*;
import logarlec.model.items.*;

public class Use extends ItemCommand {
    @Override
    boolean handleItem(Actor actor, Item item, String[] names) {
        try {
            actor.use(item);
        } catch (Exception e) {
            return false;
        }
        
        if(item.getUsesLeft() <= 0) {
            Interpreter.getInstance().removeVariable(names[1]);
        }

        return true;
    }
}
