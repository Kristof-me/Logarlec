package logarlec.model.actor.strategy;

import logarlec.model.actor.Actor;
import logarlec.model.items.Inventory;

public abstract class DefenseStrategy {
    public abstract boolean tick();

    public abstract boolean defend(Inventory inventory);
}
