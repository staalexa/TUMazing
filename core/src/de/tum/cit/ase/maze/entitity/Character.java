package de.tum.cit.ase.maze.entitity;

import de.tum.cit.ase.maze.MazeRunnerGame;

public class Character {
    private final MazeRunnerGame game;
    private float x, y;
    private boolean hasKey;
    private int lives;
    private int speed;


    public Character(float x, float y, MazeRunnerGame game) {
        this.game = game;
        this.x = x;
        this.y = y;
        hasKey = false;
        lives = 3;
        speed = 2;
    }

    public void drawCharacter(float delta){
        game.getSpriteBatch().begin();
        game.getSpriteBatch().draw(game.getCharacterDownAnimation().getKeyFrame(delta),  x,  y,  64,
                 128);
        game.getSpriteBatch().end();
    }

    public int[] getPosition(){
        int x = (int) Math.floor(this.x);
        int y = (int) Math.floor(this.y);
        return new int[]{x, y};
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
