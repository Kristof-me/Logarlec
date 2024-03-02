package logarlec.model.characters;

import logarlec.model.labyrinth.Room;
import logarlec.model.labyrinth.IHasLocation;

import java.util.List;

import logarlec.model.enums.ActorEffect;
import logarlec.model.items.Item;

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

    public void Step() {
    }

    protected void Use(int index) {
        inventoryManager.Use(index);
    }

    protected void Drop(int index) {
        Item dropped = inventoryManager.removeItem(item);

        if (dropped != null) {
            room.droppedItem(item);
        }
    }

    protected void Search() {
        List<Item> items = room.getItems();
    }

    protected void PickUp(Item item) {
        if (room.takeItem(item)) { // ha sikerül elvenni
            if (!inventoryManager.addItem(item)) { // ha nem sikerül eltenni
                room.droppedItem(item); // akkor adjuk vissza
            }
        }
    }

    public void Move(Room destination, boolean forced) {
        // if it's forced and the room is full, Kill() the actor
    }

    public void AddEffect(ActorEffect effect) {
        // Add the effect
    }

    public void Kill() {
        // public, mert a room manager is meg tudja hívni
        isAlive = false;
    }
}
