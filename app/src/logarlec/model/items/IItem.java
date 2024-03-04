package logarlec.model.items;

import logarlec.model.labyrinth.Room;
import logarlec.model.characters.Actor;
import logarlec.model.enums.Event;

public interface IItem {
    /**
     * Egy karakter megpróbálja használni az adott tárgyat egy adott eseményre, visszaadja, hogy sikerült-e
     */
    public boolean use(Actor invoker, Event event);

    /**
     * Egy másik tárghy megpróbálja használni az adott tárgyat egy adott eseményre, visszaadja, hogy sikerült-e
     */
    public default boolean use(IItem invoker, Event event) {
        return false;
    }

    /**
     * A tárgy felvételekor hívódik meg
     */
    public default void onPickup(Actor newOwner) {}

    /**
     * A tárgy eldobásakor hívódik meg
     */
    public default void onDrop(Room inRoom) {}

    /**
     * Visszaadja, hogy a tárgy hányszor használható még
     */
    public default int getUsesLeft() {
        return 1;
    }

}
