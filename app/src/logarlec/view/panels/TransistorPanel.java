package logarlec.view.panels;

import java.awt.Color;
import java.awt.Graphics;

import logarlec.model.items.impl.Transistor;
import logarlec.view.utility.ColorGenerator;

public class TransistorPanel extends ItemPanel<Transistor> {
    public TransistorPanel(Transistor item, String icon) {
        super(item, icon);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (item.getPair() != null) {
            Color c = ColorGenerator.getInstance().fromHash(item.hashCode(), item.getPair().hashCode());
            this.setBackground(c);
            iconLabel.setBackground(c);
            iconLabel.setOpaque(true);
            iconLabel.setForeground(ColorGenerator.getInstance().getForegroundColor(c));
            
            // g.setColor(c);
            // //fill bottom right corner
            // g.fillOval(getWidth() - 12, getHeight() - 17, 13, 13);
            // //draw white border for the oval
            // g.setColor(Color.WHITE);
            // g.drawOval(getWidth() - 12, getHeight() - 17, 13, 13);
            
            usesLeft.setForeground(ColorGenerator.getInstance().getForegroundColor(c));
        }
    }

    @Override
    public void updateView() {
        super.updateView();
        revalidate();
        repaint();
    }
}