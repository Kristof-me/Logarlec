package logarlec.view.panels;

import java.awt.Color;

import logarlec.model.actor.actions.StunnedStep;
import logarlec.view.utility.IconLoader;

public class StunnedStrategyPanel extends EffectPanel {

    protected StunnedStep stunnedStep;
    public StunnedStrategyPanel(StunnedStep defense, String description, String icon) {
        super(Color.red, description, IconLoader.getInstance().getIcon(icon, 30));
        this.stunnedStep = defense;
        this.setTurnsLeft(stunnedStep.getRemaining());
    }
    @Override
    public void updateView() {
        int turnsLeft = stunnedStep.getRemaining();
        if (turnsLeft == 0) {
            EffectListPanel.getInstance().removeEffect(this);
        }
        else {
            setTurnsLeft(turnsLeft);
        }
    }
}
