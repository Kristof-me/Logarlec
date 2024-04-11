package logarlec.control.commands;

import java.util.Map.Entry;

import logarlec.model.actor.*;
import logarlec.model.room.Room;

public class Tick extends Command {
    Class<?>[] targetTypes = { Room.class, Student.class, Professor.class };

    public boolean execute(String input) {
        input = input.trim();
        
        Entry<Class<?>, Object> target = matchedTypes(targetTypes, input);
        
        if(target == null) {
            return false;
        }
        
        // TODO test this

        try {
            target.getKey().getMethod("tick").invoke(target);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
}
