package de.tum.cit.ase.maze.entitity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import de.tum.cit.ase.maze.MazeRunnerGame;
import de.tum.cit.ase.maze.map.MazeLoader;
import java.util.function.BiConsumer;

import static de.tum.cit.ase.maze.map.MazeLoader.convertToMapPosition;

/**
 * This Class makes a Character move and counts the lives and all the character statuses
 */
public class Character {
    private float x, y;
    private boolean hasKey;
    private int lives;
    private int speed;

    public Character(float x, float y) {
        this.x = x;
        this.y = y;
        hasKey = false;
        lives = 3;
        speed = 3;
    }

    /**
     * Draw a character with the drawSomething function, draws the character with the walking effect
     *
     * @param sinusInput the delta value added together through the game
     * @param game reference of the entire game, to be called
     */
    public BiConsumer<Float, MazeRunnerGame> drawCharacter = (sinusInput, game) ->
            game.getSpriteBatch().draw(
                    game.getCharacterDownAnimation().getKeyFrame(sinusInput, true), x, y, 64, 128
            );

    /**
     * Move the character based on the keypad input, works with the arrows and the WASD
     *
     * @param mazeLoader the maze loader class, so we can check whether there is a wall
     */
    public void moveCharacter(MazeLoader mazeLoader) {
        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP))
            if (!mazeLoader.isWall(new int[]{convertToMapPosition(x), convertToMapPosition(incrementY())}))
                y = incrementY();
        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN))
            if (!mazeLoader.isWall(new int[]{convertToMapPosition(x), convertToMapPosition(decrementY())}))
                y = decrementY();
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            if (!mazeLoader.isWall(new int[]{convertToMapPosition(incrementX()), convertToMapPosition(y)}))
                x = incrementX();
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT))
            if (!mazeLoader.isWall(new int[]{convertToMapPosition(decrementX()), convertToMapPosition(y)}))
                x = decrementX();
    }

    /**
     * gets the position of the character
     *
     * @return the position
     */
    public int[] getPosition() {
        int x = (int) Math.floor(this.x);
        int y = (int) Math.floor(this.y);
        return new int[]{x, y};
    }

    /**
     * move the character on the x axes
     *
     * @return the incremented value
     */
    public float incrementX() {
        return x + speed;
    }

    /**
     * move the character on the y axes
     *
     * @return the incremented value
     */
    public float incrementY() {
        return y + speed;
    }

    /**
     * move the character on the x axes
     *
     * @return the decremented value
     */
    public float decrementX() {
        return x - speed;
    }

    /**
     * move the character on the y axes
     *
     * @return the decremented value
     */
    public float decrementY() {
        return y - speed;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Character{" +
                "x=" + x +
                ", y=" + y +
                ", hasKey=" + hasKey +
                ", lives=" + lives +
                ", speed=" + speed +
                '}';
    }
}
