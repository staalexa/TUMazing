package de.tum.cit.ase.maze.util;

import de.tum.cit.ase.maze.MazeRunnerGame;

/**
 * Handles Key collecting
 */
public class Key {
    private MazeRunnerGame game;
    private Position position;
    private boolean found;

    public Key(MazeRunnerGame game) {
        this.game = game;
        this.position = null;
        found = false;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void pickUp() {
        game.getGameScreen().getHud().collectedKey(true);
        game.getGameScreen().getCharacter().setHasKey(true);
    }

    public boolean checkIfPickedUp() {
        return game.getGameScreen().getCharacter().hasKey();
    }

    public Position getPosition() {
        return position;
    }
}
