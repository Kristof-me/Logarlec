package logarlec.control.commands;

import java.util.Map.Entry;
import logarlec.model.actor.*;
import logarlec.model.room.Door;

public class Move extends Command {
    @Override
    public boolean execute(String input) {
        String[] data = removeExtraSpace(input).split(" ");

        if(data.length != 2) {
            return false;
        }

        Entry<Class<?>, Object> actorEntry = findVariable(Actor.class, data[0]);
        Entry<Class<?>, Object> doorEntry = findVariable(Door.class, data[1]);

        if(actorEntry == null || doorEntry == null) {
            return false;
        }

        Actor actor = (Actor) actorEntry.getValue();
        Door door = (Door) doorEntry.getValue();

        try {
            actor.move(door);
        } catch (Exception e) {
            return false;
        }

        return true;        
    }
}
