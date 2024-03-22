package logarlec.model.proxy;

import java.lang.reflect.Method;
import java.util.Deque;


public class Logger {
    private Logger() {}

    static int depth = 0;

    Deque<Method> callStack;

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
     * Returns a string representation of the parameters
     * 
     * @param params The parameters to be represented
     */
    private static String getParams(Object... params) {
        StringBuilder sb = new StringBuilder();
        sb.append("(");

        for (int i = 0; i < params.length; i++) {
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
