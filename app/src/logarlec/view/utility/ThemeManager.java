package logarlec.view.utility;

import java.awt.Font;
import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.BorderFactory;

import logarlec.view.elements.ScrollUI;


public class ThemeManager {
    private static ThemeManager instance;

    // COLORS
    // todo setup properly
    Color PRIMARY = colorFrom("#F00");
    Color PRIMARY_LIGHT = colorFrom("#F00");

    Color BACKGROUND = colorFrom("#545454");
    
    Color SCROLL = colorFrom("#AAA");
    Color TRACK = colorFrom("#777");
    
    Color TEXT = colorFrom("#EEE");
    Color BUTTON = PRIMARY;

    Color BORDER_COLOR = colorFrom("#000000");

    private ThemeManager() {}

    public static ThemeManager getInstance() {
        if (instance == null) {
            instance = new ThemeManager();
        }
        return instance;
    }

    /**
     * Creates a color from a string (supports alpha)
     * 
     * @param hex - the hex string (starts with # and has 3 to 8 digits for rgb and rgba)
     * @return the color, NULL if the string is invalid
     */
    private Color colorFrom(String hex) {
        int[] rgba = {0, 0, 0, 255};

        if (hex.isEmpty() || hex.charAt(0) != '#' || hex.length() < 4 || hex.length() > 9) {
            return null;
        }

        // removing the #
        hex = hex.substring(1, hex.length());
        int length = hex.length();

        int digits = length > 4 ? 2 : 1; // number of digits per component
        Double maxComponents = Math.ceil((double) length / digits);
        int components = maxComponents.intValue(); // number of components

        for (int i = 0; i < components; i++) {
            int start = i * digits;
            int end = Math.min(hex.length(), start + digits);

            // try to parse this color component
            try {
                rgba[i] = Integer.parseInt(hex.substring(start, end), 16);

                if (end - start == 1) {
                    rgba[i] *= 17; // 0x0f -> 0xff (mod 16 operation)
                }
            } catch (NumberFormatException err) {
                throw new NumberFormatException(err.getMessage());
            }
        }

        return new Color(rgba[0], rgba[1], rgba[2], rgba[3]);
    }

    private void setContainerStyle() {
        UIManager.put("InternalFrame.background", BACKGROUND);
        UIManager.put("PopupMenu.background", BACKGROUND);
        UIManager.put("Panel.background", BACKGROUND);
        UIManager.put("Viewport.background", BACKGROUND);
    }
    
    private void setButtonStyle() {
        UIManager.put("Button.background", BUTTON);
        UIManager.put("Button.foreground", TEXT);
        UIManager.put("Button.border", BorderFactory.createEmptyBorder());

        //Border border = BorderFactory.createLineBorder(BUTTON, 2);
        //UIManager.put("ButtonUI", "logarlec.view.elements.ButtonUI");
    }
    
    private void setLabelStyle() {
        // TODO
    }
    
    private void setScrollStyle() {    
        UIManager.put("ScrollBar.track", TRACK);
        
        UIManager.put("ScrollBar.thumb", SCROLL);
        UIManager.put("ScrollBar.thumbDarkShadow", SCROLL);
        UIManager.put("ScrollBar.thumbShadow", SCROLL);
        UIManager.put("ScrollBar.thumbHighlight", SCROLL);
        
        UIManager.put("ScrollBarUI", ScrollUI.class.getName());
    }

    private void setTextFieldStyle() {
        // TODO
    }

    public void loadTheme() {
        // see more at https://gist.github.com/ezhov-da/357f80b3182cce3ece19d47935d377b8
        setContainerStyle();
        setButtonStyle();
        setLabelStyle();
        setScrollStyle();
        setTextFieldStyle();
    }

    
    public Font getFont(int style, int fontSize) {
        // return new Font();
        // TODO
        return null;
    }

    public Font getFont(int fontSize) {
        return getFont(Font.PLAIN, fontSize);
    }
}
