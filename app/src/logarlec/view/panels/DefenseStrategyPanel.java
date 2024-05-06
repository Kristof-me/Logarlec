package logarlec.view.panels;

import java.awt.Color;

import logarlec.model.actor.strategy.DefenseStrategy;
import logarlec.view.frames.GameFrame;
import logarlec.view.utility.IconLoader;

public class DefenseStrategyPanel extends EffectPanel {

    protected DefenseStrategy defenseStrategy;
    public DefenseStrategyPanel(DefenseStrategy defense, String description, String icon) {
        super(new Color(0, 200, 0), description, IconLoader.getInstance().getIcon(icon, 30));
        this.defenseStrategy = defense;
        this.setTurnsLeft(defenseStrategy.getRemaining());
    }

    @Override
    public void updateView() {
        int turnsLeft = defenseStrategy.getRemaining();

        if (turnsLeft == 0) {
            EffectListPanel panel = GameFrame.getInstance().getEffectListPanel();
            panel.removeEffect(this);
        }
        else {
            setTurnsLeft(turnsLeft);
        }
    }
}
