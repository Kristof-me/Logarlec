package logarlec.view.utility;

import java.awt.Font;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import javax.swing.UIManager;

import javax.swing.plaf.ColorUIResource;

public class UiManager {
    private static UiManager instance;

    private UiManager() {
    }

    public static UiManager getInstance() {
        if (instance == null) {
            instance = new UiManager();
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

    public void loadTheme() {
        String path = getClass().getResource("../../../resources/theme.csv").getPath();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");

                UIManager.getDefaults().addPropertyChangeListener((e) -> {
                    System.out.println(e.getPropertyName() + " " + e.getNewValue());
                });

                if (values.length != 2) continue;

                // trimming the components
                values[0] = values[0].trim();
                values[1] = values[1].trim();

                if(values[1].startsWith("#")) {
                    UIManager.put(values[0].trim(), new ColorUIResource(colorFrom(values[1])));
                } else {
                    UIManager.put(values[0].trim(), values[1].trim());
                }
                
            }
        } catch (Exception e) {
            System.err.println("Failed to load theme");
        }
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
