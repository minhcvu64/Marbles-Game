/*
  Project: Final Game - Squid Game Marbles
  Team Members: Minh Chau Vu
                Mahamed Ali
  Game Rule: https://www.insider.com/i-played-childrens-games-made-popular-squid-game-lost-2021-10#marbles-4
  Description: this class extends a JPanel and displays the number of each user's marbles
 */
package com.company;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

public class UserMarblesPanel extends JPanel {
    private static final String TEXT = "%s has a total of %d marbles";
    private static final int MARBLE_WIDTH = 50;
    private static final int MARBLE_HEIGHT = 50;

    private final String playerName;
    private int numberOfMarbles;
    private final JLabel label;
    private final Image marble;

    public UserMarblesPanel(String playerName, int numberOfMarbles) {
        this.playerName = playerName;
        label = new JLabel(String.format(TEXT, playerName, numberOfMarbles));
        add(label);
        this.numberOfMarbles = numberOfMarbles;
        marble = GameView.getLabelFromImage("src/resources/marble.png");
        setMaximumSize(new Dimension(0, 40));
    }

    public void setNumberOfMarbles(int numOfMarbles) {
        this.numberOfMarbles = numOfMarbles;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        label.setText(String.format(TEXT, playerName, numberOfMarbles));
        label.setVisible(true);
        for (int i = 0; i < numberOfMarbles; i++) {
            g.drawImage(marble, (getWidth() - numberOfMarbles * MARBLE_WIDTH) / 2 + (i % 10)  * MARBLE_WIDTH, (getHeight() - MARBLE_HEIGHT) / 2 + (i/ 10) * MARBLE_HEIGHT, MARBLE_WIDTH, MARBLE_HEIGHT, this);
        }
    }
}
