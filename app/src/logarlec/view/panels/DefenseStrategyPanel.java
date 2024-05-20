package logarlec.view.panels;

import logarlec.model.actor.strategy.DefenseStrategy;
import logarlec.view.frames.GameFrame;
import logarlec.view.observerviews.View;
import logarlec.view.utility.IconLoader;
import logarlec.view.utility.ThemeManager;

public class DefenseStrategyPanel extends EffectPanel {

    protected DefenseStrategy defenseStrategy;

    public DefenseStrategyPanel(DefenseStrategy defense, String description, String icon) {
        super(ThemeManager.ACCENT, description, IconLoader.getInstance().getIcon(icon, 30));
        this.defenseStrategy = defense;
        this.setTurnsLeft(defenseStrategy.getRemaining());
    }

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
