package logarlec.model.items;

import java.util.List;

public abstract class ItemFinder<T extends Item> extends ItemVisitor {
    protected List<T> potentialItems;

    public abstract T findIn(Inventory inventory);
}
