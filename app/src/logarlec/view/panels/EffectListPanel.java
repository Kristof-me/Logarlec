package logarlec.view.panels;

import javax.swing.*;

import logarlec.model.actor.Student;
import logarlec.model.room.Room;
import logarlec.view.observerviews.View;

public class EffectListPanel extends View {
    private Student student;
    private Room room;
    public EffectListPanel(Student student, Room room) {
        this.student = student;
        this.room = room;
        student.addListener(this);
        room.addListener(this);
        //create a new panel that will contain multiple effectpanels, and has a scrollpane if there are too many effects
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));   
    }

    public void addEffect(EffectPanel effectPanel){
        this.add(effectPanel);
        this.add(Box.createVerticalStrut(10)); // Add a 10 pixel gap
        redraw();
    }
    
    public void removeEffect(EffectPanel effectPanel){
        this.remove(effectPanel);
        redraw();
    }

    public void reset(){
        this.removeAll();
        redraw();
    }

    protected void redraw() {
        revalidate();
        repaint();
    }

    @Override
    public void updateView() {
        reset();
        B
    }
}
