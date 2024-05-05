package logarlec.view.rendering;

import logarlec.model.items.impl.*;
import logarlec.view.panels.ItemPanel;
import logarlec.view.panels.TransistorPanel;
import logarlec.model.items.Item;
import logarlec.model.items.ItemVisitor;

public class ItemDrawer extends ItemVisitor {
    private ItemPanel panel;

    public synchronized void createPanel(Item item) {
        item.accept(this);
    }

    public void visit(AirFreshener airFreshener) {
        panel = new ItemPanel(airFreshener, "air-freshener.png");
        airFreshener.addListener(panel);
    }

    public void visit(Beer beer) {
        panel = new ItemPanel(beer, "beer.png");
        beer.addListener(panel);
    }

    public void visit(Cocktail cocktail) {
        panel = new ItemPanel(cocktail, "cocktail.png");
        cocktail.addListener(panel);
    }

    public void visit(Camembert camembert) {
        panel = new ItemPanel(camembert, "camembert.png");
        camembert.addListener(panel);
    }

    public void visit(FakeItem fakeItem) {
        fakeItem.getItemToFake().accept(this);
        fakeItem.addListener(panel);
    }

    public void visit(GasMask gasMask) {
        panel = new ItemPanel(gasMask, "gas-mask.png");
        gasMask.addListener(panel);
    }

    public void visit(SlideRule slideRule) {
        panel = new ItemPanel(slideRule, "slide-rule.png");
        slideRule.addListener(panel);
    }

    public void visit(Sponge sponge) {
        panel = new ItemPanel(sponge, "sponge.png");
        sponge.addListener(panel);
    }
    
    public void visit(Transistor transistor) {
        panel = new TransistorPanel(transistor, "transistor.png");
        transistor.addListener(panel);
    }

    public void visit(Tvsz tvsz) {
        panel = new ItemPanel(tvsz, "tvsz.png");
        tvsz.addListener(panel);
    }

    public ItemPanel getPanel() {
        return panel;
    }
}
