package logarlec.model.actor;

import java.util.List;

import logarlec.model.room.Door;
import logarlec.model.room.IHasLocation;
import logarlec.model.room.Room;
import logarlec.model.room.RoomEffect;
import logarlec.view.utility.ColorGenerator;
import logarlec.model.GameObject;
import logarlec.model.actor.actions.ActionState;
import logarlec.model.actor.actions.IActions;
import logarlec.model.actor.strategy.DefaultDefense;
import logarlec.model.actor.strategy.DefenseStrategy;
import logarlec.model.items.Inventory;
import logarlec.model.items.Item;
import logarlec.model.items.ItemFinder;
import java.awt.Color;

/**
 * Abstract class representing an actor in the game.<br>
 * Perfors actions with the help of the current action state.
 */
public abstract class Actor extends GameObject implements IHasLocation, IActions {
    private String name;
    private Color color;

    protected Room room;
    protected boolean alive;
    protected ActionState actionState;
    protected DefenseStrategy defenseStrategy;
    protected Inventory inventory;

    /**
     * Creates a new actor
     */
    protected Actor() {
        this.alive = true;
        this.inventory = new Inventory(9, this);
        this.defenseStrategy = new DefaultDefense(this);

        name = "";
        color = ColorGenerator.getInstance().random();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public void newRandomColor() {
        color = ColorGenerator.getInstance().random();
    }

    /**
     * Called when the actor is attacked, by default, the actor does not die when attacked
     */
    public void attacked() {
        // By default, the actor does not die when attacked
    }

    /**
     * Revives the actor, by default, the actor does not revive
     * 
     * @return true if the actor is revived, false otherwise
     */
    public boolean revive() {
        return false;
    }

    /**
     * Accepts an effect and applies it to the actor, unless the actor has an item that protects them from the effect
     * 
     * @param effect the effect to apply
     * @param unless the list of items that protect the actor from the effect
     */
    public abstract void acceptEffect(RoomEffect effect, List<ItemFinder<? extends Item>> unless);

    /**
     * Sets the default action state of the actor in case another one expires
     */
    public abstract void setDefaultActionState();

    /**
     * Sets the action state of the actor to the higher priority between the current action state and the new state
     * 
     * @param state the new action state
     */
    public void setActionState(ActionState state) {
        actionState = actionState.setNextState(state);
        update();
    }

    /**
     * Sets the defense strategy of the actor
     * 
     * @param strategy the new defense strategy
     */
    public void setDefenseStrategy(DefenseStrategy strategy) {
        defenseStrategy = strategy;
        update();
    }

    /**
     * Teleports the actor to a new room, without moving through a door If the actor is forced to move, the actor dies if
     * the room is full If the actor is not forced to move, the actor does not move if the room is full
     * 
     * @param room the room to teleport to
     * @param isForced if the actor is forced to move
     * @return true if the actor gets to stay alive / enter the room, false if actor has to die / or no space in the room
     */
    public boolean teleport(Room room, boolean isForced) {
        Room prevRoom = this.room;
        this.room = room;

        boolean staysAlive = room.enter(this, isForced);
        if (isForced && !staysAlive) {
            alive = false;
        }

        if (!staysAlive && !isForced) {
            this.room = prevRoom;
        } else {
            if (prevRoom != null) {
                prevRoom.leave(this);
            }
        }
        update();
        return staysAlive;
    }

    /**
     * Gets the inventory of the actor
     * 
     * @return the inventory of the actor
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Drops all items in the inventory to the room
     * 
     * @param room the room to drop the items to
     */
    public void dropAllTo(Room room) {
        if (!alive)
            return;
        inventory.dropAll(room);
        update();
    }

    /**
     * Ticks the actor, which in turn ticks the action state and the defense strategy If either one of them expires (their
     * tick returns false), the actor sets the default action state/denfense strategy
     */
    public void tick() {
        if (!actionState.tick()) {
            this.setDefaultActionState();
        }
        if (!defenseStrategy.tick()) {
            setDefenseStrategy(new DefaultDefense(this));
        }
    }

    @Override
    public void attack() {
        if (!alive)
            return;
        actionState.attack();
    }

    @Override
    public boolean move(Door door) {
        if (!alive)
            return false;
        boolean result = actionState.move(door);
        update();
        return result;
    }

    @Override
    public void use(Item item) {
        if (!alive)
            return;
        actionState.use(item);
        update();
    }

    @Override
    public boolean pickUp(Item item) {
        if (!alive)
            return false;
        boolean result = actionState.pickUp(item);
        update();
        return result;
    }

    @Override
    public void drop(Item item) {
        if (!alive)
            return;
        actionState.drop(item);
        update();
    }

    @Override
    public Room getLocation() {
        return room;
    }

    /**
     * Checks if the actor is alive
     * 
     * @return true if the actor is alive, false otherwise
     */
    public Boolean isAlive() {
        return alive;
    }

    /**
     * Sets the location of the actor
     * 
     * @param location the new location of the actor
     */
    public void setLocation(Room location) {
        this.room = location;
    }

    /**
     * Gets the defense strategy of the actor
     * 
     * @return the defense strategy of the actor
     */
    public DefenseStrategy getDefense() {
        return defenseStrategy;
    }

    /**
     * Gets the action state of the actor
     * 
     * @return the action state of the actor
     */
    public ActionState getState() {
        // we need an isStunned() method but that would be an implicit type check
        return actionState;
    }
}
