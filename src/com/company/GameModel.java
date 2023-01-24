/*
  Project: Final Game - Squid Game Marbles
  Team Members: Minh Chau Vu
                Mahamed Ali
  Game Rule: https://www.insider.com/i-played-childrens-games-made-popular-squid-game-lost-2021-10#marbles-4
  Description: This class is model of this game. It knows the rule and handle all the game's logic.
 */
package com.company;

public class GameModel {
    public static final int PLAYER_1 = 0;
    public static final int PLAYER_2 = 1;
    private static final int[] PLAYER_OF_TURN = new int[] {PLAYER_1, PLAYER_2, PLAYER_2, PLAYER_1};
    public static final int PICK_MODE = 0;
    public static final int GUESSING_MODE = 1;

    private final ModelListener modelListener;
    private final Player[] players;
    private int numOfPickedUpMarbles;
    private int turn;
    private int gameMode;

    public GameModel(ModelListener modelListener) {
        this.modelListener = modelListener;
        players = new Player[2];
        players[0] = new Player();
        players[1] = new Player();
        numOfPickedUpMarbles = 0;
        gameMode = 0;
    }

    public int pickupMarbles(int numOfMarbles) {
        numOfPickedUpMarbles = Math.min(numOfMarbles, currentPlayer().getNumOfMarbles());
        nextTurn();
        return numOfPickedUpMarbles;
    }

    public boolean guessNumOfPickedUpMarbles(boolean isEven) {
        if ((isEven && (numOfPickedUpMarbles % 2 == 0)) || (!isEven && (numOfPickedUpMarbles % 2 != 0))) {
            currentPlayer().giveMarbles(otherPlayer().takeMarbles(numOfPickedUpMarbles));
            modelListener.updatePlayerNumOfMarbles(playerIndex(), currentPlayer().getNumOfMarbles());
            modelListener.updatePlayerNumOfMarbles(1 - playerIndex(), otherPlayer().getNumOfMarbles());
            nextTurn();
            return true;
        } else {
            otherPlayer().giveMarbles(currentPlayer().takeMarbles(numOfPickedUpMarbles));
            modelListener.updatePlayerNumOfMarbles(playerIndex(), currentPlayer().getNumOfMarbles());
            modelListener.updatePlayerNumOfMarbles(1 - playerIndex(), otherPlayer().getNumOfMarbles());
            nextTurn();
            return false;
        }
    }

    private Player currentPlayer() {
        return players[playerIndex()];
    }

    private Player otherPlayer() {
        return players[1 - playerIndex()];
    }

    public interface ModelListener {
        void updatePlayerNumOfMarbles(int player, int numOfMarbles);
    }

    private int nextTurn() {
        turn += 1;
        gameMode = (turn % 2 == 0) ? PICK_MODE : GUESSING_MODE;
        return turn;
    }

    public int playerIndex() {
        return PLAYER_OF_TURN[turn % 4];
    }

    public boolean isGameEnded() {
        return currentPlayer().isEmpty() || otherPlayer().isEmpty();
    }

    public int getWinner() {
        if (currentPlayer().isEmpty()) {
            return 1 - playerIndex();
        } else if (otherPlayer().isEmpty()) {
            return playerIndex();
        } else {
            return -1;
        }
    }
}
