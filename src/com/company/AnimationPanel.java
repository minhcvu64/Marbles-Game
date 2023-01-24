/*
  Project: Final Game - Squid Game Marbles
  Team Members: Minh Chau Vu
                Mahamed Ali
  Game Rule: https://www.insider.com/i-played-childrens-games-made-popular-squid-game-lost-2021-10#marbles-4
  Description: this class extends a JPanel and handles the animation of moving players' marbles
 */

package com.company;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AnimationPanel extends JPanel implements ActionListener {
    private static final int ICON_WIDTH = 400;
    private static final int ICON_HEIGHT = 400;
    private static final int MARBLE_WIDTH = 50;
    private static final int MARBLE_HEIGHT = 50;
    private static final int ANIMATION_DELAY = 5;

    private int numberOfMarbles;
    private boolean isFist;
    private final Image fist;
    private final Image hand;
    private final Image marble;
    private int delta;
    private final Timer timer;
    private int winnerOfThisRound;

    public AnimationPanel() {
        isFist = true;
        fist = GameView.getLabelFromImage("src/resources/fist.png");
        hand = GameView.getLabelFromImage("src/resources/hand.png");
        marble = GameView.getLabelFromImage("src/resources/marble.png");
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setMinimumSize(new Dimension(ICON_WIDTH, ICON_HEIGHT));
        setPreferredSize(new Dimension(ICON_WIDTH, ICON_HEIGHT));
        timer = new Timer(ANIMATION_DELAY, this);
        timer.start();
    }

    public void setNumberOfMarbles(int numberOfMarbles) {
        this.numberOfMarbles = numberOfMarbles;
    }

    public void setFist(boolean fist) {
        isFist = fist;
    }

    public void setWinnerOfThisRound(int player) {
        winnerOfThisRound = player;
    }

    private void drawMarbles(Graphics g) {
        for (int i = 0; i < numberOfMarbles; i++) {
            int x = (getWidth() - numberOfMarbles * MARBLE_WIDTH) / 2 + i * MARBLE_WIDTH + ((winnerOfThisRound == 0) ? -delta : delta);
            int y = (getHeight() - MARBLE_HEIGHT) / 2 + delta;
            g.drawImage(marble, x, y, MARBLE_WIDTH, MARBLE_HEIGHT, this);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image icon = isFist ? fist : hand;
        g.drawImage(icon, (getWidth() - ICON_WIDTH) / 2, (getHeight() - ICON_HEIGHT) / 2, ICON_WIDTH, ICON_HEIGHT, this);

        // draw the marbles
        if (!isFist) {
            drawMarbles(g);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isFist) {
            delta = 0;
        } else {
            delta++;
        }
        repaint();
    }
}
