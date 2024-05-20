package logarlec.view.frames;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;

import logarlec.view.elements.CustomButton;
import logarlec.view.utility.ThemeManager;

public class GameEndFrame extends JFrame{
    public GameEndFrame(MenuFrame returnToFrame, boolean isWon) {
        super("Game Over");
        this.setSize(400, 280);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);

        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints(0, GridBagConstraints.RELATIVE, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0);

        JLabel gameOverLabel = new JLabel(isWon ? "The Students Won!!!!!" : "The Students Lost :(", JLabel.CENTER);
        gameOverLabel.setForeground(isWon ? new Color(240, 231, 74) : new Color(200, 24, 104));
        gameOverLabel.setFont(ThemeManager.getInstance().getFont(Font.BOLD, 28));
        this.add(gameOverLabel, gbc);

        // Restart button
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        CustomButton restartButton = new CustomButton("Restart", e -> {
            this.dispose();
            returnToFrame.createGame(true);
        });

        customizeButton(restartButton, ThemeManager.ACCENT);
        this.add(restartButton, gbc);


        // New game button
        CustomButton newGameButton = new CustomButton("New game", e -> {
            this.dispose();
            returnToFrame.setVisible(true);
        });
        
        customizeButton(newGameButton, ThemeManager.ACCENT);
        this.add(newGameButton, gbc);
        
        // Exit button
        gbc.insets = new Insets(20, 5, 5, 5);
        CustomButton exitButton = new CustomButton("Exit", e -> {
            this.dispose();
            returnToFrame.dispose();
            System.exit(0);
        });

        customizeButton(exitButton, ThemeManager.BUTTON);
        this.add(exitButton, gbc);
    }

    private void customizeButton (CustomButton button, Color color) {
        button.setBackground(color);
        button.setBorder(BorderFactory.createLineBorder(color, 3));
        button.setExpectedSize(new Dimension(220, 30));
        button.setFont(ThemeManager.getInstance().getFont(Font.BOLD, 18));
    }
}
