package logarlec.control.rendering;

import logarlec.model.room.Door;
import logarlec.view.panels.DoorPanel;

/**
 * DoorViewFactory
 */
public class DoorViewFactory {

    public DoorPanel createDoorView(Door door) {
        return new DoorPanel(door);
    }
}
