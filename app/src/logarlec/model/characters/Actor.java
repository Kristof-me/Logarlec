package logarlec.model.characters;

import logarlec.model.labyrinth.Room;
import logarlec.model.labyrinth.IHasLocation;
import logarlec.model.enums.ActorEffect;

public abstract class Actor implements IHasLocation {
    protected InventoryManager inventoryManager;
    protected boolean isAlive;
    protected Room room;
    // protected List<Pair<ActorEffect, Integer>> effects;

    protected Actor(Room spawnRoom) {
        room = spawnRoom;
        inventoryManager = new InventoryManager(this);

        isAlive = true;
    }

    public Room getLocation() {
        return room;
    }

    public void Step() {}

    protected void Use() {}

    protected void Drop() {}

    protected void Search() {}

    protected void PickUp() {}

    public void Move(Room destination, boolean forced) {
        // if it's forced and the room is full, Kill() the actor
    }

    public void AddEffect(ActorEffect effect) {
        // Add the effect
    }

    private void Kill() {
        isAlive = false;
    }
}
