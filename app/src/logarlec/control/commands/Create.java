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

        Class<?> type = classes.get(data[0]);

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
        put("tvsz ", Tvsz.class);
        put("transistor", Transistor.class);
        put("beer", Beer.class);
        put("camambert", Camembert.class);
        put("cocktail", Cocktail.class);
        put("sponge", Sponge.class);
        put("airfreshener", AirFreshener.class);
    }};

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
        Inventory inventory = getInventory(remaining[0]);

        if(inventory == null) {
            return false;
        }

        // handling options
        String options = remaining[1];

        boolean isFake = false;
        Integer usesLeft = null;

        if(!options.isBlank()) {
            // fake option
            isFake = options.contains("-f");
            options = options.replaceAll("-f", "");
            
            // uses left option
            if(options.contains("-u")) {
                options = options.replaceAll("-u", "");

                // cause this is the last remaining value
                usesLeft = Integer.parseInt(options.trim());

                // out of range error
                if(usesLeft < 1 || usesLeft > 100) {
                    return false;
                }
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
        
        return true;
    }

    // classes that have an inventory
    Class<?>[] hasInventory = { Room.class, Student.class, Professor.class };

    /**
     * Gets the inventory of a variable with the given name.
     * @param inventoryName the name of the inventory holder
     * @return the inventory of the inventory holder, or <b>null</b> if the inventory holder does not exist
     */
    private Inventory getInventory(String inventoryName) {
        Entry<Class<?>, Object> inventoryHolder = findVariableMatching(hasInventory, inventoryName);
        return (Inventory) invoke(inventoryHolder, "getInventory").getKey();
    }

    /**
     * Handles the creation of an actor.
     * 
     * @param type the type of the actor
     * @param variableName the name of the variable (not empty, not taken - checked before)
     * @param data the remaining data (options without extra spaces)
     * @return true if the actor was created successfully, false otherwise
     */
    private boolean handleActorCreation(Class<? extends Actor> type, String variableName, String data) {
        String[] remaining = data.split(" ", 2);

        // get spawn-room
        String spawnRoom = remaining[0];
        Room room = (Room) findVariable(Room.class, spawnRoom).getValue();

        if(room == null) {
            return false;
        }

        // handling options
        String options = null;
        Integer inventorySize = null;
        
        if(remaining.length > 1 && remaining[1].contains("-i")) {
            options = remaining[1].replaceAll("-i", "");
            
            // cause this is the last remaining value
            inventorySize = Integer.parseInt(options.trim());   

            // out of range error
            if(inventorySize < 1 || inventorySize > 100 || type == Janitor.class) {
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
            Method addMethod = GameManager.class.getMethod("Add" + type.getSimpleName(), type);
            addMethod.invoke(GameManager.getInstance(), type.cast(actor));

            // adding the actor to variable list
            Interpreter.getInstance().AddVariable(variableName, actor);
        } catch (Exception e) {
            Interpreter.getInstance().removeVariable(variableName);
            return false;
        }
        
        if(inventorySize != null) {
            actor.getInventory().setSize(inventorySize);
        }

        return true;
    }
    
    private boolean handleRoomCreation(String variableName, String remaining) {
        Integer capacity = 10;

        // handle options
        if(remaining != null && !remaining.isBlank() && remaining.contains("-c")) {
            remaining = remaining.replaceAll("-c", "");
            capacity = Integer.parseInt(remaining.trim());

            // out of range error
            if(capacity < 1 || capacity > 100) {
                return false;
            }
        }

        Room room = new Room(capacity);
        GameManager.getInstance().AddRoom(room);
        Interpreter.getInstance().AddVariable(variableName, room);

        return true;
    }

    private boolean handleDoorCreation(String variableName, String remaining) {
        String[] data = remaining.split(" ", 3);

        if(data.length < 2) {
            return false;
        }

        // get rooms
        Room room1 = (Room) findVariable(Room.class, data[0]).getValue();
        Room room2 = (Room) findVariable(Room.class, data[1]).getValue();

        if(room1 == null || room2 == null) {
            return false;
        }

        // handle options
        boolean isOneWay = false;
        String options = data.length > 2 ? data[2] : "";

        if(!options.isBlank() && options.contains("-o")) {
            isOneWay = true;
            options = options.replaceAll("-o", "");
        }

        Door door = new Door(room1, room2, isOneWay);
        Interpreter.getInstance().AddVariable(variableName, door);

        return true;
    }
}
