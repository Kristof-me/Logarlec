package logarlec.model.characters;

import java.util.Map;
import logarlec.model.characters.visitor.IActorVisitor;
import logarlec.model.enums.RoomEffect;
import logarlec.model.labyrinth.Room;

/**
 * Egy oktatót reprezentáló osztály.
 */
public class Professor extends Actor {

    public Professor(Room spawnRoom) {
        super(spawnRoom, 2);
    }

    /**
     * visszaadja az értéket, hogy támadhat-e
     */
    public boolean attack() {
        return true;
    }

    public void accepts(IActorVisitor v) {
        v.visit(this);
    }

    @Override
    public void handleRoomEffects(Map<RoomEffect, Integer> effects) {
        // Handle the effects
    }
}
