package logarlec.control.commands;

import logarlec.control.GameManager;
import logarlec.control.Interpreter;

public class Reset extends Command {
    public boolean execute(String input) {
        if(!input.isBlank()) {
            return false;
        }
        
        GameManager.getInstance().reset();
        Interpreter.getInstance().reset();
        return true;
    }
    
}
