package logarlec.view.panels;

import logarlec.model.actor.strategy.DefenseStrategy;
import logarlec.view.frames.GameFrame;
import logarlec.view.observerviews.View;
import logarlec.view.utility.IconLoader;
import logarlec.view.utility.ThemeManager;

/**
 * A panel displaying a defense strategy
 */
public class DefenseStrategyPanel extends EffectPanel {
    /**
     * The defense strategy being displayed
     */
    protected DefenseStrategy defenseStrategy;
    /**
     * Creates a new defense strategy panel
     * @param defense The defense strategy to display
     * @param description The description of the defense strategy
     * @param icon The icon to display next to the defense strategy
     */
    public DefenseStrategyPanel(DefenseStrategy defense, String description, String icon) {
        super(ThemeManager.ACCENT, description, IconLoader.getInstance().getIcon(icon, 30));
        this.defenseStrategy = defense;
        this.setTurnsLeft(defenseStrategy.getRemaining());
    }
    /**
     * Updates the view to reflect the current state of the defense strategy
     * If the defense strategy has expired, this panel is removed from the effect list, otherwise sets the remaining turns
     */
    @Override
    public void updateView() {
        int turnsLeft = defenseStrategy.getRemaining();

        if (turnsLeft == 0) {
            EffectListPanel panel = GameFrame.getInstance().getEffectListPanel();
            panel.removeEffect(this);
        } else {
            setTurnsLeft(turnsLeft);
        }
    }

    @Override
    public View removeView() {
        defenseStrategy.removeListener(this);
        return this;
    }
}
