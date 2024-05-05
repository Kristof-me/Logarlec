package logarlec.view.panels;

import javax.swing.*;

public class EffectListPanel extends JPanel {
    private static EffectListPanel instance;

    JScrollPane scrollPane;
    public static EffectListPanel getInstance() {
        if (instance == null) {
            instance = new EffectListPanel();
        }

        return instance;
    }

    public EffectListPanel() {
        //create a new panel that will contain multiple effectpanels, and has a scrollpane if there are too many effects
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));   
    }

    public void addEffect(EffectPanel effectPanel){
        this.add(effectPanel);
        this.add(Box.createVerticalStrut(10)); // Add a 10 pixel gap
        redraw();
    }
    
    public void removeEffect(EffectPanel effectPanel){
        this.remove(effectPanel);
        redraw();
    }

    public void reset(){
        this.removeAll();
        redraw();
    }

    protected void redraw() {
        revalidate();
        repaint();
    }
}
