package logarlec.control.rendering;

import logarlec.model.room.Door;
import logarlec.view.panels.DoorPanel;

/**
 * DoorViewFactory
 */
public class DoorViewFactory {
    
    public DoorPanel createDoorView(Door door) {
        DoorPanel panel = new DoorPanel(door);
        door.addListener(panel);
        return panel;
    }
}