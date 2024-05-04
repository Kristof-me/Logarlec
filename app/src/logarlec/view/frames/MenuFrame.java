package logarlec.view.frames;

import javax.swing.*;

import logarlec.view.panels.PlayerPanel;
import logarlec.view.utility.Player;
import logarlec.view.elements.ScrollUI;

import java.util.List;
import java.util.ArrayList;
import java.awt.*;

public class MenuFrame extends JFrame {
    private List<Player> players = new ArrayList<Player>();

    public MenuFrame() {
        super("Logarlec");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(300, 350);
        this.setLayout(new GridBagLayout());
        

        GridBagConstraints constraints = new GridBagConstraints(0, GridBagConstraints.RELATIVE, 3, 1, 1, 0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(10, 5, 20, 5), 0, 0);

        // Title label
        JLabel titleLabel = new JLabel("Logarléc", JLabel.CENTER);
        // TODO set font
        this.add(titleLabel, constraints);


        // Create a scrollable panel to hold the player panels in a vertical layout and has a vertical scrollbar, with a maximum height of 300 pixels
        JPanel playerPanel = new JPanel();
        playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));
        playerPanel.add(Box.createVerticalGlue());

        JScrollPane scrollPane = new JScrollPane(playerPanel);
        scrollPane.getVerticalScrollBar().setUI(new ScrollUI());
        scrollPane.setMinimumSize(new Dimension(300, 140));
        scrollPane.setPreferredSize(new Dimension(300, 140));

        // Add the player panels to the scrollable panel
        for (Player player : players) {
            playerPanel.add(new PlayerPanel(player));
        }
        // Add the scroll pane to the frame
        constraints.insets = new Insets(0, 5, 0, 5);
        this.add(scrollPane, constraints);

        //create three new buttons below the scroll pane, each in a new row
        constraints.insets = new Insets(10, 5, 10, 5);

        JButton addPlayer = new JButton("Add Player");
        addPlayer.addActionListener(e -> {
            players.add(new Player());
            playerPanel.add(new PlayerPanel(players.get(players.size() - 1)));

            playerPanel.revalidate();
            playerPanel.repaint();

            super.paintComponents(getGraphics());
        });
        this.add(addPlayer, constraints);

        this.add(new JPanel(), constraints);  // Add a filler component

        // Add the "Start Game" button
        constraints.insets = new Insets(0, 5, 0, 5);  // Remove bottom inset
        constraints.anchor = GridBagConstraints.SOUTH;

        
        JButton startGame = new JButton("Start Game");
        startGame.addActionListener(e -> {
            // todo start the game
        });

        this.add(startGame, constraints);

        // Add the "Exit" button
        constraints.insets = new Insets(0, 5, 10, 5);  // Remove top inset
        
        JButton exit = new JButton("Exit");
        exit.addActionListener(e -> {
            System.exit(0);
        });

        this.add(exit, constraints);
    }

    public void deletePlayer(Player player) {
        players.remove(player);
    }
}
