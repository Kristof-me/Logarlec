package logarlec.control.commands;

import java.util.Map.Entry;

import logarlec.model.actor.*;
import logarlec.model.room.Room;

public class Tick extends Command {
    Class<?>[] targetTypes = { Room.class, Student.class, Professor.class };

    public boolean execute(String input) {
        input = input.trim();
        
        Entry<Class<?>, Object> target = findVariableMatching(targetTypes, input);
        
        if(target == null) {
            return false;
        }

        try {
            invoke(target, "tick");
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
}
