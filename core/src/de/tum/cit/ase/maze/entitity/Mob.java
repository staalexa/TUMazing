package de.tum.cit.ase.maze.entitity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import de.tum.cit.ase.maze.map.Maze;
import de.tum.cit.ase.maze.util.Direction;
import de.tum.cit.ase.maze.util.Position;

import java.util.Random;

/**
 * Class to represent a mob
 */
public class Mob extends Entity {
    private long lastKill;

    public Mob(Position position) {
        super(position, 1, 1);
        lastKill = 0;
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
        Random r = new Random();
        int result = r.nextInt(4);
        switch (result) {
            case 0 -> moveEntity(Direction.UP, maze);
            case 1 -> moveEntity(Direction.DOWN, maze);
            case 2 -> moveEntity(Direction.RIGHT, maze);
            case 3 -> moveEntity(Direction.LEFT, maze);
        }
    }

    public long getLastKill() {
        return lastKill;
    }

    public void setLastKill(long lastKill) {
        this.lastKill = lastKill;
    }
}
