package logarlec.control.commands;

import java.util.Map.Entry;
import java.util.HashMap;
import java.util.Map;
import java.lang.reflect.Method;
import logarlec.control.Interpreter;

public abstract class Command {
    public abstract boolean execute(String input);
    
    public Integer tryParse(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    HashMap<String, String> longOptions;
    protected String toShortOptions(String input) {
        for (String option : longOptions.keySet()) {
            input = input.replace("--"+option, "-"+longOptions.get(option));
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

        return result.trim();
    }

    protected boolean isNameTaken(String variableName) {
        return Interpreter.getInstance().getVariable(variableName) != null;
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
            if(var.getClass() == type || var.getClass().getSuperclass() == type) {
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
            Method method;
            if(paramTypes == null) {
                method = target.getKey().getMethod(function);
            } else {
                method = target.getKey().getMethod(function, paramTypes);
            }
            
            Object result = method.invoke(target.getValue(), params);
            
            if(result == null) {
                return Map.entry("", false);
            } else {
                return Map.entry(result, true);
            }
        } catch (Exception e) {
            return Map.entry(null, false);
        }
    }
}