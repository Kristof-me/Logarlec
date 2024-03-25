package logarlec.model.items;

import java.util.List;

/**
 * An abstract class for finding items in an inventory. Uses the visitor pattern.
 * 
 * @param <T> The type of item to find.
 */
public abstract class ItemFinder<T extends Item> extends ItemVisitor {
    protected List<T> potentialItems;

    public abstract T findIn(Inventory inventory);
}
