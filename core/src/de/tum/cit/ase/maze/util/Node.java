package de.tum.cit.ase.maze.util;

public class Node {
    private final int x, y;

    private int gScore, fScore, objectType;

    public Node(int x, int y, int objectType) {
        this.x = x;
        this.y = y;

        gScore = Integer.MAX_VALUE;
        fScore = Integer.MAX_VALUE;
        
        this.objectType = objectType;
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
