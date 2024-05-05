package logarlec.view.panels;

import java.awt.Color;

import javax.swing.ImageIcon;
import logarlec.model.actor.strategy.BeerDefense;
import logarlec.view.utility.IconLoader;

public class BeerDefensePanel extends EffectPanel {

    protected BeerDefense beerDefense;
    public BeerDefensePanel(BeerDefense defense, String description, String icon) {
        super(Color.green, description, IconLoader.getInstance().getIcon(icon, 30));
        this.beerDefense = defense;
        this.setTurnsLeft(beerDefense.getRemaining());
    }

    @Override
    public void updateView() {
        int turnsLeft = beerDefense.getRemaining();
        if (turnsLeft == 0) {
            System.out.println("Removing beer defense");
            EffectsPanel.getInstance().removeEffect(this);
        }
        else {
            setTurnsLeft(turnsLeft);
            System.out.printf("Beer defense remaining: %d\n", turnsLeft);
        }

    }

    
}
