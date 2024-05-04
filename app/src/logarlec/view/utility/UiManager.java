package logarlec.view.utility;

import java.awt.Font;

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

    public void loadTheme() {
        // todo
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
