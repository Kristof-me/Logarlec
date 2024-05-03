package logarlec.view.frames;

import javax.swing.*;

import logarlec.view.panels.PlayerPanel;
import logarlec.view.utility.Player;

import java.util.List;
import java.util.ArrayList;
import java.awt.*;

public class MenuFrame extends JFrame {
    private static MenuFrame instance;
    public static MenuFrame getInstance() {
        if (instance == null) {
            instance = new MenuFrame();
        }
        return instance;
    }
    public void deletePlayer(Player player) {
        players.remove(player);

    }

    private List<Player> players = new ArrayList<Player>();
    public MenuFrame() {
        super("Logarlec");
        this.setSize(300, 350);
        this.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 3;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;
        constraints.weighty = 0;
        constraints.anchor = GridBagConstraints.NORTH;
        constraints.insets = new Insets(10, 5, 20, 5);
        this.add(new JLabel("Logarléc", JLabel.CENTER), constraints);


        // Create a scrollable panel to hold the player panels in a vertical layout and has a vertical scrollbar, with a maximum height of 300 pixels
        JPanel playerPanel = new JPanel();
        playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));
        playerPanel.add(Box.createVerticalGlue());
        JScrollPane scrollPane = new JScrollPane(playerPanel);

        scrollPane.setMinimumSize(new Dimension(300, 140));
        scrollPane.setPreferredSize(new Dimension(300, 140));
        // Add the player panels to the scrollable panel
        for (Player player : players) {
            playerPanel.add(new PlayerPanel(player));
        }
        // Add the scroll pane to the frame
        constraints.insets = new Insets(0, 5, 0, 5);
        constraints.gridx = 0;
        constraints.gridy = 1;  // Set gridy to 1
        constraints.gridwidth = 3;
        constraints.fill = GridBagConstraints.HORIZONTAL;  // Change to BOTH to allow vertical expansion
        constraints.weightx = 1;
        constraints.weighty = 0;  // Set weighty to 1 to allow vertical expansion
        this.add(scrollPane, constraints);

        //create three new buttons below the scroll pane, each in a new row
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;
        constraints.weighty = 0;
        constraints.insets = new Insets(10, 5, 10, 5);
        Button addPlayer = new Button("Add Player");
        addPlayer.addActionListener(e -> {
            players.add(new Player());
            playerPanel.add(new PlayerPanel(players.get(players.size() - 1)));
            playerPanel.revalidate();
            playerPanel.repaint();
            super.paintComponents(getGraphics());
            System.out.println(scrollPane.getSize());
        });
        this.add(addPlayer, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 0;
        constraints.weighty = 1;  // Set weighty to 1 so the filler takes up extra vertical space
        this.add(new JPanel(), constraints);  // Add a filler component

        // Add the "Start Game" button
        constraints.insets = new Insets(0, 5, 0, 5);  // Remove bottom inset
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;
        constraints.weighty = 0;  // Set weighty to 0
        constraints.anchor = GridBagConstraints.SOUTH;
        Button startGame = new Button("Start Game");
        startGame.addActionListener(e -> {
            //start the game
        });
        this.add(startGame, constraints);

        // Add the "Exit" button
        constraints.insets = new Insets(0, 5, 10, 5);  // Remove top inset
        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;
        constraints.weighty = 0;  // Set weighty to 0
        Button exit = new Button("Exit");
        exit.addActionListener(e -> {
            System.exit(0);
        });
        this.add(exit, constraints);



    }
}
