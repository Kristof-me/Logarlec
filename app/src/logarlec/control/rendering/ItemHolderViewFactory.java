package logarlec.control.rendering;

import logarlec.model.room.Room;

import java.util.HashMap;

import logarlec.model.actor.*;
import logarlec.model.items.Inventory;
import logarlec.view.panels.ActorPanel;
import logarlec.view.panels.InventoryPanel;
import logarlec.view.panels.PlayerPanel;
import logarlec.view.panels.RoomPanel;

/**
 * Item Holder View Factory
 */
public class ItemHolderViewFactory {
    private static RoomPanel autoPlaceTo = null;

    HashMap<Student, PlayerPanel> playerPanels = new HashMap<>();

    public static void setAutoPlacement(RoomPanel target) {
        autoPlaceTo = target;
    }

    public PlayerPanel createMenuPanel(Student student) {
        if(playerPanels.containsKey(student)) {
            return playerPanels.get(student);
        }

        PlayerPanel playerPanel = new PlayerPanel(student);
        student.addListener(playerPanel);
        playerPanels.put(student, playerPanel);

        return playerPanel;
    }

    public ActorPanel createPanel(Student student) {
       ActorPanel panel = new ActorPanel(student, "student.png");

        if(autoPlaceTo != null) {
            autoPlaceTo.AddStudentView(panel);
        }
            
        return panel;
    }

    public ActorPanel createPanel(Janitor janitor) {
        ActorPanel panel = new ActorPanel(janitor, "janitor.png");

        if(autoPlaceTo != null) {
            autoPlaceTo.AddJanitorView(panel);
        }
            
        return panel;
    }

    public ActorPanel createPanel(Professor professor) {
        ActorPanel panel = new ActorPanel(professor, "professor.png");

        if(autoPlaceTo != null) {
            autoPlaceTo.AddProfessorView(panel);
        }
            
        return panel;
    }

    public RoomPanel createPanel(Room room) {
        RoomPanel roomPanel = new RoomPanel(room);
        room.addListener(roomPanel);
        
        return roomPanel;
    }

    public InventoryPanel createPanel(Inventory inventory) {
        InventoryPanel panel = new InventoryPanel(inventory);
        inventory.addListener(panel);
        return panel;
    }
}