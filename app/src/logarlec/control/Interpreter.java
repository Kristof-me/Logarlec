package logarlec.control;

import java.util.HashMap;

import logarlec.control.commands.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Interpreter {
    // singleton
    static Interpreter instance = null;

    public static Interpreter getInstance() {
        if (instance == null) {
            instance = new Interpreter();
        }

        return instance;
    }
    
    // user generated content
    HashMap<String, Object> variables = new HashMap<String, Object>();
    HashMap<String, Command> commands = new HashMap<String, Command>();

    public HashMap<String, Object> getVariables() {
        return variables;
    }

    protected Interpreter() {
        // ....
        commands.put("load", new Load());
        commands.put("random", new Random());
        commands.put("reset", new Reset());
    }

    public void handleInput(InputStream is) {
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader bufferedReader = new BufferedReader(isr);
        
        try (isr; bufferedReader) {
            String line;

            // read until EOF
            while((line = bufferedReader.readLine()) != null) { 
                String[] tokens = line.split(" ", 2);

                String command = tokens[0].trim().toLowerCase();
                Command action = commands.get(command);

                // if there is no such command
                if (action == null) {
                    handleInvalid();
                    continue;
                }

                // get the remaining part of the command
                String remaining = "";
                if (tokens.length > 1) {
                    remaining = tokens[1];
                }

                // try to execute it
                if (action.execute(remaining) == false) { 
                    handleInvalid();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reset() {
        variables.clear();
    }

    private void handleInvalid() {
        System.out.println("Invalid command");
    }
}
