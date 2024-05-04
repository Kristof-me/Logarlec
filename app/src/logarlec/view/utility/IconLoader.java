package logarlec.view.utility;

import java.util.HashMap;
import javax.swing.ImageIcon;

public class IconLoader {
    HashMap<String, ImageIcon> icons = new HashMap<String, ImageIcon>();

    private static IconLoader instance;
    private String path;

    private IconLoader() {
        path = getClass().getResource("../../../resoutces/icons/").getPath();
        System.out.println(path);
    }

    public static IconLoader getInstance() {
        if (instance == null) {
            instance = new IconLoader();
        }
        return instance;
    }

    public ImageIcon getImage(String name) {
        if (icons.containsKey(name)) {
            return icons.get(name);
        }

        ImageIcon icon = new ImageIcon(path + name);
        icons.put(name, icon);
        return icon;
    }
    
}
