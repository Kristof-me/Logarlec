package logarlec.control.commands;

import java.util.ArrayList;
import java.util.Map.Entry;
import logarlec.control.Interpreter;
import logarlec.model.room.Door;
import logarlec.model.room.Room;

public class Split extends Command {

    @Override
    public boolean execute(String input) {
        String[] roomNames = removeExtraSpace(input).trim().split(" ");
        
        // Check if the input is valid
        if (roomNames.length != 2 || isNameTaken(roomNames[1])) {
            return false;
        }

        Entry<Class<?>, Object> room1entry = findVariable(Room.class, roomNames[0]);
        
        if (room1entry == null) {
            return false;
        }

        Room room = (Room) room1entry.getValue();

        // reference to the door list and get the last door
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

        // get the new room
        lastDoor = doors.get(doors.size() - 1);
        Room newRoom = lastDoor.leadsTo(room);

        // TODO give name to the newly created door
        // Interpreter.getInstance().AddVariable("TODO", lastDoor);

        Interpreter.getInstance().AddVariable(roomNames[1], newRoom);
        return true;
    }
}
