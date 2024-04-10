package logarlec.control.commands;

import logarlec.control.GameManager;
import logarlec.control.Interpreter;

public class Reset implements Command {
    public boolean execute(String input) {
        GameManager.getInstance().reset();
        Interpreter.getInstance().reset();
        return true;
    }
    
}
