package logarlec.control.rendering;

import logarlec.model.room.Room;

import java.awt.Component;
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
    private HashMap<Student, PlayerPanel> studentViews = new HashMap<>(); 
    private HashMap<Room, RoomPanel> roomViews = new HashMap<>(); 

    
    private Component actorPanelTarget;

    public void setPlayerPanelTarget(Component target) {
        actorPanelTarget = target;
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
       // ActorPanel panel = new ActorPanel(student, actorPanelTarget);

        if(actorPanelTarget != null)
            
        return new ActorPanel(student);
    }

    public ActorPanel createPanel(Janitor janitor) {
        return new ActorPanel(janitor);
    }

    public ActorPanel createPanel(Professor professor) {
        return new ActorPanel(professor);
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