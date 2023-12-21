package de.tum.cit.ase.maze.entitity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import de.tum.cit.ase.maze.MazeRunnerGame;
import de.tum.cit.ase.maze.hud.HUD;
import de.tum.cit.ase.maze.map.Maze;
import de.tum.cit.ase.maze.util.Direction;
import de.tum.cit.ase.maze.util.Position;

import java.util.function.BiConsumer;

/**
 * This Class makes a Character move and counts the lives and all the character statuses
 */
public class Character extends Entity {
    private static final int MAX_LIVES = 3;
    private boolean hasKey;

    public Character(Position position, MazeRunnerGame game, HUD hud) {
        super(position, MAX_LIVES, 1, game, hud);
        hasKey = false;
    }

    /**
     * Draw a character with the drawSomething function, draws the character with the walking effect
     *
     * @param sinusInput the delta value added together through the game
     * @param game reference of the entire game, to be called
     */
    public BiConsumer<Float, MazeRunnerGame> drawCharacter = (sinusInput, game) ->
            game.getSpriteBatch().draw(
                    game.getCharacterDownAnimation().getKeyFrame(sinusInput, true), getPosition().getX(), getPosition().getY(), 12, 25
            );

    /**
     * Move the character based on the keypad input, works with the arrows and the WASD
     *
     * @param maze the maze loader class, so we can check whether there is a wall
     */
    public void moveCharacter(Maze maze) {
        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP))
            moveEntity(Direction.UP, maze);
        else if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN))
            moveEntity(Direction.DOWN, maze);
        else if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            moveEntity(Direction.RIGHT, maze);
        else if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT))
            moveEntity(Direction.LEFT, maze);
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
