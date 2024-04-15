package logarlec.control.commands;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map.Entry;

import logarlec.model.room.*;
import logarlec.control.GameManager;
import logarlec.control.Interpreter;
import logarlec.model.actor.*;
import logarlec.model.items.Inventory;
import logarlec.model.items.Item;
import logarlec.model.items.impl.*;

public class Create extends Command {
    /**
     * Executes the create command. <br>
     * Creates a new object of the given type and adds it to the game.
     * 
     * @param input the input string containing the command
     * @return true if the command was successful, false otherwise
     */
    @Override
    public boolean execute(String input) {
        String[] data = removeExtraSpace(input).split(" ", 3);
        // missing arguments
        if(data.length < 2) {
            return false;
        }

        Class<?> type = classes.get(data[0].toLowerCase());

        // type not supported
        if(type == null) { 
            return false;
        }

        String variableName = data[1];

        // is empty or name is taken
        if(variableName.isBlank() || isNameTaken(variableName)) {
            return false;
        }

        String remaining = data.length > 2 ? toShortOptions(data[2]) : "";

        // handle items
        if(type.getSuperclass() == Item.class) { 
            return handleItemCreation(type, variableName, remaining);
        }

        // handle actors
        if(type.getSuperclass() == Actor.class) { 
            return handleActorCreation((Class<? extends Actor>) type, variableName, remaining);
        }

        // handle rooms
        if(type == Room.class) { 
            return handleRoomCreation(variableName, remaining);
        }
        
        
        // handle doors
        if(type == Door.class) { 
            return handleDoorCreation(variableName, remaining);
        }


        return false;
    }

    public Create() {
        longOptions = new HashMap<>();

        longOptions.put("one-way", "o");
        longOptions.put("fake", "f");
    }

    HashMap<String, Class<?>> classes = new HashMap<>() {{
        put("door", Door.class);
        put("room", Room.class);
        put("professor", Professor.class);
        put("student", Student.class);
        put("janitor", Janitor.class);
        put("sliderule", SlideRule.class);
        put("gas-mask", GasMask.class);
        put("tvsz", Tvsz.class);
        put("transistor", Transistor.class);
        put("beer", Beer.class);
        put("camembert", Camembert.class);
        put("cocktail", Cocktail.class);
        put("sponge", Sponge.class);
        put("airfreshener", AirFreshener.class);
    }};

     // classes that have an inventory
     Class<?>[] hasInventory = { Room.class, Student.class, Professor.class };


    /**
     * Handles the creation of an item.
     * 
     * @param type the tpye of the item
     * @param variableName the name of the variable (not empty, not taken - checked before)
     * @param data the remaining data (options without extra spaces)
     * @return true if the item was created successfully, false otherwise
     */
    private boolean handleItemCreation(Class<?> type, String variableName, String data) {
        String[] remaining = data.split(" ", 2);

        // getting the inventory
        Entry<Class<?>, Object> inventoryHolder = findVariableMatching(hasInventory, remaining[0]);

        if(inventoryHolder == null) {
            return false;
        }

        
        Inventory inventory = (Inventory) invoke(inventoryHolder, "getInventory").getKey();

        if(inventory == null) {
            return false;
        }

        // handling options
        boolean isFake = false;
        Integer usesLeft = null;

        if(remaining.length > 1 && !remaining[1].isBlank()) {
            String options = remaining[1];

            // fake option
            isFake = options.contains("-f");
            options = options.replaceAll("-f", "");
            
            // uses left option
            if(options.contains("-u")) {
                options = options.replaceAll("-u", "");

                // because this is the last remaining value
                usesLeft = tryParse(options);

                if(usesLeft == null) {
                    return false;
                }
                
                options = options.replaceAll(usesLeft.toString(), "");

                // missing or out of range error
                if(usesLeft == null || usesLeft < 1 || usesLeft > 100) {
                    return false;
                }
            }

            if(!options.isBlank()) {
                // invalid option
                return false;
            }
        }
        
        // generate item
        Item item = null;
        try {
            item = (Item) type.getConstructor().newInstance();
        } catch (Exception e) {
            return false;
        }
        
        if(isFake) {
            item = new FakeItem(item);    
        }

        if(usesLeft != null) {
            item.setUsesLeft(usesLeft);
        }

        inventory.addItem(item);

        if(inventoryHolder.getValue() instanceof Actor) {
            item.onPickup((Actor) inventoryHolder.getValue());
        } else {
            item.onDrop((Room) inventoryHolder.getValue());
        }
        

        Interpreter.getInstance().AddVariable(variableName, item);
        return true;
    }

    /**
     * Handles the creation of an actor.
     * 
     * @param type the type of the actor
     * @param variableName the name of the variable (not empty, not taken - checked before)
     * @param data the remaining data (options without extra spaces or empty string if no options are present)
     * @return true if the actor was created successfully, false otherwise
     */
    private boolean handleActorCreation(Class<? extends Actor> type, String variableName, String data) {
        String[] remaining = data.split(" ", 2);

        // get spawn-room
        String spawnRoom = remaining[0];
        Entry <Class<?>, Object> spawnRoomEntry = findVariable(Room.class, spawnRoom);

        if(spawnRoomEntry == null) {
            return false;
        }

        Room room = (Room) spawnRoomEntry.getValue();

        // handling options
        String options = null;
        Integer inventorySize = null;
        
        if(remaining.length > 1 && !remaining[1].isBlank()) {
            if(remaining[1].contains("-i")) {
                options = remaining[1].replaceAll("-i", "");
            } else {
                // invalid option
                return false;
            }
            
            // because this is the last remaining value
            inventorySize = tryParse(options.trim());
            
            // missing or out of range error
            if(inventorySize == null || inventorySize < 1 || inventorySize > 100 || type == Janitor.class) {
                return false;
            }
        }

        // create actor 
        Actor actor = null;
        try {
            actor = (Actor) type.getConstructor().newInstance();
        } catch (Exception e) {
            return false;
        }
    
        try {
            // adding the actor to game manager
            Method addMethod = GameManager.class.getMethod("add" + type.getSimpleName(), type);
            addMethod.invoke(GameManager.getInstance(), type.cast(actor));
        } catch (Exception e) {
            return false;
        }

        // adding the actor to variable list
        Interpreter.getInstance().AddVariable(variableName, actor);
        
        // setting the spawn room
        actor.teleport(room, false); // ? is the actor supposed to be added by teleport?

        if(inventorySize != null) {
            actor.getInventory().setSize(inventorySize);
        }

        return true;
    }
    
    /**
     * Handles the creation of a room.
     * @param variableName the name of the variable (not empty, not taken - checked before)
     * @param remaining the remaining data (options without extra spaces or empty string if no options are present)
     * @return true if the room was created successfully, false otherwise
     */
    private boolean handleRoomCreation(String variableName, String remaining) {
        Integer capacity = 10;

        // handle options
        if(!remaining.isBlank()) {
            if(remaining.contains("-c")) {
                remaining = remaining.replaceAll("-c", "");

                capacity = tryParse(remaining.trim());
            } 
            else { 
                // invalid option
                return false;
            }

            // missing or out of range error
            if(capacity == null || capacity < 1 || capacity > 100) {
                return false;
            }
        }

        Room room = new Room(capacity);
        GameManager.getInstance().addRoom(room);
        Interpreter.getInstance().AddVariable(variableName, room);

        return true;
    }

    /**
     * Handles the creation of a door.
     * @param variableName the name of the variable (not empty, not taken - checked before)
     * @param remaining the remaining data (options without extra spaces or empty string if no options are present)
     * @return true if the door was created successfully, false otherwise
     */
    private boolean handleDoorCreation(String variableName, String remaining) {
        String[] data = remaining.split(" ", 3);

        if(data.length < 2) {
            return false;
        }

        // get rooms
        Entry<Class<?>, Object> room1Entry = findVariable(Room.class, data[0]);
        Entry<Class<?>, Object> room2Entry = findVariable(Room.class, data[1]);

        if(room1Entry == null || room2Entry == null) {
            return false;
        }

        Room room1 = (Room) room1Entry.getValue();
        Room room2 = (Room) room2Entry.getValue();

        // handle options
        boolean isOneWay = false;
        String options = data.length > 2 ? data[2] : "";

        if(!options.isBlank()) {
            if(options.contains("-o")) {
                isOneWay = true;
                options = options.replaceAll("-o", "");
            }

            // invalid option (extra data)
            if(!options.isBlank()) { 
                return false;
            }
        }

        Door door = new Door(room1, room2, isOneWay);
        Interpreter.getInstance().AddVariable(variableName, door);

        return true;
    }
}
