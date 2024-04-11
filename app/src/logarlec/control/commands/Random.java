package logarlec.control.commands;

import logarlec.control.GameManager;

public class Random extends Command {
    public boolean execute(String input) {
        switch (removeExtraSpace(input)) {
            case "on":
                GameManager.getInstance().setRandomness(true);
                return true;
                
            case "off":
                GameManager.getInstance().setRandomness(false);
                return true;
            default:
                return false;
        }
    }
}
