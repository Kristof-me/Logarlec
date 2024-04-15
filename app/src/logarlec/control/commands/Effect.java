package logarlec.control.commands;

import java.util.HashMap;
import java.util.Map.Entry;
import logarlec.model.room.*;
import logarlec.model.actor.*;
import logarlec.model.actor.actions.StunnedStep;

public class Effect extends Command {
    HashMap<String, Class<? extends RoomEffect>> effects = new HashMap<>() {{
        put("gas", GasEffect.class);
        put("wet", WetEffect.class);
        put("sticky", StickyEffect.class);
    }};

    @Override
    public boolean execute(String input) {
        String[] data = input.split(" ", 2);

        String effectType = data[0].toLowerCase();

        if (data.length != 2 || data[1].isBlank()) {
            return false;
        }

        if(effectType.equals("stun")) {
            return handleUserEffect(data[1]);
        } else {
            // getting the effect
            Class<? extends RoomEffect> effectClass = effects.get(effectType);
    
            if (effectClass == null) {
                return false;
            }

            return handleNewEffect(effectClass, data[1]);
        }
    }
 
    private boolean handleNewEffect(Class<? extends RoomEffect> effectClass, String remaining) {
        String[] data = remaining.split(" ", 2);

        // getting the room
        Entry<Class<?>, Object> roomEntry = findVariable(Room.class, data[0]);

        if (roomEntry == null) {
            return false;
        }

        Room room = (Room) roomEntry.getValue();

        // creating the effect
        RoomEffect effect;

        try {
            effect = effectClass.getConstructor(Room.class).newInstance(room);
        } catch (Exception e) {
            return false;
        }

        // handling options
        if(data.length == 2 && !data[1].isBlank()) {
            String option = data[1];
            Integer timeLeft = null;

            if(option.contains("-t")) {
                option = option.replaceAll("-t", "").trim();

                timeLeft = tryParse(option);
                
                // missing or out of range error
                if(timeLeft == null || timeLeft < 1 || timeLeft > 10000) {
                    return false;
                }

                effect.setTimeLeft(timeLeft);
            } else {
                return false;
            }
        }

        // adding the effect to the room
        room.addEffect(effect);

        return true;
    }

    Class<?>[] stunnable = new Class[] { Professor.class, Student.class };

    private boolean handleUserEffect(String remaining) {
        String[] data = remaining.split(" ", 2);

        // getting the target actor
        Entry<Class<?>, Object> actorEntry = findVariableMatching(stunnable, data[0]);

        if (actorEntry == null) {
            return false;
        }

        Actor actor = (Actor) actorEntry.getValue();

        // creating the effect
        StunnedStep effect = new StunnedStep(actor);
        
        // handle options
        if(data.length == 2 && !data[1].isBlank()) {
            String option = data[1];
            Integer timeLeft = null;

            if(option.contains("-t")) {
                option = option.replaceAll("-t", "").trim();

                timeLeft = tryParse(option);

                // missing or out of range error
                if(timeLeft == null || timeLeft < 1 || timeLeft > 10000) {
                    return false;
                }
            } else {
                return false;
            }

            effect.setRemaining(timeLeft);
        }

        // actually applying the effect
        actor.setActionState(new StunnedStep(actor));
        
        return true;
    }
}
