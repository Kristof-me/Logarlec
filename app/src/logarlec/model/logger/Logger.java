package logarlec.model.logger;

import java.lang.reflect.Method;
import java.lang.reflect.Field;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class Logger {
    private Logger() {}

    static int depth = 0;

    static Map<Object, String> instanceNames = new HashMap<>();
    static Deque<Method> remainingNames = new ArrayDeque<>();

    static InputStreamReader isr = new InputStreamReader(System.in);
    static BufferedReader br = new BufferedReader(isr);

    // Deque<Method> calls = new ArrayDeque<>();

    // for constructors
    public static void preExecute(Object caller, Object... params) {
        StringBuilder sb = new StringBuilder();
        sb.append("\t".repeat(depth));
        sb.append(String.format("=> %s", caller.getClass().getSimpleName()));
        sb.append(getParams(params));

        System.out.println(sb.toString());
        depth++;
    }

    public static void preExecute(Object caller, String functionName, Object... params) {
        StringBuilder sb = new StringBuilder();
        sb.append("\t".repeat(depth));
        sb.append(String.format("=> %s.%s", getCallerName(caller), functionName));
        sb.append(getParams(params));

        System.out.println(sb.toString());
        depth++;

        setStateValues(caller, functionName);
    }

    public static <T> T postExecute(T re) {
        depth--;
        System.out.println("\t".repeat(depth) + "<=" + re.toString() + " : " + re.getClass().getSimpleName());

        return re;
    }

    public static void postExecute() {
        depth--;
        System.out.println("\t".repeat(depth) + "<= void");
    }

    /**
     * Sets the state values of the called function
     * 
     * @param caller The object that called the function
     * @param functionName The name of the function that was called
     */
    public static void setStateValues(Object caller, String functionName) {
        Method method = null;
        Method[] allMethods = caller.getClass().getDeclaredMethods();

        // finding the method that was called
        for (Method m : allMethods) {
            if (m.getName().equals(functionName) && m.isAnnotationPresent(Uses.class)) {
                method = m;
                break;
            }
        }

        // if the method doesn't exist or doesn't have the Uses annotation
        if (method == null) {
            return;
        }

        String[] fieldNames = method.getAnnotation(Uses.class).fields();

        // setting the state values
        for (String fieldName : fieldNames) {
            Field field;

            try {
                field = caller.getClass().getDeclaredField(fieldName);
            } catch (Exception e) {
                // skip this if we can't find the field
                continue;
            }

            // skip this if it isn't a state varibale
            if (!field.isAnnotationPresent(State.class))
                continue;

            try {
                // checking if the value is null (it throws an error if it is)
                Object value = field.get(caller);
            } catch (NullPointerException exception) {
                // if it's null set the value
                field.setAccessible(true);
                setFieldValue(caller, field);
            } catch (Exception e) {
                // we can't modify it if the field is unaccesable
            }

        }
    }

    /**
     * Sets the value of the field
     * 
     * @param caller the object that contains the field
     * @param field the field to be set
     */
    private static void setFieldValue(Object caller, Field field) {
        Class<?> type = field.getType();
        State state = field.getAnnotation(State.class);
        boolean success = false;

        do {
            try {
                // ask for a value
                System.out.print(fieldToString(field, type, state));
                String line = br.readLine();

                // try to set the value
                field.set(caller, type.cast(parseInput(line, type, state.min(), state.max())));
                success = true;
            } catch (Exception e) {
                // retry if the value is not assignable
            }
        } while (!success);

        // the tester uses the stream so we must keep it open
    }

    /**
     * Parses the input string to the given type
     * 
     * @param string The string to be parsed
     * @param type The type to be parsed to
     * @param min The minimum value of the type
     * @param max The maximum value of the type
     */
    private static Object parseInput(String string, Class<?> type, int min, int max) {
        String typeName = type.getName();

        try {
            if (typeName.equals(Integer.class.getName())) {
                Integer value = Integer.parseInt(string);

                if (min < max) {
                    return (min <= value && value <= max) ? value : null;
                }

                return value;
            } else if (typeName.equals(Double.class.getName())) {
                Double value = Double.parseDouble(string);

                if (min < max) {
                    return (min <= value && value <= max) ? value : null;
                }

                return value;
            } else if (typeName.equals(Boolean.class.getName())) {
                return Boolean.parseBoolean(string);
            } else if (typeName.equals(String.class.getName())) {
                return string;
            } else {
                return type.cast(string);
            }

        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Returns a string representation of the parameters
     * 
     * @param params The parameters to be represented
     */
    private static String getParams(Object... params) {
        StringBuilder sb = new StringBuilder();
        sb.append("(");

        for (int i = 0; i < params.length; i++) {
            if (params[i] == null) {
                sb.append("null");
                continue;
            }

            sb.append(String.format("%s : %s", params[i].toString(), params[i].getClass().getSimpleName()));
            sb.append(i == params.length - 1 ? "" : ", ");
        }

        sb.append(")");

        return sb.toString();
    }

    /**
     * Returns the name of the caller
     * 
     * @param caller The caller object
     */
    private static String getCallerName(Object caller) {
        if (caller.getClass().isAnnotationPresent(InstanceName.class))
            return caller.getClass().getAnnotation(InstanceName.class).value();

        return caller.toString();
    }

    /**
     * Returns a string representation of the field
     * 
     * @param field The field to be represented
     * @param type The type of the field
     * @param state The state annotation of the field
     */
    private static String fieldToString(Field field, Class<?> type, State state) {
        String constraints = "";

        if ((type == Integer.class || type == Double.class) && state.min() < state.max()) {
            constraints = String.format("(%d - %d)", state.min(), state.max());
        } else if (type == Boolean.class) {
            constraints = "(true/false)";
        }
        return String.format("%s : %s %s = ", state.name(), field.getType().getSimpleName(), constraints);
    }
}
