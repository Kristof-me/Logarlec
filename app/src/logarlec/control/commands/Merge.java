package logarlec.control.commands;

import java.util.Map.Entry;

import logarlec.control.Interpreter;
import logarlec.model.room.Room;

public class Merge extends Command {

    @Override
    public boolean execute(String input) {
        String[] roomNames = removeExtraSpace(input).split(" ");
        
        if (roomNames.length != 2) {
            return false;
        }

        Entry<Class<?>, Object> room1entry = findVariable(Room.class, roomNames[0]);
        Entry<Class<?>, Object> room2entry = findVariable(Room.class, roomNames[1]);
        
        if (room1entry == null || room2entry == null) {
            return false;
        }
        
        if (roomNames[0].equals(roomNames[1])) {
            // merging with the same room is always successful
            return true;
        }

        Room room1 = (Room) room1entry.getValue();
        Room room2 = (Room) room2entry.getValue();

        room2.merge(room1);
        Interpreter.getInstance().removeVariable(roomNames[1]);

        return true;
    }
}
