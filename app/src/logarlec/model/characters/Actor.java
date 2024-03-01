package logarlec.model.characters;

import logarlec.model.items.Item;
import logarlec.model.labyrinth.Room;

public abstract class Actor {
    private Item[] items = new Item[5];
    private boolean isAlive = true;
    private int stunRemaining = 0;
    // private Room room;

    public void Step() {}

    protected void Use() {}

    protected void Drop() {}

    protected void Search() {}

    protected void PickUp() {}

    protected void Move() {}
}
