package logarlec.model.actor.strategy;

import logarlec.model.actor.Actor;
import logarlec.model.items.BestTvszFinder;
import logarlec.model.items.Inventory;
import logarlec.model.items.impl.Tvsz;
import logarlec.control.GameManager;
import logarlec.model.room.Room;


/**
 * Default defense strategy. It tries to use the best TVSZ in the inventory, otherwise dies.
 */
public class DefaultDefense extends DefenseStrategy {
    private int lastDefenseTick = -2;
    private Room usedIn = null;
    
    /**
     * Constructor.
     * @param actor Actor to defend.
     */
    public DefaultDefense(Actor actor) {
        super(actor);
    }

    private boolean isTvszActive() {
        int timeDiff = GameManager.getInstance().getTick() - lastDefenseTick;
        return actor.getLocation() == usedIn && timeDiff <= 1; // TODO watch out for ticks here
    }

    /**
     * Defends the actor by using the best TVSZ in the inventory, otherwise dies.
     * @param inventory Inventory to search for TVSZ.
     * 
     * @return True if the actor was defended, false otherwise.
     */
    @Override
    public boolean defend(Inventory inventory) {
        if(!isTvszActive()) { 
            BestTvszFinder finder = new BestTvszFinder();

            Tvsz tvsz = finder.findIn(inventory);
            if (tvsz != null) {
                tvsz.use(actor);
                usedIn = actor.getLocation();
            }
            else{
                return false;
            }
        }
        lastDefenseTick =  GameManager.getInstance().getTick();
        return true;
    }

    @Override
    public Integer getRemaining() {
        return isTvszActive() ? Integer.MAX_VALUE : 0;
    }
}
