package logarlec.view.utility;

import java.awt.Color;

public class ColorGenerator {
    static private ColorGenerator instance;
    
    private ColorGenerator() {}

    public static ColorGenerator getInstance() {
        if (instance == null) {
            instance = new ColorGenerator();
        }
        return instance;
    }

    public Color random() {
        return new Color((int)(Math.random() * 0x1000000));
    }

    public Color fromHash(int hash1, int hash2) {
        // so the order is always the same
        if(hash1 > hash2) {
            int temp = hash1;
            hash1 = hash2;
            hash2 = temp;
        }
        
        return new Color((hash1 + hash2) % 0x1000000);
    }
    public Color getForegroundColor(Color backgroundColor) {
        double luminance = 0.299 * backgroundColor.getRed() + 0.587 * backgroundColor.getGreen() + 0.114 * backgroundColor.getBlue();
    
        // If the color is dark, return white. Otherwise, return black.
        return luminance < 128 ? Color.WHITE : Color.BLACK;
    }
}
