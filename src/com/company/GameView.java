/*
  Project: Final Game - Squid Game Marbles
  Team Members: Minh Chau Vu
                Mahamed Ali
  Game Rule: https://www.insider.com/i-played-childrens-games-made-popular-squid-game-lost-2021-10#marbles-4
  Description: This class is the View + Controller of this game. It extends a JFrame and stores all sub-panels.
 */
package com.company;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameView extends JFrame implements GameModel.ModelListener {
    private static final String[] PLAYERS = {"Player 1", "Player 2"};
    private static final String PICK_ANNOUNCEMENT = "<html>%s's turn: type in the number of marbles</html>";
    private static final String GUESS_ANNOUNCEMENT = "<html>%s's turn: guess if %s picks and odd or even number of marbles</html>";
    private static final String PICK_AND_GUESS_RESULT_ANNOUNCEMENT = "<html>%s picks up %d marbles then %s guesses the number of marbles picked up to be %s. Now it's %s's turn: type in the number of marbles</html>";
    private int numberOfMarbles;

    private final GameModel gameModel;
    private final JPanel mainPanel;
    private final JLabel notificationLabel;
    private final UserInputPanel usersInputPanel;
    private final AnimationPanel animationPanel;
    private final JPanel usersMarblePanel;
    private final UserMarblesPanel[] userMarblesPanels;
    private final UserMarblesPanel user1MarblesPanel;
    private final UserMarblesPanel user2MarblesPanel;

    public GameView() {
        this.gameModel = new GameModel(this);
        numberOfMarbles = 0;

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setVisible(true);

        // create the main panel that stores all the sub-panels
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

        // setup the notification section to guide the players
        int currentPlayer = gameModel.playerIndex();
        notificationLabel = new JLabel(String.format(PICK_ANNOUNCEMENT, PLAYERS[currentPlayer]));
        notificationLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        notificationLabel.setFont(new Font("Serif", Font.PLAIN, 40));
        mainPanel.add(Box.createRigidArea(new Dimension(0,50)));
        mainPanel.add(notificationLabel);

        // create the panel that handles marbles moving animation
        animationPanel = new AnimationPanel();
        mainPanel.add(animationPanel);

        // create the section that handle user inputs and guesses
        usersInputPanel = new UserInputPanel(this);
        mainPanel.add(usersInputPanel);

        // create the panel displaying player's marbles
        usersMarblePanel = new JPanel();
        usersMarblePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        usersMarblePanel.setLayout(new GridLayout(1, 2));
        user1MarblesPanel = new UserMarblesPanel(PLAYERS[GameModel.PLAYER_1], Player.INITIAL_MARBLES_FOR_EACH_PLAYER);
        user2MarblesPanel = new UserMarblesPanel(PLAYERS[GameModel.PLAYER_2], Player.INITIAL_MARBLES_FOR_EACH_PLAYER);
        usersMarblePanel.add(user1MarblesPanel);
        usersMarblePanel.add(user2MarblesPanel);
        userMarblesPanels = new UserMarblesPanel[2];
        userMarblesPanels[GameModel.PLAYER_1] = user1MarblesPanel;
        userMarblesPanels[GameModel.PLAYER_2] = user2MarblesPanel;
        mainPanel.add(usersMarblePanel);

        // add the main panel into the frame
        add(mainPanel);
        setResizable(true);
        setTitle("Marbles");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void updatePlayerNumOfMarbles(int player, int numberOfMarbles) {
        userMarblesPanels[player].setNumberOfMarbles(numberOfMarbles);
        userMarblesPanels[player].repaint();
    }

    void handlePickingMarblesEvent(int numOfMarbles) {
        numberOfMarbles = gameModel.pickupMarbles(numOfMarbles);
        int currentPlayer = gameModel.playerIndex();
        notificationLabel.setText(String.format(GUESS_ANNOUNCEMENT, PLAYERS[currentPlayer], PLAYERS[1 - currentPlayer]));
        animationPanel.setFist(true);
        animationPanel.setNumberOfMarbles(numberOfMarbles);
        usersInputPanel.setInputMode(GameModel.GUESSING_MODE);
        usersInputPanel.repaint();
    }

    void handleGuessingNumberOfMarblesEvent(boolean isEven) {
        int currentPlayer = gameModel.playerIndex();
        boolean correct = gameModel.guessNumOfPickedUpMarbles(isEven);
        int nextPlayer = gameModel.playerIndex();
        animationPanel.setFist(false);
        animationPanel.setWinnerOfThisRound(correct ? currentPlayer : 1 - currentPlayer);
        animationPanel.repaint();
        usersInputPanel.setInputMode(GameModel.PICK_MODE);
        usersInputPanel.repaint();
        if (gameModel.isGameEnded()) {
            usersInputPanel.setGameEnded(true);
            notificationLabel.setText(PLAYERS[gameModel.getWinner()] + " wins");
        } else {
            usersInputPanel.setGameEnded(false);
            notificationLabel.setText(String.format(PICK_AND_GUESS_RESULT_ANNOUNCEMENT, PLAYERS[1 - currentPlayer], numberOfMarbles, PLAYERS[currentPlayer], isEven ? "even" : "odd", PLAYERS[nextPlayer]));
        }
    }

    public static Image getLabelFromImage(String path) {
        ImageIcon icon = new ImageIcon(path);
        return icon.getImage();
    }
}