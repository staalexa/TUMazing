package de.tum.cit.ase.maze.entitity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import de.tum.cit.ase.maze.MazeRunnerGame;
import de.tum.cit.ase.maze.map.Maze;
import de.tum.cit.ase.maze.util.Direction;
import de.tum.cit.ase.maze.util.Position;

import java.util.Random;

/**
 * Class to represent a mob
 */
public class Mob extends Entity {
    private long lastKill;
    private final int moveCooldown = 5;
    private int currentCooldown;
    private Direction currentDirection;

    public Mob(Position position, MazeRunnerGame game) {
        super(position, 1, 1, game, null);
        lastKill = 0;
        currentDirection = Direction.UP;
    }

    /**
     * draw the mob
     *
     * @param maze         reference of the maze
     * @param spriteBatch  reference of the sprite batch
     * @param enemyTexture texture of the mob
     */
    public void drawMob(Maze maze, SpriteBatch spriteBatch, TextureRegion enemyTexture) {
        maze.drawTexture(spriteBatch, getPosition().getX(), getPosition().getY(), enemyTexture);
    }

    /**
     * Move the mob in random directions
     *
     * @param maze reference of the whole maze
     */
    public void moveMob(Maze maze) {

        if(currentCooldown-- > 0) return;

        Random r = new Random();
        int result = r.nextInt(4);
        switch (result) {
            case 0 -> currentDirection = Direction.UP;
            case 1 -> currentDirection = Direction.DOWN;
            case 2 -> currentDirection = Direction.LEFT;
            case 3 -> currentDirection = Direction.RIGHT;
        }

        for (int i = 0; i < 3; i++)
            moveEntity(currentDirection, maze);

        currentCooldown = moveCooldown;
    }

    public long getLastKill() {
        return lastKill;
    }

    public void setLastKill(long lastKill) {
        this.lastKill = lastKill;
    }
}
