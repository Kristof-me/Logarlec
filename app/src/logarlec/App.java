
package logarlec;

import logarlec.control.Interpreter;

public class App {
    public static void main(String[] args) {
        Interpreter.getInstance().handleInput(System.in);
    }
}
