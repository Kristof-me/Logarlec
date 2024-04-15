package logarlec.control.commands;

import java.util.HashMap;
import java.util.Map.Entry;
import logarlec.model.room.*;
import logarlec.model.items.*;
import logarlec.model.items.impl.*;
import logarlec.control.GameManager;
import logarlec.control.Interpreter;
import logarlec.model.actor.*;
import logarlec.model.actor.strategy.*;
import logarlec.model.actor.actions.*;

public class Status extends Command {
    Class<?>[] targetClasses = {Room.class, Door.class, Item.class, Actor.class};

    HashMap<String, String> translations = new HashMap<String, String>() {{
        put(GasEffect.class.getName(), "gáz");
        put(WetEffect.class.getName(), "víz");
        put(StickyEffect.class.getName(), "ragad");
        put(Boolean.toString(true), "igaz");
        put(Boolean.toString(false), "hamis");
        put(BeerDefense.class.getName(), "sör");
        put(DefaultDefense.class.getName(), "alap");
    }};

    @Override
    public boolean execute(String input) {
        String[] attributes = removeExtraSpace(input).split(" ", 2);
        
        if (attributes.length != 2) {
            return false;
        }
        
        if(attributes[0].equals("game") && attributes[1].equals("state")) {
            return handleGameState();
        }

        Entry<Class<?>, Object> variable = findVariableMatching(targetClasses, attributes[0]);
        
        if (variable == null) {
            return false;
        }

        attributes[1] = attributes[1].toLowerCase();

        if(variable.getKey() == Room.class) {
            Room room = (Room) variable.getValue();
            return handleRoom(room, attributes[1]);
        }

        if(variable.getKey() == Door.class) {
            Door door = (Door) variable.getValue();
            return handleDoor(door, attributes[1]);
        }

        if(variable.getKey() == Item.class) {
            Item item = (Item) variable.getValue();
            return handleItem(item, attributes[1]);
        }

        if(variable.getKey() == Actor.class) {
            Actor actor = (Actor) variable.getValue();
            return handleActor(actor, attributes[1]);
        }

        return false;
    }

    private boolean handleGameState() {
        if(GameManager.getInstance().isWon()) {
            System.out.println("hallgató nyert");
        } else if(GameManager.getInstance().isGameOver()) {
            System.out.println("professzor nyert");
        } else {
            System.out.println("folyamatban van");
        }
        
        return true;
    }

    private boolean handleRoom(Room target, String option) {
        switch (option) {
            case "effects":
                for (RoomEffect effect : target.getRoomEffects()) {
                    String effectName = translations.get(effect.getClass().getName());
                    System.out.println(effectName + ": " + effect.getTimeLeft());
                }

                if(target.getRoomEffects().isEmpty()) {
                    System.out.println("-");
                }
                return true;

            case "doors":
                for (Door door : target.getDoors()) {
                    String doorName = Interpreter.getInstance().getVariableName(door);
                    System.out.println(doorName);

                    if(target.getDoors().isEmpty()) {
                        System.out.println("-");
                    }
                }
                return true;

            case "actors":
                for (Actor actor : target.getActors()) {
                    String actorName = Interpreter.getInstance().getVariableName(actor);
                    System.out.println(actorName);
                }

                if(target.getActors().isEmpty()) {
                    System.out.println("-");
                }
                return true;

            case "capacity":
                System.out.println(target.getCapacity().toString());
                return true;

            case "inventory":
                listInventory(target.getInventory());

            case "sticky":
                System.out.println(translations.get(target.getIsSticky()));
                return true;

            default:
                return false;
        }
    }

    private boolean handleDoor(Door target, String option) { 
        switch (option) {
            case "invisible":
                System.out.println(target.getRemainingInvisibility());
                return true;

            case "oneway":
                System.out.println(translations.get(target.getIsOneWay().toString()));
                return true;

            case "rooms":
                Room[] rooms = target.getRooms();
                String room1 = Interpreter.getInstance().getVariableName(rooms[0]);
                String room2 = Interpreter.getInstance().getVariableName(rooms[1]);
                System.out.println(room1 + " " + room2);
                return true;
        
            default:
                return false;
        }
    }

    private boolean handleItem(Item target, String option) { 
        switch (option) {
            case "uses-left": {
                System.out.println(target.getUsesLeft());
                return true;
            }

            case "inventory": {
                Inventory inventory = target.getInventory();
                IHasLocation owner = inventory.getOwner();
                System.out.println(Interpreter.getInstance().getVariableName(owner));
                return true;
            }

            case "fake": {
                Boolean isFake = FakeItem.class.isInstance(target);
                System.out.println(translations.get(isFake.toString()));
                return true;
            }

            case "location": {
                Inventory inventory = target.getInventory();
                IHasLocation owner = inventory.getOwner();

                Room location = Room.class.isInstance(owner) ? (Room) owner : ((Actor) owner).getLocation();
                printLocation(location);
                return true;
            }

            case "pair":
                if(!Transistor.class.isInstance(target)) { 
                    return false;
                
                }
                Transistor transistor = (Transistor) target;
                Transistor pair = transistor.getPair();

                String result = "-";

                if(pair != null) {
                    result = Interpreter.getInstance().getVariableName(pair);
                }

                System.out.println(result);
                return true;
                

            default:
                return false;
        }
    }

    private boolean handleActor(Actor actor, String option) { 
        switch (option) {
            case "alive":
                System.out.println(translations.get(actor.isAlive().toString()));
                return true;

            case "location":
                printLocation(actor.getLocation());
                return true;

            case "inventory":
                listInventory(actor.getInventory());
                return true;

            case "defense":
                System.out.println(translations.get(actor.getDefense().getClass().getName()));
                return true;

            case "stunned":
                Boolean isStunned = StunnedStep.class.isInstance(actor.getState());
                System.out.println(translations.get(isStunned.toString()));
                return true;

            default:
                return false;
        }
    }

    private void listInventory(Inventory inventory) {
        for (Item item : inventory.getItems()) {
            String itemName = Interpreter.getInstance().getVariableName(item);
            System.out.println(itemName);
        }

        if(inventory.getItems().isEmpty()) {
            System.out.println("-");
        }
    }

    private void printLocation(Room room) {
        String result = "-";

        if(room != null) {
            result = Interpreter.getInstance().getVariableName(room);
        }

        System.out.println(result);
    }
}
