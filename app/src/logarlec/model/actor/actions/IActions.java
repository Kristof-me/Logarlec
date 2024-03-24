package logarlec.model.actor.actions;

import logarlec.model.items.Item;
import logarlec.model.room.Door;

public interface IActions {
    public void attack();

    public boolean move(Door door);

    public void use(Item item);

    public boolean pickUp(Item item);

    public void drop(Item item);
}
