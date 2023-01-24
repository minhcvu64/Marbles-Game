/*
  Project: Final Game - Squid Game Marbles
  Team Members: Minh Chau Vu
                Mahamed Ali
  Game Rule: https://www.insider.com/i-played-childrens-games-made-popular-squid-game-lost-2021-10#marbles-4
  Description: this is the main class
 */
package com.company;

import java.awt.EventQueue;

public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            GameView gameView = new GameView();
        });
    }
}
