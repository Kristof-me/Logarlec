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

/**
 * The frame in which the menu is displayed.
 */
public class MenuFrame extends JFrame {
    /**
     * The list of players added to play the game.
     */
    private List<Player> players = new ArrayList<Player>();
    /**
     * The panel that contains the player list.
     */
    private JPanel playerList;
    /**
     * Creates a new menu frame.
     */
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

        // Scrollpanel with player list
        playerList = new JPanel();
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
        
        constraints.insets = new Insets(0, 5, 0, 5);
        this.add(scrollPane, constraints);

        //Buttons
        constraints.insets = new Insets(10, 5, 10, 5);
        //Add player button
        CustomButton addPlayer = new CustomButton("Add Player", e -> {
            players.add(new Player());
            playerList.add(new MenuPlayerPanel(players.get(players.size() - 1), this));

            revalidate();
            repaint();

            super.paintComponents(getGraphics());
        });

        // Filler to follow UI design
        constraints.fill = GridBagConstraints.VERTICAL;
        addPlayer.setExpectedSize(new Dimension(240, 21));
        addPlayer.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        this.add(addPlayer, constraints);
        this.add(new JPanel(), constraints);  
        
        // Start Game button
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(0, 5, 0, 5);  // Remove bottom inset
        constraints.anchor = GridBagConstraints.SOUTH;
        
        CustomButton startGame = new CustomButton("Start", e -> {
            //No players should not be able to play
            if (players.size() < 1) {
                JOptionPane.showMessageDialog(this, "You must have at least one player to start the game.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            //Everyone has to have a name
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
            //If everything is fine, start the game
            createGame(false);
        });
        startGame.setFont(ThemeManager.getInstance().getFont(Font.BOLD, 18));
        startGame.setBackground(ThemeManager.ACCENT);
        startGame.setBorder(BorderFactory.createLineBorder(new Color(12, 167, 137), 4));
        this.add(startGame, constraints);

        //Exit button
        constraints.insets = new Insets(10, 5, 5, 5);  // Remove top inset
        
        CustomButton exit = new CustomButton("Exit", e -> {
            System.exit(0);
        });

        exit.setFont(ThemeManager.getInstance().getFont(Font.BOLD, 18));
        this.add(exit, constraints);
    }
    /**
     * Creates a new game with the players added to the menu.
     * @param resetPlayers - whether the players should be reset.
     */
    public void createGame(boolean resetPlayers) {
        GameManager.getInstance().reset();
            for (Player player : players) {
                if (resetPlayers) player.reset();

                GameManager.getInstance().addPlayer(player);
            }

            GameManager.getInstance().startGame(this);
    }
    /**
     * Deletes a player from the menu.
     * @param player - the player to delete.
     * @param panel - the panel to remove.
     */
    public void deletePlayer(Player player, MenuPlayerPanel panel) {
        players.remove(player);
        this.remove(panel);
        revalidate();
        repaint();
    }

    public void reset(){
        players.clear();
        playerList.removeAll();
    }
}