package de.tum.cit.ase.maze.entitity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import de.tum.cit.ase.maze.MazeRunnerGame;
import de.tum.cit.ase.maze.map.Maze;
import de.tum.cit.ase.maze.util.Direction;
import de.tum.cit.ase.maze.util.Position;

import java.util.Random;

/**
 * The Mob class represents the enemy in the game. It is a subclass of {@link Entity}.
 */
public class Mob extends Entity {
    private long lastKill;
    private final int moveCooldown = 5;
    private int currentCooldown;
    private Direction currentDirection;
    private final Random rand = new Random();

    public Mob(Position position, MazeRunnerGame game) {
        super(position, 1, 1, game, null);
        lastKill = 0;
        currentDirection = Direction.UP;
    }

    /**
     * draw the mob
     *
     * @param maze         reference of the {@link Maze}
     * @param spriteBatch  reference of the sprite batch
     * @param enemyTexture texture of the {@link Mob}
     */
    public void drawMob(Maze maze, SpriteBatch spriteBatch, TextureRegion enemyTexture) {
        maze.drawTexture(spriteBatch, getPosition().getX(), getPosition().getY(), enemyTexture);
    }

    /**
     * Move the mob. The mob will move in a random direction and will change direction if it hits a wall.
     *
     * @param maze reference of the whole {@link Maze}
     */
    public void moveMob(Maze maze) {

        if(currentCooldown-- > 0) return;

        // Check if the sides are free during movement and change direction accordingly
        checkAndChangeDirection(maze);

        boolean canGoLeft = !maze.isWall(getPosition().getX() - 15, getPosition().getY());
        boolean canGoRight = !maze.isWall(getPosition().getX() + 15, getPosition().getY());
        boolean canGoUp = !maze.isWall(getPosition().getX(), getPosition().getY() + 15);
        boolean canGoDown = !maze.isWall(getPosition().getX(), getPosition().getY() - 15);

        // Change direction only if cooldown is over and any side is free
        if (canGoUp || canGoDown || canGoLeft || canGoRight) {
            do {
                Random r = new Random();
                int result = r.nextInt(4);
                switch (result) {
                    case 0 -> currentDirection = Direction.UP;
                    case 1 -> currentDirection = Direction.DOWN;
                    case 2 -> currentDirection = Direction.LEFT;
                    case 3 -> currentDirection = Direction.RIGHT;
                }
            } while (!((currentDirection == Direction.UP && canGoUp) ||
                    (currentDirection == Direction.DOWN && canGoDown) ||
                    (currentDirection == Direction.LEFT && canGoLeft) ||
                    (currentDirection == Direction.RIGHT && canGoRight)));
        }

        for (int i = 0; i < 3; i++) {
            moveEntity(currentDirection, maze);
        }

        currentCooldown = moveCooldown;
    }

    /**
     * Check if the mob can change direction and change direction if possible
     *
     * @param maze reference of the whole {@link Maze}
     */
    private void checkAndChangeDirection(Maze maze) {
        // Check if the sides are free during movement and change direction accordingly
        boolean canGoLeft = !maze.isWall(getPosition().getX() - 15, getPosition().getY());
        boolean canGoRight = !maze.isWall(getPosition().getX() + 15, getPosition().getY());
        boolean canGoUp = !maze.isWall(getPosition().getX(), getPosition().getY() + 15);
        boolean canGoDown = !maze.isWall(getPosition().getX(), getPosition().getY() - 15);

        // Change direction only if cooldown is over and any side is free
        if (!((currentDirection == Direction.UP && canGoUp) ||
                (currentDirection == Direction.DOWN && canGoDown) ||
                (currentDirection == Direction.LEFT && canGoLeft) ||
                (currentDirection == Direction.RIGHT && canGoRight))) {
            currentDirection = Direction.values()[rand.nextInt(4)];
            currentCooldown = 0; // Reset cooldown to change direction immediately
        }
    }

    public long getLastKill() {
        return lastKill;
    }

    public void setLastKill(long lastKill) {
        this.lastKill = lastKill;
    }
}
