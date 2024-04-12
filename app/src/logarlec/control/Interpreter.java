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
    HashMap<String, Command> commands = new HashMap<String, Command>() {{
        put("create", new Create());
        // ...
        put("load", new Load());
        put("random", new Random());
        put("reset", new Reset());
    }};

    public Object getVariable(String name) {
        return variables.get(name);
    }

    public boolean AddVariable(String name, Object item) {
        return variables.put(name, item) == null;
    }

    public void removeVariable(String variableName) {
        variables.remove(variableName);
    }

    protected Interpreter() {}

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
                    if(!command.isBlank()) {
                        handleInvalid();
                    }
                    
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
