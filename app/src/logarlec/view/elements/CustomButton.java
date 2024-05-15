package logarlec.view.elements;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.function.Consumer;


import javax.swing.JButton;
import javax.swing.Icon;
import java.awt.Color;

/**
 * A button with a custom style.
 */
public class CustomButton extends JButton {
    private transient Consumer<ActionEvent> action;
    private Color background;
    private Color highlightColor;
    private boolean hasAnimation = true;

    public CustomButton(Icon icon, String text, Consumer<ActionEvent> action) {
        super(text, icon);

        setModel(new CustomButtonState());
        setFocusPainted(false);

        this.addActionListener(this::onClickAnimation);
        this.action = action;

        this.background = getBackground();
        this.highlightColor = getBackground().darker();
    }

    public CustomButton(Icon icon, Consumer<ActionEvent> action) {
        this(icon, "", action);
    }

    /**
     * Creates a button with the given text, action and style.
     * 
     * @param text - the text to display on the button.
     * @param action - the action to perform when the button is clicked.
     * @param style - the style to apply to the button.
     * @see Color
     */
    public CustomButton(String text, Consumer<ActionEvent> action) {
        this(null, text, action);
    }

    /**
     * Creates a button with the given text and action.
     * 
     * @param text - the text to display on the button.
     */
    public CustomButton(String text) {
        this(text, null);
    }

    /**
     * Sets all the sizes to the same value. (to support most layouts)
     */
    public void setExpectedSize(Dimension size) {
        setSize(size);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
    }

    /**
     * Sets the background, waits 100 ms then changes it back to the default color.
     * 
     * @param event - the event that triggered the action.
     */
    private void onClickAnimation(ActionEvent event) {
        setHighlight(true);

        if (!hasAnimation) {
            if (action != null) {
                action.accept(event);
            }

            return;
        }

        // wait 100 ms then change the background back to the default color
        new Thread(() -> {
            try {
                Thread.sleep(100);
                setHighlight(false);

                if (action != null) {
                    action.accept(event);
                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();

                setHighlight(false);

                if (action != null) {
                    action.accept(event);
                }
            }
        }).start();
    }

    /**
     * Sets the background and foreground colors of the button.
     * 
     * @param highlight - whether to use the default colors or the highlight colors.
     */
    public void setHighlight(boolean highlight) {        
        super.setBackground(highlight ? highlightColor : background);
    }

    /**
     * Sets whether the button should have an animation when clicked.
     * 
     * @param hasAnimation - true if the button should have an animation when clicked.
     */
    public void setHasAnimation(boolean hasAnimation) {
        this.hasAnimation = hasAnimation;
    }

    /**
     * Sets the background color of the button.
     */
    @Override
    public void setBackground(Color color) {
        super.setBackground(color);
        this.background = color;
    }

    /**
     * Sets the highlight color of the button.
     */
    public void setHighlightColor(Color color) {
        this.highlightColor = color;  
    }
}

