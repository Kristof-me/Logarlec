package logarlec.view.elements;

import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.plaf.basic.BasicScrollBarUI;

/**
 * A custom scroll bar
 */
public class ScrollUI extends BasicScrollBarUI {
    @Override
    protected JButton createDecreaseButton(int orientation) {
        return createZeroButton();
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        return createZeroButton();
    }

    /**
     * Creates a button with no size
     * 
     * @returns a new empty button
     */
    private JButton createZeroButton() {
        JButton jbutton = new JButton();
        Dimension size = new Dimension(0, 0);

        jbutton.setPreferredSize(size);
        jbutton.setMinimumSize(size);
        jbutton.setMaximumSize(size);

        return jbutton;
    }
}
