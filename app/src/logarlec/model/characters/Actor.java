package logarlec.model.characters;

import logarlec.model.labyrinth.Room;
import logarlec.model.labyrinth.IHasLocation;

import java.util.List;
import javax.lang.model.element.Element;
import logarlec.model.characters.visitor.*;
import logarlec.model.enums.ActorEffect;
import logarlec.model.enums.RoomEffect;
import logarlec.model.items.Item;

public abstract class Actor implements IHasLocation, IElement {
    protected InventoryManager inventoryManager;
    protected boolean isAlive;
    protected Room room;
    // protected List<Pair<ActorEffect, Integer>> effects;

    protected Actor(Room spawnRoom, int inventorySize) {
        room = spawnRoom;
        spawnRoom.Move(this, false);
        inventoryManager = new InventoryManager(this, inventorySize);

        isAlive = true;
    }

    public Room getLocation() {
        return room;
    }

    public void Step() {}

    protected void Use(int index) {
        inventoryManager.Use(index);
    }

    protected void Drop(int index) {
        Item dropped = inventoryManager.removeItem(index);

        if (dropped != null) {
            room.droppedItem(dropped);
        }
    }

    protected void Search() {
        List<Item> items = room.getItems();
    }

    protected void PickUp(Item item) {
        Item got = room.takeItem(item);

        if (got != null) { // ha sikerül elvenni
            if (!inventoryManager.addItem(item)) { // ha nem sikerül eltenni
                room.droppedItem(item); // akkor adjuk vissza
            }
        }
    }

    public abstract void accepts(IVisitor v);

    public void Move(Room destination, boolean forced) {
        // if it's forced and the room is full, Kill() the actor
    }

    public void AddEffect(ActorEffect effect) {
        // Add the effect
    }

    public void HandleRoomEffect(RoomEffect effect) {}

    public void Kill() {
        // public, mert a room manager is meg tudja hívni
        isAlive = false;
    }

    public boolean isAlive() {
        return isAlive;
    }
}
