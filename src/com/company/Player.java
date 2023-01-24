package com.company;

public class Player {
    public static final int INITIAL_MARBLES_FOR_EACH_PLAYER = 10;

    private int numOfMarbles;

    public Player() {
        this.numOfMarbles = INITIAL_MARBLES_FOR_EACH_PLAYER;
    }

    public int getNumOfMarbles() {
        return numOfMarbles;
    }

    public int takeMarbles(int numOfMarbles) {
        if (numOfMarbles > this.numOfMarbles) {
            numOfMarbles = this.numOfMarbles;
        }

        this.numOfMarbles -= numOfMarbles;
        return numOfMarbles;
    }

    public int giveMarbles(int numOfMarbles) {
        if (numOfMarbles > 0) {
            this.numOfMarbles += numOfMarbles;
        }

        return this.numOfMarbles;
    }

    public boolean isEmpty() {
        return this.numOfMarbles == 0;
    }
}
