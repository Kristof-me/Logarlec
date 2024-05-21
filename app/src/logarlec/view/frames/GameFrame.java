package logarlec.view.frames;

import javax.swing.*;

import logarlec.model.actor.Student;
import logarlec.view.elements.ScrollUI;
import logarlec.view.panels.EffectListPanel;
import logarlec.view.panels.PlayerPanel;
import logarlec.view.panels.RoomPanel;
import logarlec.view.utility.ThemeManager;

import java.awt.*;

/**
 * The frame in which the game is displayed, singleton.
 */
public class GameFrame extends JFrame {
    /**
     * The singleton instance of the GameFrame.
     */
    static GameFrame instance;
    /**
     * The label displaying the current room name.
     */
    private JLabel roomLabel;
    /**
     * The panel displaying the effects on the player.
     */
    private EffectListPanel effectListPanel;
    /**
     * The panel displaying the player.
     */
    private PlayerPanel playerPanel;
    /**
     * The panel displaying the current room.
     */
    private RoomPanel roomPanel;
    /**
     * Creates a new game frame.
     */
    protected GameFrame() {
        super("Logarléc");
        this.setMinimumSize(new Dimension(900, 600));
        this.setSize(1200, 750);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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
    /**
     * Returns the singleton instance of the GameFrame.
     * @return The singleton instance of the GameFrame.
     */
    public static GameFrame getInstance() {
        if (instance == null) {
            instance = new GameFrame();
        }
        return instance;
    }

    /**
     * The PlayerPanel calls this method when the player is updated.
     * If the player has moved, the room and effect list is updated.
     */
    public void updateStudent() {
        if (playerPanel == null) return;
        if (playerPanel.getViewedPlayer().getLocation() != roomPanel.getRoom()) {
            remove(roomPanel.removeView());
            roomPanel = playerPanel.getViewedPlayer().getLocation().createOwnView();
            add(roomPanel, BorderLayout.CENTER);
            roomLabel.setText(playerPanel.getViewedPlayer().getLocation().getName());
            effectListPanel.bindStudent(playerPanel.getViewedPlayer());
            this.repaint();
            this.revalidate();
        }
    }
    /**
     * Sets the player panel to the given player panel, and recreates the room panel and effect list based on the player.
     * @param playerPanel - the player panel to set.
     */
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

    /**
     * Returns the effect list panel.
     * @return The effect list panel.
     */
    public EffectListPanel getEffectListPanel() {
        return effectListPanel;
    }
}
