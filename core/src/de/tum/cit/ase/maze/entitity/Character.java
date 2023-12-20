package de.tum.cit.ase.maze.entitity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import de.tum.cit.ase.maze.MazeRunnerGame;
import de.tum.cit.ase.maze.map.Maze;
import de.tum.cit.ase.maze.util.Direction;
import de.tum.cit.ase.maze.util.Position;

import java.util.function.BiConsumer;

/**
 * This Class makes a Character move and counts the lives and all the character statuses
 */
public class Character {
    private Position position;
    private boolean hasKey;
    private int lives;
    private int speed;

    public Character(Position position) {
        this.position = position;
        hasKey = false;
        lives = 3;
        speed = 1;
    }

    /**
     * Draw a character with the drawSomething function, draws the character with the walking effect
     *
     * @param sinusInput the delta value added together through the game
     * @param game reference of the entire game, to be called
     */
    public BiConsumer<Float, MazeRunnerGame> drawCharacter = (sinusInput, game) ->
            game.getSpriteBatch().draw(
                    game.getCharacterDownAnimation().getKeyFrame(sinusInput, true), position.getX(), position.getY(), 12, 25
            );

    /**
     * Move the character based on the keypad input, works with the arrows and the WASD
     *
     * @param maze the maze loader class, so we can check whether there is a wall
     */
    public void moveCharacter(Maze maze) {
        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP))
            position.move(Direction.UP, speed, maze);
        else if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN))
            position.move(Direction.DOWN, speed, maze);
        else if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            position.move(Direction.RIGHT, speed, maze);
        else if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT))
            position.move(Direction.LEFT, speed, maze);
    }

    /**
     * gets the position of the character
     *
     * @return the position
     */
    public Position getPosition() {
        return position;
    }

    public boolean hasKey() {
        return hasKey;
    }

    public void setHasKey(boolean hasKey) {
        this.hasKey = hasKey;
    }

    @Override
    public String toString() {
        return "Character{" +
                "x=" + position.getX() +
                ", y=" + position.getY() +
                ", hasKey=" + hasKey +
                ", lives=" + lives +
                ", speed=" + speed +
                '}';
    }
}
