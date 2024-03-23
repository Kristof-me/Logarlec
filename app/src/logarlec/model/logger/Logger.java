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

    Map<Object, String> instanceNames = new HashMap<>();
    Deque<Method> remainingNames = new ArrayDeque<>();
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


    public static void setStateValues(Object caller, String functionName) {
        Method method = null;
        Method[] allMethods = caller.getClass().getDeclaredMethods();

        for (Method m : allMethods) {
            if (m.getName().equals(functionName) && m.isAnnotationPresent(Uses.class)) {
                method = m;
                break;
            }
        }

        if (method == null) {
            return;
        }

        String[] usedFields = method.getAnnotation(Uses.class).fields();

        for (String field : usedFields) {
            Field current;

            try {
                current = caller.getClass().getDeclaredField(field);
            } catch (Exception e) {
                continue;
            }

            if (current != null && current.isAnnotationPresent(State.class)) {
                setFieldValue(caller, current);
            }
        }
    }

    private static void setFieldValue(Object caller, Field field) {
        State state = field.getAnnotation(State.class);
        Class<?> type = field.getType();

        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);

        field.setAccessible(true);
        boolean success = false;

        do {
            try {
                String constraints = type == Integer.class ? String.format("(%d - %d)", state.min(), state.max()) : "";
                System.out.print(String.format("%s : %s %s = ", state.name(), type.toString(), constraints));

                String line = br.readLine();
                field.set(caller, type.cast(parseInput(line, type)));
                success = true;
            } catch (Exception e) {
                success = false;
            }
        } while (!success);


        // the tester uses the stream so we must keep it open
    }

    private static Object parseInput(String string, Class<?> type) {
        String typeName = type.getName();

        try {
            if (typeName.equals(Integer.class.getName())) {
                return Integer.parseInt(string);
            } else if (typeName.equals(Double.class.getName())) {
                return Double.parseDouble(string);
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
}
