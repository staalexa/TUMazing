package de.tum.cit.ase.maze.util;

public class Node {
    private final int x, y;

    private int gScore, fScore;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;

        gScore = Integer.MAX_VALUE;
        fScore = Integer.MAX_VALUE;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getgScore() {
        return gScore;
    }

    public void setgScore(int gScore) {
        this.gScore = gScore;
    }

    public int getfScore() {
        return fScore;
    }

    public void setfScore(int fScore) {
        this.fScore = fScore;
    }
}
