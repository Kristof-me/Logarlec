package logarlec.view.panels;

import javax.swing.*;


import java.awt.*;
public class EffectsPanel extends JPanel {
    private static EffectsPanel instance;
    public static EffectsPanel getInstance() {
        if (instance == null) {
            instance = new EffectsPanel();
        }
        return instance;
    }
    JScrollPane scrollPane;
    public EffectsPanel() {
        //create a new panel that will contain multiple effectpanels, and has a scrollpane if there are too many effects
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
    }

    public void addEffect(EffectPanel effectPanel){
        this.add(effectPanel);
        this.add(Box.createVerticalStrut(10)); // Add a 10 pixel gap
    }

    public void reset(){
        this.removeAll();
    }
    public void removeEffect(EffectPanel effectPanel){
        this.remove(effectPanel);
    }
}
