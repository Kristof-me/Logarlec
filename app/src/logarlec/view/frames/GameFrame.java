package logarlec.view.frames;
import javax.swing.*;

import logarlec.view.observerviews.View;
import logarlec.view.panels.PlayerPanel;

import java.awt.*;

public class GameFrame extends JFrame {
    public GameFrame() {
        super("Logarléc");
        this.setSize(800, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
    }
    static GameFrame instance;
    public static GameFrame getInstance() {
        if (instance == null) {
            instance = new GameFrame();
        }
        return instance;
    }

    private View playerPanel;
    public void setPlayerPanel(View playerPanel) {
        if (this.playerPanel != null) {
            this.remove(this.playerPanel);
        }
        this.playerPanel = playerPanel;
        this.add(playerPanel, BorderLayout.SOUTH);
        this.revalidate();
        this.repaint();
    }

}
