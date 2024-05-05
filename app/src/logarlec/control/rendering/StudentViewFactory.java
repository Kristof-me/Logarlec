package logarlec.control.rendering;

import logarlec.model.actor.Student;
import logarlec.view.panels.PlayerPanel;
/**
 * StudentViewFactory
 */
public class StudentViewFactory {
    private PlayerPanel playerPanel;
    
    public PlayerPanel createPanel(Student student){
        playerPanel = new PlayerPanel(student);
        student.addListener(playerPanel);
        return playerPanel;
    }
}