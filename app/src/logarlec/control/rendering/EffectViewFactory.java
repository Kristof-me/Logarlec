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
            panel = new DefenseStrategyPanel(defenseStrategy, "May the TVSZ bless you", "tvsz.png");
        }
        else {
            panel = new DefenseStrategyPanel(defenseStrategy, "You're invincible", "beer.png");
        }

        defenseStrategy.addListener(panel);
        return panel;
    }

    public EffectPanel createPanel(GasEffect gasEffect) {
        EffectPanel panel = new RoomEffectPanel(gasEffect, "Something smells cheesy", "effect-gas.png");
        gasEffect.addListener(panel);
        return panel;
    }

    public EffectPanel createPanel(WetEffect wetEffect) {
        EffectPanel panel = new RoomEffectPanel(wetEffect, "The board is wet", "effect-wet.png");
        wetEffect.addListener(panel);
        return panel;
    }

    public EffectPanel createPanel(StickyEffect stickyEffect) {
        EffectPanel panel;
        if (stickyEffect.getRoom().getIsSticky())
            panel = new RoomEffectPanel(stickyEffect, "Everything is stuck to the floor", "effect-sticky.png");
        else 
            panel = new RoomEffectPanel(stickyEffect, "The floor is wet", "effect-sticky.png");    
        stickyEffect.addListener(panel);
        return panel;
    }

    public EffectPanel createPanel(StunnedStep stunnedStep) {
        EffectPanel panel = new StunnedStrategyPanel(stunnedStep, "You are stunned", "stunned.png");
        stunnedStep.addListener(panel);
        return panel;
    }
}
