package logarlec.view.utility;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.BorderFactory;
import java.io.File;
import java.util.List;
import java.util.Arrays;

import logarlec.view.elements.ScrollUI;


public class ThemeManager {
    private static ThemeManager instance;

    // COLORS
    public static final Color PRIMARY = new Color(242, 71, 38);
    public static final Color PRIMARY_LIGHT = colorFrom("#F00");
    public static final Color ACCENT = new Color(12, 167, 137);

    public static final Color BACKGROUND = colorFrom("#808080");
    public static final Color BACKGROUND_DARK = new Color(105, 105, 105);
    
    public static final Color SCROLL = colorFrom("#FFF");
    public static final Color TRACK = colorFrom("#434343");
    
    public static final Color TEXT = colorFrom("#FFF");
    public static final Color TEXT_DARK = colorFrom("#000");
    public static final Color BUTTON = colorFrom("#1A1A1A");

    Font FONT;
    final int FONT_SIZE = 14;

    private ThemeManager() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        List<String> takenNames = Arrays.asList(ge.getAvailableFontFamilyNames());

        // load font
        File fontFile = new File("app/src/resources/Roboto-Regular.ttf");

        if (fontFile.exists()) {
            try {
                FONT = Font.createFont(Font.TRUETYPE_FONT, fontFile);
                if(!takenNames.contains(FONT.getFontName())) {
                    ge.registerFont(FONT);
                }
            } catch (Exception e) {
                System.err.println("Error loading font: " + e.getMessage());
            }
        } else {
            FONT = ge.getAllFonts()[0];
            System.err.println("Font file not found");
        }
    }

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
    public static Color colorFrom(String hex) {
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
        int BUTTON_BORDER = 3;

        UIManager.put("Button.background", BUTTON);
        UIManager.put("Button.foreground", TEXT);
        UIManager.put("Button.border", BorderFactory.createLineBorder(BUTTON, BUTTON_BORDER));
        UIManager.put("Button.font", getFont(FONT_SIZE));
    }
    
    private void setLabelStyle() {
        UIManager.put("Label.foreground", TEXT);
        UIManager.put("Label.font", getFont(FONT_SIZE));
        UIManager.put("Label.opaque", true);
        UIManager.put("Label.disabledShadow", true);
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
        UIManager.put("TextField.background", TEXT);
        UIManager.put("TextField.foreground", TEXT_DARK);
        UIManager.put("TextField.font", getFont(FONT_SIZE));
        UIManager.put("TextField.border", BorderFactory.createEmptyBorder());
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
        return FONT.deriveFont(style, fontSize);
    }

    public Font getFont(int fontSize) {
        return getFont(Font.PLAIN, fontSize);
    }
}
