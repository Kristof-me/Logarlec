package logarlec.control.rendering;

import logarlec.model.items.impl.*;
import logarlec.view.panels.ItemPanel;
import logarlec.view.panels.TransistorPanel;
import logarlec.model.items.Item;
import logarlec.model.items.ItemVisitor;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class ItemViewFactory extends ItemVisitor {
    private ItemPanel<? extends Item> panel;

    public ItemPanel<? extends Item>  createPanel(Item item) {
        item.accept(this);
        return panel;
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
        panel = new ItemPanel<FakeItem>(fakeItem, panel.getIcon());
        fakeItem.addListener(panel);
    }

    public void visit(GasMask gasMask) {
        panel = new ItemPanel(gasMask, "gas-mask.png");
        gasMask.addListener(panel);
    }

    public void visit(SlideRule slideRule) {
        panel = new ItemPanel(slideRule, "ruler.png");
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
}
