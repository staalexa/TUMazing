package de.tum.cit.ase.maze.util;

import de.tum.cit.ase.maze.MazeRunnerGame;

/**
 * This Class handles the Players life
 */
public class LivesManager {
    private MazeRunnerGame game;
    private static final int MAX_LIVES = 3;
    private int lives;

    public LivesManager(MazeRunnerGame game) {
        this.game = game;
        lives = MAX_LIVES;
    }

    /**
     * Handles lives of Player
     */
    private void looseLife() {
        if(checkIfDead()) game.goToMenu(); //TODO: call the end of the game
        lives--;
        game.getGameScreen().getHud().loseLife();
    }

    /**
     * Helper method to check the last life
     * @return true when player has his last life
     */
    private boolean checkIfDead() {
        return lives-- < 1;
    }

    public static int getMaxLives() {
        return MAX_LIVES;
    }

    public int getLives() {
        return lives;
    }
}
