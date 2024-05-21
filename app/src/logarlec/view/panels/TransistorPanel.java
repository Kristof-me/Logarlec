package logarlec.view.panels;

import java.awt.Color;
import java.awt.Graphics;

import logarlec.model.items.impl.Transistor;
import logarlec.view.utility.ColorGenerator;
/**
 * An Item Panel for a Transistor, which has an additional color for the pair indicator
 */
public class TransistorPanel extends ItemPanel<Transistor> {
    /**
     * Creates a new transistor panel
     * @param item The transistor to display
     * @param icon The icon to display next to the transistor
     */
    public TransistorPanel(Transistor item, String icon) {
        super(item, icon);
    }
    /**
     * Paints the component, setting the background color to the color of the pair
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (item.getPair() != null) {
            Color c = ColorGenerator.getInstance().fromHash(item.hashCode(), item.getPair().hashCode());
            this.setBackground(c);
            iconLabel.setBackground(c);
            iconLabel.setOpaque(true);
            iconLabel.setForeground(ColorGenerator.getInstance().getForegroundColor(c));
            
            usesLeft.setForeground(ColorGenerator.getInstance().getForegroundColor(c));
        }
    }
    /**
     * Updates the view to reflect the current state of the transistor, setting the number of uses left
     */
    @Override
    public void updateView() {
        super.updateView();
        revalidate();
        repaint();
    }
}
