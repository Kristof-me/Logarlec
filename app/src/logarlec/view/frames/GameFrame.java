package logarlec.view.frames;
import javax.swing.*;

import logarlec.model.actor.Student;
import logarlec.view.elements.ScrollUI;
import logarlec.view.panels.EffectListPanel;
import logarlec.view.panels.PlayerPanel;
import logarlec.view.panels.RoomPanel;

import java.awt.*;

public class GameFrame extends JFrame {
    static GameFrame instance;
    private EffectListPanel effectListPanel;

    protected GameFrame() {
        super("Logarléc");
        this.setSize(800, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setPreferredSize(new Dimension(150, 500));
        this.add(rightPanel, BorderLayout.EAST);
        
        JLabel roomLabel = new JLabel("Room Name");
        roomLabel.setHorizontalAlignment(JLabel.RIGHT);
        rightPanel.add(roomLabel, BorderLayout.NORTH);        
        
        effectListPanel = new EffectListPanel();
        JScrollPane effectScroll = new JScrollPane(effectListPanel);
        
        //create margin around the scrollpane
        effectScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        effectScroll.getVerticalScrollBar().setUI(new ScrollUI());

        rightPanel.add(effectScroll, BorderLayout.CENTER);
    }

    public static GameFrame getInstance() {
        if (instance == null) {
            instance = new GameFrame();
        }
        return instance;
    }

    private PlayerPanel playerPanel;
    private RoomPanel roomPanel;

    public void setPlayerPanel(PlayerPanel playerPanel) {
        if (this.playerPanel != null) {
            this.remove(this.playerPanel);
        }

        // adding player panel
        this.add(playerPanel, BorderLayout.SOUTH);
        
        // adding effects
        effectListPanel.bindStudent(playerPanel.getViewedPlayer());
        // TODO add all effects to the effects panel
        
        // displaying room
        Student player = playerPanel.getViewedPlayer();
        roomPanel = player.getLocation().createOwnView();
        this.add(roomPanel, BorderLayout.CENTER);
        
        this.revalidate();
        this.repaint();
    }

    public EffectListPanel getEffectListPanel() {
        return effectListPanel;
    }
}
