package logarlec.view.panels;

import javax.swing.*;

import logarlec.model.actor.Student;
import logarlec.model.room.Room;
import logarlec.model.room.RoomEffect;
import logarlec.view.observerviews.View;

/**
 * A panel displaying a list of effects
 */
public class EffectListPanel extends View {
    /**
     * The student whose effects (DefenseStrategy and ActionState) are being displayed
     */
    private Student student;
    /**
     * The room in which the student is located, whose effects are being displayed
     */
    private Room room;
    /**
     * Creates a new effect list panel
     */
    public EffectListPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }
    /**
     * Redraws the panel
     */
    protected void redraw() {
        revalidate();
        repaint();
    }
    /**
     * Binds a student to the panel, allowing us to display the student's effects
     * Also binds the room in which the student is located
     * @param student The student whose effects to display
     */
    public void bindStudent(Student student) {
        if (this.student != null) {
            this.student.removeListener(this);
            this.student.getLocation().removeListener(this);
        }
        this.student = student;
        this.room = student.getLocation();

        student.addListener(this);
        room.addListener(this);
        updateView();
    }
    /**
     * Adds an effect to the list
     * @param effectPanel The effect panel to add
     */
    public void addEffect(EffectPanel effectPanel) {
        this.add(effectPanel);
        this.add(Box.createVerticalStrut(10));
        redraw();
    }
    /**
     * Removes an effect from the list
     * @param effectPanel The effect panel to remove
     */
    public void removeEffect(EffectPanel effectPanel) {
        this.remove(effectPanel);
        redraw();
    }
    /**
     * Resets the panel, removing all effects 
     */
    public void reset() {
        this.removeAll();
        redraw();
    }
    /**
     * Updates the view to reflect the current state of the student and room
     * Displays the student's defense strategy and action state, as well as the room's effects
     * If the student has no defense strategy or action state, they are not displayed
     */
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

        for (int i = 0; i < room.getRoomEffects().size(); i++) {
            RoomEffect effect = room.getRoomEffects().get(i);
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
