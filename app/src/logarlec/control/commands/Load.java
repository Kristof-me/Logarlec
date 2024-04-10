package logarlec.control.commands;

import java.io.FileInputStream;
import logarlec.control.Interpreter;

public class Load implements Command {
    public boolean execute(String input) {
        // load the file
        try (FileInputStream file = new FileInputStream(input.trim())) {
            // let the interpreter handle the file
            Interpreter.getInstance().handleInput(file);
            return true;
        } catch (Exception e) {
            return false;
        }
    }    
}
