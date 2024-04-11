package logarlec.control.commands;

import java.util.HashMap;
import java.util.Map.Entry;

import logarlec.model.room.*;
import logarlec.control.Interpreter;
import logarlec.model.actor.*;
import logarlec.model.items.Inventory;
import logarlec.model.items.Item;
import logarlec.model.items.impl.*;

public class Create extends Command {
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
        if(variableName.isBlank() || Interpreter.getInstance().getVariable(variableName) != null) {
            return false;
        }

        String remaining = data.length > 2 ? data[2] : "";

        if(type.getNestHost() == Item.class) { // handle items
            return handleItemCreation(type, variableName, remaining);
        }

        return false;
    }

    public Create() {
        longOptions = new HashMap<>();

        longOptions.put("one-way", "o");
        longOptions.put("fake", "f");
    }

    HashMap<String, Class<?>> classes = new HashMap<>() {{
        put("room", Room.class);
        put("professor", Professor.class);
        put("student", Student.class);
        put("janitor", Janitor.class);
        put("door", Door.class);
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
            options.replaceAll("-f", "");
            
            // uses left option
            if(options.contains("-u")) {
                options.replaceAll("-u", "");

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

    Class<?>[] hasInventory = { Room.class, Student.class, Professor.class };

    private Inventory getInventory(String inventoryName) {
        Entry<Class<?>, Object> inventoryHolder = findVariableMatching(hasInventory, inventoryName);
        return (Inventory) invoke(inventoryHolder, "getInventory").getKey();
    }
}
