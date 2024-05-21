package logarlec.view.utility;

import java.util.HashMap;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 * A utility class for loading icons, singleton which caches the icons
 */
public class IconLoader {
    /**
     * A map of icons that have already been loaded
     */
    HashMap<String, Image> icons = new HashMap<String, Image>();
    /**
     * The singleton instance of the icon loader
     */
    private static IconLoader instance;
    /**
     * The base path to the icons
     */
    private String path;
    /**
     * Creates a new icon loader, setting the path
     */
    private IconLoader() {
        path = getClass().getResource("../../../resources/icons/").getPath();
    }
    /**
     * Returns the singleton instance of the IconLoader
     * @return The singleton instance of the IconLoader
     */
    public static IconLoader getInstance() {
        if (instance == null) {
            instance = new IconLoader();
        }
        return instance;
    }

    /**
     * Get an image from the `/resources/icons/` folder
     * @param name The name of the image
     * @param scale The scale of the image in pixels
     */
    public Image getImage(String name, int scale) {
        if (icons.containsKey(name)) {
            return icons.get(name).getScaledInstance(scale, scale, Image.SCALE_SMOOTH);
        }
        
        Image image = new ImageIcon(path + name).getImage();
        icons.put(name, image);
        return image.getScaledInstance(scale, scale, Image.SCALE_SMOOTH);
    }

    /**
     * Get an image from the `/resources/icons/` folder
     * @param name The name of the image
     * @param scale The scale of the image in pixels
     */
    public ImageIcon getIcon(String name, int scale) {
        return new ImageIcon(getImage(name, scale));
    }
}
