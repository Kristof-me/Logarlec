
package logarlec;

import logarlec.view.frames.MenuFrame;

public class App {
    public static void main(String[] args) {
        MenuFrame menuFrame = new MenuFrame();
        menuFrame.setVisible(true);
    }

    // ? lehet SRP miatt ezt nem ide kéne, de egy 1 függvényes osztályt nem akartam csinálni
    private static void loadTheme() {

    }
}
