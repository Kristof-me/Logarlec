package logarlec.view.panels;

import logarlec.model.room.RoomEffect;
import logarlec.view.frames.GameFrame;
import logarlec.view.observerviews.View;
import logarlec.view.utility.IconLoader;
import logarlec.view.utility.ThemeManager;

/**
 * A panel displaying a room effect
 
 */
public class RoomEffectPanel extends EffectPanel {
    /**
     * The RoomEffect being displayed
     */
    protected RoomEffect effect;
    /**
     * Creates a new room effect panel
     * @param defense The room effect to display
     * @param description The description of the room effect
     * @param icon The icon to display next to the room effect
     */
    public RoomEffectPanel(RoomEffect defense, String description, String icon) {
        super(ThemeManager.PRIMARY, description, IconLoader.getInstance().getIcon(icon, 30));
        this.effect = defense;
        this.setTurnsLeft(effect.getTimeLeft());
    }
    /**
     * Updates the view to reflect the current state of the room effect, setting the remaining turns
     */
    @Override
    public void updateView() {
        int turnsLeft = effect.getTimeLeft();

        if (turnsLeft == 0) {
            EffectListPanel panel = GameFrame.getInstance().getEffectListPanel();
            panel.removeEffect(this);
        } else {
            setTurnsLeft(effect.getTimeLeft());
        }

    }

    @Override
    public View removeView() {
        effect.removeListener(this);
        return this;
    }

}
