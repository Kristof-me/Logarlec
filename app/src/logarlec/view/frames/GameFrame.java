package logarlec.view.frames;
import javax.swing.*;

import logarlec.view.elements.ScrollUI;
import logarlec.view.panels.EffectListPanel;
import logarlec.view.panels.PlayerPanel;

import java.awt.*;

public class GameFrame extends JFrame {
    static GameFrame instance;
    private EffectListPanel effectListPanel;

    protected GameFrame() {
        super("Logarléc");
        this.setSize(800, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        
        JLabel roomLabel = new JLabel("Room Name");
        roomLabel.setPreferredSize(new Dimension(150, 75));
        roomLabel.setHorizontalAlignment(JLabel.RIGHT);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(roomLabel, BorderLayout.LINE_END);

        this.add(topPanel, BorderLayout.PAGE_START);

        effectListPanel = new EffectListPanel();
        JScrollPane effectScroll = new JScrollPane(effectListPanel);
        //create margin around the scrollpane

        effectScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        effectScroll.getVerticalScrollBar().setUI(new ScrollUI());
        effectScroll.setPreferredSize(new Dimension(150, 350));
        this.add(effectScroll, BorderLayout.EAST);
    }

    public static GameFrame getInstance() {
        if (instance == null) {
            instance = new GameFrame();
        }
        return instance;
    }

    private PlayerPanel playerPanel;

    public void setPlayerPanel(PlayerPanel playerPanel) {
        if (this.playerPanel != null) {
            this.remove(this.playerPanel);
        }

        this.playerPanel = playerPanel;
        this.add(playerPanel, BorderLayout.SOUTH);
        
        effectListPanel.reset();
        // TODO add all effects to the effects panel
        
        
        this.revalidate();
        this.repaint();
    }

    public EffectListPanel getEffectListPanel() {
        return effectListPanel;
    }
}
