package logarlec.model.items.impl;

import logarlec.control.controller.Player;
import logarlec.model.actor.Actor;
import logarlec.model.items.Item;
import logarlec.model.items.ItemVisitor;
import logarlec.view.observerviews.View;
import logarlec.view.panels.ItemPanel;

/**
 * The beer item defends the player from attacks from a certain amount of turns.
 */
public class AirFreshener extends Item {

    public AirFreshener() { }

    public AirFreshener(Integer usesLeft){
        super(usesLeft);
    }

    /**
     * Uses the beer item. It switches the defense strategy for a BeerDefense
     * strategy.
     * 
     * @param invoker The actor that uses the item.
     */
    @Override
    public void use(Actor invoker) {

        if(invoker.getLocation().clean()){
            usesLeft--;
        }

        super.use(invoker);
        
    }

    /**
     * Accepts a visitor.
     * 
     * @param visitor The visitor to accept.
     */
    @Override
    public void accept(ItemVisitor visitor) {
        visitor.visit(this);
        
    }

    @Override
    public ItemPanel createOwnView() {
        ItemPanel itemPanel = new ItemPanel(this, "air-freshener.png");
        addListener(itemPanel);
        return itemPanel;
    }
}
