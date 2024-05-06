package logarlec.model.items.impl;

import java.util.Random;

import logarlec.control.GameManager;
import logarlec.model.actor.Actor;
import logarlec.model.items.Inventory;
import logarlec.model.items.Item;
import logarlec.model.items.ItemVisitor;
import logarlec.model.actor.strategy.BeerDefense;



/**
 * The beer item defends the player from attacks from a certain amount of turns.
 */
public class Beer extends Item {

    public Beer() { }

    public Beer(Integer usesLeft){
        super(usesLeft);
    }

    /**
     * Uses the beer item. It switches the defense strategy for a BeerDefense
     * strategy and drops a random (if random is on) item from the inventory.
     * 
     * @param invoker The actor that uses the item.
     */
    @Override
    public void use(Actor invoker) {

        BeerDefense beerDefense = new BeerDefense(invoker);
        invoker.setDefenseStrategy(beerDefense);
        usesLeft--;

        super.use(invoker);

        Inventory inventory = invoker.getInventory();
        if(inventory.getItems().isEmpty()) {
            return;
        }
        int max = inventory.getItems().size();
        int selected = new Random().nextInt(0, max);
        invoker.drop(inventory.getItems().get(selected));
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
}
