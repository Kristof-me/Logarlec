package logarlec.model.characters;

import java.util.List;
import java.util.ArrayList;
import logarlec.model.items.Item;
import logarlec.model.items.impl.Transistor;
import logarlec.model.effects.Event;

public class InventoryManager extends Inventory {
    Actor owner;

    public InventoryManager(Actor owner) {
        this.owner = owner;
    }

    public Boolean useGasMask() {
        List<Integer> orderedByUseIdx = new ArrayList<>();

        Integer i = 0;
        for (Item item : items) {
            Integer uses = item.getUsesLeft(Event.GAS);
            /*
             * Order the indexes by the uses left then call use on them in that order
             */
        }

        for (Integer idx : orderedByUseIdx) {
            if (items.get(idx).use(owner, Event.CONTROLLER_ACTIVATED)) {
                return true;
            }
        }

        return false;
    }

    public boolean useTvsz() {
        return false; // Almost same as GasMask case
    }

    public boolean getPair(Transistor transistor) {
        for (Item item : items) {
            if (item.use(transistor, Event.TRANSISTOR_PAIR_REQUEST)) {
                transistor.setPair(item);
                // transistorToPairWith.setPair((Transistor) item); // xd
                return true;
            }
        }

        return false;
    }
}
