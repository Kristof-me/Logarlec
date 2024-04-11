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

    protected Entry<Class<?>, Object> findVariable(Class<?> typeName, String variableName) {
        return findVariableMatching(new Class<?>[] { typeName }, variableName);
    }

    protected Entry<Class<?>, Object> findVariableMatching(Class<?>[] acceptedTypes, String variableName) {
        Object var = Interpreter.getInstance().getVariable(variableName);

        if(var == null) {
            return null;
        }

        for (Class<?> type : acceptedTypes) {
            if(var.getClass() == type) {
                return Map.entry(type, var);
            }
        }

        return null;
    }

    protected Entry<Object, Boolean> invoke(Entry<Class<?>, Object> target, String function) {
        return invoke(target, function, null);
    }

    protected Entry<Object, Boolean> invoke(Entry<Class<?>, Object> target, String function, Class<?>[] paramTypes, Object... params) {
        try {
            Object result = target.getKey().getMethod(function, paramTypes).invoke(target, params);
            return Map.entry(result, true);
        } catch (Exception e) {
            return Map.entry(null, false);
        }
    }
}
