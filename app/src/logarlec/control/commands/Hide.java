package logarlec.control.commands;

import java.util.Map.Entry;
import logarlec.model.room.Door;

public class Hide extends Command {

    @Override
    public boolean execute(String input) {
        String[] data = removeExtraSpace(input).split(" ", 2);

        if (data.length == 0) {
            return false;
        }

        Entry<Class<?>, Object> doorEntry = findVariable(Door.class, data[0]);
        
        if(doorEntry == null) {
            return false;
        }

        Door door = (Door) doorEntry.getValue();
        Integer duration = 10;

        if(data.length > 1) {
            String option = data[1];

            if(option.contains("-t")) {
                option = option.replace("-t", "").trim();

                duration = tryParse(option);
                
                // missing or out of range error
                if(duration == null || duration < 1 || duration > 100) {
                    return false;
                }
            } else {
                return false;
            }
        }

        door.hide(duration);
        return true;
    }
    
}
