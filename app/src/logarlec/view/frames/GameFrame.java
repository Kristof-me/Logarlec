package logarlec.view.frames;

import javax.swing.*;

import logarlec.model.actor.Student;
import logarlec.view.elements.ScrollUI;
import logarlec.view.panels.EffectListPanel;
import logarlec.view.panels.PlayerPanel;
import logarlec.view.panels.RoomPanel;
import logarlec.view.utility.ThemeManager;

import java.awt.*;

public class GameFrame extends JFrame {
    static GameFrame instance;

    private JLabel roomLabel;

    private EffectListPanel effectListPanel;
    private PlayerPanel playerPanel;
    private RoomPanel roomPanel;

    protected GameFrame() {
        super("Logarléc");
        this.setMinimumSize(new Dimension(900, 600));
        this.setSize(1200, 750);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setPreferredSize(new Dimension(150, 500));
        this.add(rightPanel, BorderLayout.EAST);

        roomLabel = new JLabel();
        roomLabel.setHorizontalAlignment(JLabel.RIGHT);
        roomLabel.setFont(ThemeManager.getInstance().getFont(Font.BOLD, 28));
        roomLabel.setBackground(ThemeManager.TRACK);
        roomLabel.setHorizontalAlignment(JLabel.CENTER);
        rightPanel.add(roomLabel, BorderLayout.NORTH);

        effectListPanel = new EffectListPanel();
        JScrollPane effectScroll = new JScrollPane(effectListPanel);

        // create margin around the scrollpane
        effectScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        effectScroll.getVerticalScrollBar().setUI(new ScrollUI());
        effectScroll.setBorder(null);

        rightPanel.add(effectScroll, BorderLayout.CENTER);

    }

    public static GameFrame getInstance() {
        if (instance == null) {
            instance = new GameFrame();
        }
        return instance;
    }

    /**
     * If the player has changed rooms, recreate RoomPanel and EffectListPanel
     */
    public void updateStudent() {
        // todo - im doing it mom
        if (playerPanel == null) return;
        if (playerPanel.getViewedPlayer().getLocation() != roomPanel.getRoom()) {
            remove(roomPanel.removeView());
            roomPanel = playerPanel.getViewedPlayer().getLocation().createOwnView();
            add(roomPanel, BorderLayout.CENTER);
            roomLabel.setText("KA-" + playerPanel.getViewedPlayer().getLocation().getId());
            effectListPanel.bindStudent(playerPanel.getViewedPlayer());
            this.repaint();
            this.revalidate();
        }
    }

    public void setPlayerPanel(PlayerPanel playerPanel) {
        if (this.playerPanel != null) {
            this.remove(this.playerPanel);
        }
        System.out.println("Setting player panel");
        this.playerPanel = playerPanel;
        Student player = playerPanel.getViewedPlayer();

        // adding player panel
        this.add(playerPanel, BorderLayout.SOUTH);

        roomLabel.setText("KA-" + player.getLocation().getId());

        // adding effects
        effectListPanel.bindStudent(playerPanel.getViewedPlayer());

        // displaying room
        if (roomPanel != null) {
            this.remove(roomPanel.removeView());
        }
        roomPanel = player.getLocation().createOwnView();
        this.add(roomPanel, BorderLayout.CENTER);

        this.revalidate();
        this.repaint();
    }

    public EffectListPanel getEffectListPanel() {
        return effectListPanel;
    }
}
