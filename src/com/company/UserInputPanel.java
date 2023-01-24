/*
  Project: Final Game - Squid Game Marbles
  Team Members: Minh Chau Vu
                Mahamed Ali
  Game Rule: https://www.insider.com/i-played-childrens-games-made-popular-squid-game-lost-2021-10#marbles-4
  Description: this class extends a JPanel and handles users' input events.
 */

package com.company;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class UserInputPanel extends JPanel {
    private final GameView gameView;
    private final JPasswordField playerMarblesInput;
    private final JButton evenButton;
    private final JButton oddButton;

    private boolean isGameEnded;

    private int inputMode;

    public UserInputPanel(GameView gameView) {
        this.gameView = gameView;
        inputMode = GameModel.PICK_MODE;

        setAlignmentX(Component.CENTER_ALIGNMENT);
        playerMarblesInput = new JPasswordField(1);
        playerMarblesInput.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                String userInput = new String(playerMarblesInput.getPassword());
                if (userInput.equals("")) {
                    return;
                }
                gameView.handlePickingMarblesEvent(Integer.parseInt(userInput));
            }
        });
        playerMarblesInput.setVisible(true);
        add(playerMarblesInput);

        evenButton = new JButton("Even");
        evenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameView.handleGuessingNumberOfMarblesEvent(true);
            }
        });
        evenButton.setVisible(false);
        oddButton = new JButton("Odd");
        oddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameView.handleGuessingNumberOfMarblesEvent(false);
            }
        });
        oddButton.setVisible(false);
        add(evenButton);
        add(oddButton);

        setMinimumSize(new Dimension(0, 100));
        isGameEnded = false;
    }

    public void setInputMode(int inputMode) {
        this.inputMode = inputMode;
    }

    public void setGameEnded(boolean gameEnded) {
        isGameEnded = gameEnded;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (isGameEnded) {
            playerMarblesInput.setVisible(false);
            evenButton.setVisible(false);
            oddButton.setVisible(false);
            return;
        }

        if (inputMode == GameModel.PICK_MODE) {
            playerMarblesInput.setVisible(true);
            evenButton.setVisible(false);
            oddButton.setVisible(false);
        } else {
            playerMarblesInput.setVisible(false);
            evenButton.setVisible(true);
            oddButton.setVisible(true);
        }
    }
}
