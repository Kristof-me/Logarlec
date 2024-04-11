package logarlec.control.commands;

import java.util.Map.Entry;
import java.util.HashMap;
import java.util.Map;
import logarlec.control.Interpreter;

public abstract class Command {
    public abstract boolean execute(String input);
    
    // TODO handle argument, options
    
    HashMap<String, String> longOptions;
    protected String toShortOptions(String input) {
        for (String option : longOptions.keySet()) {
            input.replace("--"+option, "-"+longOptions.get(option));
        }

        return input;
    }

    protected String removeExtraSpace(String data) {
        String previous = "";
        String result = data;

        do {
            previous = result;
            result = result.replaceAll("  ", " ");
        } while(!previous.equals(result));

        return result;
    }

    protected Entry<Class<?>, Object> matchedType(Class<?> typeName, String variableName) {
        return matchedTypes(new Class<?>[] { typeName }, variableName);
    }

    protected Entry<Class<?>, Object> matchedTypes(Class<?>[] types, String variableName) {
        Object var = Interpreter.getInstance().getVariables().get(variableName);

        if(var == null) {
            return null;
        }

        for (Class<?> type : types) {
            if(var.getClass() == type) {
                return Map.entry(type, var);
            }
        }

        return null;
    }
}
