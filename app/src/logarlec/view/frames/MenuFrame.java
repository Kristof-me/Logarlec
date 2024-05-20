package logarlec.view.frames;

import javax.swing.*;

import logarlec.view.panels.MenuPlayerPanel;
import logarlec.view.utility.ThemeManager;
import logarlec.control.GameManager;
import logarlec.control.controller.Player;
import logarlec.view.elements.CustomButton;
import logarlec.view.elements.ScrollUI;

import java.util.List;
import java.util.ArrayList;
import java.awt.*;


public class MenuFrame extends JFrame {
    private List<Player> players = new ArrayList<Player>();

    public MenuFrame() {
        super("Logarléc");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(300, 400);
        this.setPreferredSize(new Dimension(300, 400));
        this.setResizable(false);

        this.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints(0, GridBagConstraints.RELATIVE, 3, 1, 1, 0, GridBagConstraints.PAGE_START, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 10, 5), 0, 0);

        // Title label
        JLabel titleLabel = new JLabel("LOGARLÉC", JLabel.CENTER);
        titleLabel.setFont(ThemeManager.getInstance().getFont(Font.BOLD, 28));

        this.add(titleLabel, constraints);

        // Create a scrollable panel to hold the player panels in a vertical layout and has a vertical scrollbar, with a maximum height of 300 pixels
        JPanel playerList = new JPanel();
        playerList.setLayout(new BoxLayout(playerList, BoxLayout.Y_AXIS));
        playerList.setBackground(ThemeManager.TRACK);

        JScrollPane scrollPane = new JScrollPane(playerList);
        scrollPane.getVerticalScrollBar().setUI(new ScrollUI());
        scrollPane.setMinimumSize(new Dimension(300, 140));
        scrollPane.setPreferredSize(new Dimension(300, 140));
        scrollPane.setBackground(ThemeManager.TRACK);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

        // Add the player panels to the scrollable panel
        for (Player player : players) {
            playerList.add(new MenuPlayerPanel(player, this));
        }
        
        // Add the scroll pane to the frame
        constraints.insets = new Insets(0, 5, 0, 5);
        this.add(scrollPane, constraints);

        //create three new buttons below the scroll pane, each in a new row
        constraints.insets = new Insets(10, 5, 10, 5);

        CustomButton addPlayer = new CustomButton("Add Player", e -> {
            players.add(new Player());
            playerList.add(new MenuPlayerPanel(players.get(players.size() - 1), this));

            revalidate();
            repaint();

            super.paintComponents(getGraphics());
        });
        
        constraints.fill = GridBagConstraints.VERTICAL;
        addPlayer.setExpectedSize(new Dimension(240, 21));
        addPlayer.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        this.add(addPlayer, constraints);
        
        
        this.add(new JPanel(), constraints);  // Add a filler component
        
        // Add the "Start Game" button
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(0, 5, 0, 5);  // Remove bottom inset
        constraints.anchor = GridBagConstraints.SOUTH;
        
        CustomButton startGame = new CustomButton("Start", e -> {
            if (players.size() < 1) {
                JOptionPane.showMessageDialog(this, "You must have at least one player to start the game.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            boolean hasEmptyName = false;
            for (Player player : players) {
                if (player.getActor().getName().isBlank()) {
                    hasEmptyName = true;
                    break;
                }
            }
            if (hasEmptyName) {
                JOptionPane.showMessageDialog(this, "All players must have a name to start the game.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            GameManager.getInstance().reset();
            for (Player player : players) {
                GameManager.getInstance().addPlayer(player);
            }

            GameManager.getInstance().startGame(this);
        });

        startGame.setFont(ThemeManager.getInstance().getFont(Font.BOLD, 18));
        startGame.setBackground(ThemeManager.ACCENT);
        startGame.setBorder(BorderFactory.createLineBorder(new Color(12, 167, 137), 4));
        this.add(startGame, constraints);

        // Add the "Exit" button
        constraints.insets = new Insets(10, 5, 5, 5);  // Remove top inset
        
        CustomButton exit = new CustomButton("Exit", e -> {
            System.exit(0);
        });

        exit.setFont(ThemeManager.getInstance().getFont(Font.BOLD, 18));
        this.add(exit, constraints);
    }

    public void deletePlayer(Player player, MenuPlayerPanel panel) {
        players.remove(player);
        this.remove(panel);
        revalidate();
        repaint();
    }
}