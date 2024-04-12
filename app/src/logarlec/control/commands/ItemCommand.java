package logarlec.control.commands;

import java.util.Map.Entry;
import logarlec.model.actor.*;
import logarlec.model.items.*;

public abstract class ItemCommand extends Command {
    protected String actorName, itemName;
    Class<?>[] itemUsers = { Student.class, Professor.class };

    /**
     * execute the command
     * @param actor The actor that tries to handle the item.
     * @param item The item that should be handled.
     * @param names The names of the actor and the item. (actorName, itemName)
     * @return True if the command was executed successfully, false otherwise.
     */
    abstract boolean handleItem(Actor actor, Item item, String[] names);

    @Override
    public boolean execute(String input) {
        String[] data = removeExtraSpace(input).split(" ");

        if(data.length != 2) {
            return false;
        }

        Entry<Class<?>, Object> actorEntry = findVariableMatching(itemUsers, data[0]);
        Entry<Class<?>, Object> itemEntry = findVariable(Item.class, data[1]);

        if(actorEntry == null || itemEntry == null) {
            return false;
        }

        Actor actor = (Actor) actorEntry.getValue();
        Item item = (Item) itemEntry.getValue();

        return handleItem(actor, item, data);
    }
}
