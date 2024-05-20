package logarlec.view.panels;

import logarlec.model.actor.actions.StunnedStep;
import logarlec.view.frames.GameFrame;
import logarlec.view.observerviews.View;
import logarlec.view.utility.IconLoader;
import logarlec.view.utility.ThemeManager;

public class StunnedStrategyPanel extends EffectPanel {

    protected StunnedStep stunnedStep;

    public StunnedStrategyPanel(StunnedStep defense, String description, String icon) {
        super(ThemeManager.PRIMARY, description, IconLoader.getInstance().getIcon(icon, 30));
        this.stunnedStep = defense;
        this.setTurnsLeft(stunnedStep.getRemaining());
    }

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
