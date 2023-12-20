package de.tum.cit.ase.maze.map;

import de.tum.cit.ase.maze.GameScreen;
import de.tum.cit.ase.maze.util.Position;

/**
 * Handles Key collecting
 */
public class Key {
    private Position position;

    /**
     * Set the keys position. Only used during game loading
     * @param position
     */
    public void setPosition(Position position) {
        this.position = position;
        System.out.println("KEY POS: " + position.getX() + " " + position.getY());
    }

    //TODO check why this does not work from game.getGameScreen
    /**
     * Pickup the key. Set the key to the player and toggle the view on the hud
     * @param screen Current GameScreen
     * @return true if key was picked up, false if the character already has the key
     */
    public boolean pickUp(GameScreen screen) {
        if (screen.getCharacter().hasKey()) return false; // check if key already collected

        screen.getHud().collectedKey(true);
        screen.getCharacter().setHasKey(true);
        screen.getMaze().removeKey(position);
        return true;
    }

    public Position getPosition() {
        return position;
    }
}
