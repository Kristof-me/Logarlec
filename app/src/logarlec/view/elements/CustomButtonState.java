package logarlec.view.elements;

import javax.swing.DefaultButtonModel;

public class CustomButtonState extends DefaultButtonModel {
    @Override
    public boolean isPressed() {
        return false;
    }
    
    @Override
    public boolean isRollover() {
        return false;
    }
    
    @Override
    public void setRollover(boolean b) {
        // NOOP
    }
}