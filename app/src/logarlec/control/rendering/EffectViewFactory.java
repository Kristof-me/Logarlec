package logarlec.control.rendering;

import logarlec.model.actor.actions.StunnedStep;
import logarlec.model.actor.strategy.DefenseStrategy;
import logarlec.model.room.*;
import logarlec.view.panels.*;

public class EffectViewFactory {
    public EffectPanel createPanel(DefenseStrategy defenseStrategy) {
        Integer remaining = defenseStrategy.getRemaining();
        
        if(remaining == 0) {
            return null;
        }
        
        EffectPanel panel;

        if(remaining == Integer.MAX_VALUE) {
            panel = new DefenseStrategyPanel(defenseStrategy, "tvsz text", "tvsz.png");
        }
        else {
            panel = new DefenseStrategyPanel(defenseStrategy, "beer defense text", "beer.png");
        }

        defenseStrategy.addListener(panel);
        return panel;
    }

    public EffectPanel createPanel(GasEffect gasEffect) {
        EffectPanel panel = new RoomEffectPanel(gasEffect, "gas text", "effect-gas.png");
        gasEffect.addListener(panel);
        return panel;
    }

    public EffectPanel createPanel(WetEffect wetEffect) {
        EffectPanel panel = new RoomEffectPanel(wetEffect, "wet text", "effect-wet.png");
        wetEffect.addListener(panel);
        return panel;
    }

    public EffectPanel createPanel(StickyEffect stickyEffect) {
        EffectPanel panel = new RoomEffectPanel(stickyEffect, "sticky effect text", "effect-sticky.png");
        stickyEffect.addListener(panel);
        return panel;
    }

    public EffectPanel createPanel(StunnedStep stunnedStep) {
        EffectPanel panel = new StunnedStrategyPanel(stunnedStep, "stunned text", "stunned.png");
        stunnedStep.addListener(panel);
        return panel;
    }
}
