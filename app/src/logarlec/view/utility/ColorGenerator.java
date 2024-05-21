package logarlec.view.utility;

import logarlec.App;
import java.awt.Color;
import java.util.*;
/**
 * A utility class for generating colors, singleton
 */
public class ColorGenerator {
    /**
     * The random number generator used to generate random colors
     */
    private static Random random = new Random();
    /**
     * The singleton instance of the ColorGenerator
     */
    static private ColorGenerator instance;
    /**
     * Creates a new ColorGenerator
     */
    private ColorGenerator() {}
    /**
     * Returns the singleton instance of the ColorGenerator
     * @return The singleton instance of the ColorGenerator
     */
    public static ColorGenerator getInstance() {
        if (instance == null) {
            instance = new ColorGenerator();
        }
        return instance;
    }
    /**
     * Generates a random color
     * @return A random color
     */
    public Color random() {
        return new Color(random.nextInt(0x1000000));
    }
    /**
     * Generates a color from two hash codes deterministically
     * @param hash1 The first hash code
     * @param hash2 The second hash code
     * @return A color based on the two hash codes, it returns the same color for the same two hash codes, no matter the order
     */
    public Color fromHash(int hash1, int hash2) {
        // so the order is always the same
        if(hash1 > hash2) {
            int temp = hash1;
            hash1 = hash2;
            hash2 = temp;
        }
        
        return new Color((hash1 + hash2) % 0x1000000);
    }
    /**
     * Returns the foreground color for a given background color based on the luminance
     * @param backgroundColor The background color
     * @return The foreground color, either black or white
     */
    public Color getForegroundColor(Color backgroundColor) {
        double luminance = 0.299 * backgroundColor.getRed() + 0.587 * backgroundColor.getGreen() + 0.114 * backgroundColor.getBlue();
    
        // If the color is dark, return white. Otherwise, return black.
        return luminance < 128 ? Color.WHITE : Color.BLACK;
    }
}
