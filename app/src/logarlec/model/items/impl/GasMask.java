package logarlec.model.items.impl;

import logarlec.model.actor.Actor;
import logarlec.model.items.Item;
import logarlec.model.items.ItemVisitor;



/**
 * A gas mask that will protect the player if it steps into a gased room.
 * Considered a passive item, so the player doesn't use it, it gets used
 * automatically.
 */
public class GasMask extends Item {

    public GasMask() { }

    public GasMask(Integer usesLeft){
        super(usesLeft);
    }

    /**
     * The gas mask will be used automatically when the player steps into a gased
     * room.
     * The usesLeft field will be decremented.
     * 
     * @param invoker The actor that has the gas mask
     */
    @Override
    public void use(Actor invoker) {
        usesLeft--;
        super.use(invoker);
        
    }

    /**
     * Accepts a visitor to visit this item.
     * 
     * @param visitor The visitor
     */
    @Override
    public void accept(ItemVisitor visitor) {
        visitor.visit(this);
        
    }

}
