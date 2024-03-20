package logarlec.model.actor.actions;

import logarlec.model.items.Item;
import logarlec.model.room.Door;

public interface IActions {
    void attack();

    boolean move(Door door);

    void use(Item item);

    boolean pickUp(Item item);

    void drop(Item item);
}
