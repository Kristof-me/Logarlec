package logarlec.model.items.impl;

import logarlec.model.room.IHasLocation;
import logarlec.model.room.Room;
import logarlec.model.actor.Actor;
import logarlec.model.items.Item;
import logarlec.model.items.ItemVisitor;
import logarlec.model.items.TransistorPairFinder;



/**
 * A transistor that can be paired with another transistor to teleport the
 * player
 * to the room of the other transistor when used.
 */
public class Transistor extends Item {
    private Transistor pair; // The transistor that this is paired with
    private IHasLocation location;

    public Transistor() {
        pair = null;
    }

    /**
     * If the transistor does not have a pair, it will find a pair in the inventory
     * of the invoker.<br>
     * If it already has a pair, it will teleport the invoker to the room of the
     * paired transistor.
     * 
     * @param invoker The actor that uses the transistor
     */
    @Override
    public void use(Actor invoker) {

        if (pair == null) {
            TransistorPairFinder pairFinder = new TransistorPairFinder(this);
            Transistor target = pairFinder.findIn(invoker.getInventory());

            if (target != null) {
                pairWith(target);
                target.pairWith(this);
            }
        } else { // Already paired
            Room room = pair.location.getLocation();
            if (room != location.getLocation()) { // cannot teleport to the same room
                invoker.teleport(room, false);
                pair.pairWith(null);
                pairWith(null);
            }
        }
        super.use(invoker);
        
    }

    /**
     * Sets the location of the transistor to the actor that picked it up.
     * 
     * @param actor The actor that picked up the transistor
     */
    @Override
    public void onPickup(Actor actor) {
        location = actor;
        super.onPickup(actor);
    }

    /**
     * Sets the location of the transistor to the room it was dropped in.
     * 
     * @param location The room the transistor was dropped in
     */
    @Override
    public void onDrop(Room location) {
        this.location = location;
        super.onDrop(location);
    }

    /**
     * Accepts a visitor to visit the transistor.
     * 
     * @param visitor The visitor
     */
    @Override
    public void accept(ItemVisitor visitor) {
        visitor.visit(this);
        
    }

    /**
     * Pairs this transistor with another transistor.
     * 
     * @param pair The transistor to pair with
     */
    public void pairWith(Transistor pair) {
        this.pair = pair;
        
    }

    public Transistor getPair() {
        return pair;
    }
}
