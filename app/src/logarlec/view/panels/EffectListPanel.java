package logarlec.view.panels;

import javax.swing.*;

import logarlec.model.actor.Student;
import logarlec.model.room.Room;
import logarlec.model.room.RoomEffect;
import logarlec.view.observerviews.View;

public class EffectListPanel extends View {
    private Student student;
    private Room room;

    public EffectListPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    protected void redraw() {
        revalidate();
        repaint();
    }

    public void bindStudent(Student student) {
        this.student = student;
        this.room = student.getLocation();

        student.addListener(this);
        room.addListener(this);
        updateView();
    }

    public void addEffect(EffectPanel effectPanel) {
        this.add(effectPanel);
        this.add(Box.createVerticalStrut(10));
        redraw();
    }

    public void removeEffect(EffectPanel effectPanel) {
        this.remove(effectPanel);
        redraw();
    }

    public void reset() {
        this.removeAll();
        redraw();
    }

    @Override
    public void updateView() {
        reset();
        EffectPanel defensePanel = student.getDefense().createOwnView();
        if (defensePanel != null) {
            addEffect(defensePanel);
        }

        EffectPanel actionPanel = student.getState().createOwnView();
        if (actionPanel != null) {
            addEffect(actionPanel);
        }

        for (RoomEffect effect : room.getRoomEffects()) {
            addEffect(effect.createOwnView());
        }

        redraw();
    }

    @Override
    public View removeView() {
        student.removeListener(this);
        room.removeListener(this);
        return this;
    }
}
