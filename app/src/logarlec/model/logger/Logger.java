package logarlec.model.logger;

import java.lang.reflect.Method;
import java.lang.reflect.Field;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class Logger {
    private Logger() {
    }

    static int depth = 0;

    static Deque<String> remainingNames = new ArrayDeque<>();
    static Map<Object, String> instanceNames = new HashMap<>();

    public static InputStreamReader isr = new InputStreamReader(System.in);
    public static BufferedReader br = new BufferedReader(isr);

    // ------------------------------------------------
    // Instance names
    // ------------------------------------------------

    /**
     * Sets the names of the instances for the test
     * 
     * @param names
     */
    public static void setInstanceNames(String... names) {
        depth = 0;
        instanceNames.clear();
        remainingNames = new ArrayDeque<>(List.of(names));
    }

    /**
     * Logs the constructor call before it is executed
     * 
     * @param caller The object that was created
     * @param params The parameters of the constructor
     */
    public static void preConstructor(Object caller, Object... params) {
        StringBuilder sb = new StringBuilder();
        sb.append("\t".repeat(depth));
        sb.append(String.format("=> %s", caller.getClass().getSimpleName()));
        sb.append(getParams(params));

        System.out.println(sb.toString());
        instanceNames.put(caller, remainingNames.pop());

        depth++;
    }

    /**
     * Logs the constructor call after it is executed
     * 
     * @param caller The object that was created
     */
    public static void postConstructor(Object caller) {
        depth--;
        System.out.println("\t".repeat(depth) + "<= " + instanceNames.get(caller));
    }

    // ------------------------------------------------
    // Function calls
    // ------------------------------------------------

    /**
     * Logs the function call before it is executed
     * 
     * @param caller       The object that has the function
     * @param functionName The name of the function that was called
     * @param params       The parameters of the function being called
     */
    public static void preExecute(Object caller, String functionName, Object... params) {
        StringBuilder sb = new StringBuilder();
        sb.append("\t".repeat(depth));
        sb.append(String.format("=> %s.%s", instanceNames.get(caller), functionName));
        sb.append(getParams(params));

        System.out.println(sb.toString());
        depth++;

        setStateValues(caller, functionName);
    }

    /**
     * Logs the function call after it is executed
     * 
     * @param <T> the return type of the function
     * @param re  the return value of the function
     * @return the value that was returned
     */
    public static <T> T postExecute(T re) {
        depth--;

        if (re == null) {
            System.out.println("\t".repeat(depth) + "<= null");
            return re;
        }

        String name;
        if (instanceNames.containsKey(re)) {
            name = instanceNames.get(re);
        } else {
            name = re.toString();
        }

        System.out.println("\t".repeat(depth) + "<= " + name + " : " + re.getClass().getSimpleName());

        return re;
    }

    /**
     * Logs the function call after it is executed (for void functions)
     */
    public static void postExecute() {
        depth--;
        System.out.println("\t".repeat(depth) + "<= void");
    }

    // ------------------------------------------------
    // State values
    // ------------------------------------------------

    /**
     * Sets the state values of the called function
     * 
     * @param caller       The object that called the function
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

        // try to find the method in the parent class
        if (method == null && caller.getClass().getSuperclass() != null) {
            allMethods = caller.getClass().getSuperclass().getDeclaredMethods();

            for (Method m : allMethods) {
                if (m.getName().equals(functionName) && m.isAnnotationPresent(Uses.class)) {
                    method = m;
                    break;
                }
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
                // if we can't find it try for the parent class
                try {
                    if (caller.getClass().getSuperclass() != null) {
                        field = caller.getClass().getSuperclass().getDeclaredField(fieldName);
                        caller = caller.getClass().getSuperclass().cast(caller);
                    } else {
                        continue;
                    }
                } catch (Exception e2) {
                    // skip this if we can't find the field
                    continue;
                }
            }

            // skip this if it isn't a state varibale
            if (!field.isAnnotationPresent(State.class))
                continue;

            try {
                // checking if the value is null (it throws an error if it is)
                field.setAccessible(true);

                if (field.get(caller) == null) {
                    setFieldValue(caller, field);
                }
            } catch (Exception e) {
                // we can't modify it if the field is unaccesable
            }
        }
    }

    /**
     * Sets the value of the field
     * 
     * @param caller the object that contains the field
     * @param field  the field to be set
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
                System.out.println(line);

                // try to set the value
                Object value = parseInput(line, type, state.min(), state.max());

                if (value != null) {
                    field.set(caller, type.cast(value));
                    success = true;
                }
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
     * @param type   The type to be parsed to
     * @param min    The minimum value of the type
     * @param max    The maximum value of the type
     */
    private static Object parseInput(String string, Class<?> type, int min, int max) {
        String typeName = type.getName();

        // Try to convert to the supported types
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
                // if the type is not supported, try to cast it
                return type.cast(string);
            }
        } catch (Exception e) {
            return null;
        }
    }

    // ------------------------------------------------
    // String representations
    // ------------------------------------------------

    /**
     * Returns a string representation of the parameters
     * 
     * @param params The parameters to be represented
     */
    private static String getParams(Object... params) {
        StringBuilder sb = new StringBuilder();
        sb.append("(");

        for (int i = 0; i < params.length; i++) {
            String name = instanceNames.get(params[i]);

            if (params[i] == null) {
                sb.append("null");
            } else if (name != null) {
                sb.append(name + " : " + params[i].getClass().getSimpleName());
            } else {
                name = params[i].toString().replace("[", "").split("@")[0];
                sb.append(String.format("%s : %s", name, params[i].getClass().getSimpleName()));
            }

            sb.append(i == params.length - 1 ? "" : ", ");
        }

        sb.append(")");

        return sb.toString();
    }

    /**
     * Returns a string representation of the field
     * 
     * @param field The field to be represented
     * @param type  The type of the field
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

    /**
     * Closing the streams if the object is garbage collected
     */
    @Override
    protected void finalize() {
        try {
            br.close();
            isr.close();
        } catch (Exception e) {
            // we can't do anything if the stream is already closed
        }
    }
}
