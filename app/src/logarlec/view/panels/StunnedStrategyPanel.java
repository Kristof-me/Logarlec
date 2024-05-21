package logarlec.view.panels;

import logarlec.model.actor.actions.StunnedStep;
import logarlec.view.frames.GameFrame;
import logarlec.view.observerviews.View;
import logarlec.view.utility.IconLoader;
import logarlec.view.utility.ThemeManager;

/**
 * A panel displaying a stunned strategy
 */
public class StunnedStrategyPanel extends EffectPanel {
    /**
     * The stunned effect being displayed
     */
    protected StunnedStep stunnedStep;
    /**
     * Creates a new stunned strategy panel
     * @param stunnedStep The stunned effect to display
     * @param description The description of the stunned effect
     * @param icon The icon to display next to the stunned effect
     */
    public StunnedStrategyPanel(StunnedStep stunnedStep, String description, String icon) {
        super(ThemeManager.PRIMARY, description, IconLoader.getInstance().getIcon(icon, 30));
        this.stunnedStep = stunnedStep;
        this.setTurnsLeft(stunnedStep.getRemaining());
    }
    /**
     * Updates the view to reflect the current state of the stunned effect, setting the remaining turns.
     * If the stunned effect has expired, this panel is removed from the effect list
     */
    @Override
    public void updateView() {
        int turnsLeft = stunnedStep.getRemaining();

        if (turnsLeft == 0) {
            EffectListPanel panel = GameFrame.getInstance().getEffectListPanel();
            panel.removeEffect(this);
        } else {
            setTurnsLeft(turnsLeft);
        }
    }

    @Override
    public View removeView() {
        stunnedStep.removeListener(this);
        return this;
    }
}
