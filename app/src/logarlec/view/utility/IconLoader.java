package logarlec.view.utility;

import java.util.HashMap;
import java.awt.Image;
import javax.swing.ImageIcon;

public class IconLoader {
    HashMap<String, Image> icons = new HashMap<String, Image>();

    private static IconLoader instance;
    private String path;

    private IconLoader() {
        path = getClass().getResource("../../../resources/icons/").getPath();
    }

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
