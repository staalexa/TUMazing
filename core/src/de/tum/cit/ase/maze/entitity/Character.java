package de.tum.cit.ase.maze.entitity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import de.tum.cit.ase.maze.MazeRunnerGame;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.tum.cit.ase.maze.util.LivesManager;

import java.util.function.BiConsumer;

public class Character {
    private float x, y;
    private boolean hasKey;
    private int lives;
    private int speed;

    public Character(float x, float y) {
        this.x = x;
        this.y = y;
        hasKey = false;
        lives = LivesManager.getMaxLives();
        speed = 2;
    }

    public BiConsumer<Float, MazeRunnerGame> drawCharacter = (delta, game1) ->
            game1.getSpriteBatch().draw(game1.getCharacterDownAnimation().getKeyFrame(delta), x, y, 64, 128);

    public void moveCharacter() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) incrementY();
        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) decrementY();
        if (Gdx.input.isKeyJustPressed(Input.Keys.D)) incrementX();
        if (Gdx.input.isKeyJustPressed(Input.Keys.A)) decrementX();

        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) incrementY();
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) decrementY();
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) incrementX();
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) decrementX();
    }

    public int[] getPosition() {
        int x = (int) Math.floor(this.x);
        int y = (int) Math.floor(this.y);
        return new int[]{x, y};
    }

    public void incrementX() {
        x += 10;
    }

    public void incrementY() {
        y += 10;
    }

    public void decrementX() {
        x -= 10;
    }

    public void decrementY() {
        y -= 10;
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
