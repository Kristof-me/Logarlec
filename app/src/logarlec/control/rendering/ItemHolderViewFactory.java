package logarlec.control.rendering;

import logarlec.model.room.Room;

import java.util.HashMap;

import logarlec.model.actor.Janitor;
import logarlec.model.actor.Professor;
import logarlec.model.actor.Student;
import logarlec.view.panels.ActorPanel;
import logarlec.view.panels.PlayerPanel;
import logarlec.view.panels.RoomPanel;

/**
 * Item Holder View Factory
 */
public class ItemHolderViewFactory {
    // these are separated for optimization purposes
    private static HashMap<Student, PlayerPanel> studentViews = new HashMap<>(); 
    private static HashMap<Room, RoomPanel> roomViews = new HashMap<>(); 

    // todo  kristóf írta, -30 IMSc-s megoldás
    private static RoomPanel autoPlaceTo = null;

    public static void setAutoPlacement(RoomPanel target) {
        autoPlaceTo = target;
    }

    public PlayerPanel createMenuPanel(Student student) {
        if (studentViews.containsKey(student)) {
            return studentViews.get(student);
        }

        PlayerPanel playerPanel = new PlayerPanel(student);
        student.addListener(playerPanel);
        studentViews.put(student, playerPanel);
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
        if (roomViews.containsKey(room)) {
            return roomViews.get(room);
        }

        RoomPanel roomPanel = new RoomPanel(room);
        room.addListener(roomPanel);
        roomViews.put(room, roomPanel);
        
        return roomPanel;
    }
}