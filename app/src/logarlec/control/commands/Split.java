package logarlec.control.commands;

import java.util.ArrayList;
import java.util.Map.Entry;
import logarlec.control.Interpreter;
import logarlec.model.room.Door;
import logarlec.model.room.Room;

public class Split extends Command {

    @Override
    public boolean execute(String input) {
        String[] names = removeExtraSpace(input).split(" ");
        
        // Check if the input is valid
        if (names.length != 3 || isNameTaken(names[1]) || isNameTaken(names[2])) {
            return false;
        }

        Entry<Class<?>, Object> room1entry = findVariable(Room.class, names[0]);
        
        if (room1entry == null) {
            return false;
        }

        Room room = (Room) room1entry.getValue();

        // reference to the door list to get the newest door (the last one added)
        ArrayList<Door> doors = room.getDoors(); 
        Door lastDoor;

        if(doors.isEmpty()) {
            lastDoor = null;    
        } else {
            lastDoor = doors.get(doors.size() - 1);
        }

        // split the room
        room.split();

        // if the door was not added
        if(doors.isEmpty() || lastDoor == doors.get(doors.size() - 1)) { 
            return false;
        }

        // add the new door
        lastDoor = doors.get(doors.size() - 1);
        Interpreter.getInstance().AddVariable(names[2], lastDoor);
        
        // get the new room
        Room newRoom = lastDoor.leadsTo(room);
        Interpreter.getInstance().AddVariable(names[1], newRoom);

        return true;
    }
}
