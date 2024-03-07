package logarlec.model.characters;

import logarlec.model.labyrinth.Room;
import java.util.Map;
import logarlec.model.characters.visitor.*;
import logarlec.model.enums.Event;
import logarlec.model.enums.*;

/**
 * Egy hallgatót reprezentáló osztály.
 * 
 */
public class Student extends Actor {

    public Student(Room spawnRoom) {
        super(spawnRoom, 5);
    }

    /**
     * Az oktató általi támadását kezelő metódus
     */
    public void attacked() {
        // Sort by use asc
        if (effects.containsKey(ActorEffect.TVSZ_ACTIVE) || effects.containsKey(ActorEffect.BEER))
            return;


        if (inventoryManager.autoUse(Event.ATTACK)) {
            effects.put(ActorEffect.TVSZ_ACTIVE, 1);

        } else {
            kill();
        }

    }

    public void accepts(IActorVisitor v) {
        v.visit(this);
    }

    /**
     * Újraéleszti a hallgatót
     */
    public void revive() {
        isAlive = true;
    }

    @Override
    public void handleRoomEffects(Map<RoomEffect, Integer> effects) {
        // Handle the effects
    }
}
