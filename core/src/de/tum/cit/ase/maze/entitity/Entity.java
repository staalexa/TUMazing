package de.tum.cit.ase.maze.entitity;

import de.tum.cit.ase.maze.MazeRunnerGame;
import de.tum.cit.ase.maze.hud.HUD;
import de.tum.cit.ase.maze.map.Maze;
import de.tum.cit.ase.maze.util.Direction;
import de.tum.cit.ase.maze.util.GameState;
import de.tum.cit.ase.maze.util.Position;

/**
 * Template for entities
 */
public abstract class Entity {
    private final Position position;
    private int lives;
    private final int speed;
    private final MazeRunnerGame game;
    private final HUD hud;

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
     *
     * @return true if the person is still alive after, false if not
     */
    public boolean looseLife() {
        lives--;
        hud.loseLife();
        if (checkIfDead()) {
            game.goToEnd(GameState.DEATH);
            return false;
        } else return true;
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
}
