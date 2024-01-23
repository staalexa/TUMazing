package de.tum.cit.ase.maze.entitity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import de.tum.cit.ase.maze.MazeRunnerGame;
import de.tum.cit.ase.maze.hud.HUD;
import de.tum.cit.ase.maze.map.Maze;
import de.tum.cit.ase.maze.util.Direction;
import de.tum.cit.ase.maze.util.Position;

import java.time.LocalTime;
import java.util.function.BiConsumer;

import static java.time.temporal.ChronoUnit.MILLIS;

/**
 * This Class makes a Character move and counts the lives and all the character statuses
 */
public class Character extends Entity {
    private static final int MAX_LIVES = 3;
    private boolean hasKey;

    private Direction direction;
    private LocalTime lastMoved;

    public Character(Position position, MazeRunnerGame game, HUD hud) {
        super(position, MAX_LIVES, 1, game, hud);
        hasKey = false;
        direction = Direction.DOWN;
        lastMoved = LocalTime.MIN;
    }

    /**
     * Draw a character with the drawSomething function
     * It also turns the character in the right direction, and stops the loop if the character is not moving currently
     *
     * @param sinusInput the delta value added together through the game
     * @param game reference of the entire game, to be called
     */
    public BiConsumer<Float, MazeRunnerGame> drawCharacter = (sinusInput, game) -> {
        boolean looping = MILLIS.between(lastMoved, LocalTime.now()) < 50;
        switch (direction) {
            case UP:
                game.getSpriteBatch().draw(
                        game.getCharacterWalkingUpAnimation().getKeyFrame(sinusInput, looping), getPosition().getX(), getPosition().getY(), 12, 25
                );
                break;
            case DOWN:
                game.getSpriteBatch().draw(
                        game.getCharacterWalkingDownwardsAnimation().getKeyFrame(sinusInput, looping), getPosition().getX(), getPosition().getY(), 12, 25
                );
                break;
            case LEFT:
                game.getSpriteBatch().draw(
                        game.getCharacterWalkingLeftAnimation().getKeyFrame(sinusInput, looping), getPosition().getX(), getPosition().getY(), 12, 25
                );
                break;
            case RIGHT:
                game.getSpriteBatch().draw(
                        game.getCharacterWalkingRightAnimation().getKeyFrame(sinusInput, looping), getPosition().getX(), getPosition().getY(), 12, 25
                );
                break;
        }
    };


    /**
     * Move the character based on the keypad input, works with the arrows and the WASD
     * Also sets the direction and the lastMoved variables for the direction and stopping effect
     *
     * @param maze the maze loader class, so we can check whether there is a wall
     */
    public void moveCharacter(Maze maze) {
        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
            direction = Direction.UP;
            lastMoved = LocalTime.now();
            moveEntity(Direction.UP, maze);
        } else if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            direction = Direction.DOWN;
            lastMoved = LocalTime.now();
            moveEntity(Direction.DOWN, maze);
        } else if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            direction = Direction.RIGHT;
            lastMoved = LocalTime.now();
            moveEntity(Direction.RIGHT, maze);
        } else if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            direction = Direction.LEFT;
            lastMoved = LocalTime.now();
            moveEntity(Direction.LEFT, maze);
        }
    }

    public boolean hasKey() {
        return hasKey;
    }

    public void setHasKey(boolean hasKey) {
        this.hasKey = hasKey;
    }

    public static int getMaxLives() {
        return MAX_LIVES;
    }
}
