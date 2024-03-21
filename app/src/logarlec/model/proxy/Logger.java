package logarlec.model.proxy;


public class Logger {
    static int depth = 0;

    public static void Reset() {
        depth = 0;
    }

    public static void preExecute(Object caller, String functionName, Object... params) {
        for (int i = 0; i < depth; i++) {
            System.out.print("\t");
        }
        System.out.print("=>" + caller.toString() + "." + functionName);
        // Print parameters in form (value1 : type1, value2 : type2, ...)
        System.out.print("(");
        for (int i = 0; i < params.length; i++) {
            System.out.print(params[i].toString() + " : " + params[i].getClass().getSimpleName());
            if (i != params.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println(")");
        depth++;
    }

    public static <T> T postExecute(T re) {
        depth--;
        for (int i = 0; i < depth; i++) {
            System.out.print("\t");
        }
        System.out.println("<=" + re.toString() + " : " + re.getClass().getSimpleName());
        return re;
    }

    public static void postExecute() {
        depth--;
        for (int i = 0; i < depth; i++) {
            System.out.print("\t");
        }
        System.out.println("<=" + "void");
    }
}
