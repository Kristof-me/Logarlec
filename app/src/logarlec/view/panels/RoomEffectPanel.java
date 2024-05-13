package logarlec.view.panels;

import java.awt.Color;

import logarlec.model.room.RoomEffect;
import logarlec.view.frames.GameFrame;
import logarlec.view.observerviews.View;
import logarlec.view.utility.IconLoader;

public class RoomEffectPanel extends EffectPanel {
    protected RoomEffect effect;

    public RoomEffectPanel(RoomEffect defense, String description, String icon) {
        super(Color.red, description, IconLoader.getInstance().getIcon(icon, 30));
        this.effect = defense;
        this.setTurnsLeft(effect.getTimeLeft());
    }

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
