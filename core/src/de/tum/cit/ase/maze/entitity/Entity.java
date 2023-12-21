package de.tum.cit.ase.maze.entitity;

import de.tum.cit.ase.maze.MazeRunnerGame;
import de.tum.cit.ase.maze.hud.HUD;
import de.tum.cit.ase.maze.map.Maze;
import de.tum.cit.ase.maze.util.Direction;
import de.tum.cit.ase.maze.util.Position;

/**
 * Template for entities
 */
public abstract class Entity {
    private final Position position;
    private int lives;
    private int speed;
    private MazeRunnerGame game;
    private HUD hud;

    public Entity(Position position, int lives, int speed, MazeRunnerGame game, HUD hud) {
        this.position = position;
        this.lives = lives;
        this.speed = speed;
        this.game = game;
        this.hud = hud;
    }

    /**
     * Moves the entity by the given parameters
     *
     * @param direction the direction in which the entity should be moved
     * @param maze      a reference to the maze
     */
    public void moveEntity(Direction direction, Maze maze) {
        position.move(direction, speed, maze);
    }

    /**
     * Loose a life of the player
     */
    public void looseLife() {
        lives--;
        hud.loseLife();
        if (checkIfDead()) game.goToMenu(); //TODO: call the end of the game
    }

    /**
     * Helper method to check if the player is dead
     *
     * @return true when the lives are zero
     */
    private boolean checkIfDead() {
        return lives <= 0;
    }

    public Position getPosition() {
        return position;
    }

    public int getLives() {
        return lives;
    }


    public int getSpeed() {
        return speed;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
