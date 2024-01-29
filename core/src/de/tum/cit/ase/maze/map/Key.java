package de.tum.cit.ase.maze.map;

import de.tum.cit.ase.maze.GameScreen;
import de.tum.cit.ase.maze.util.Position;

/**
 * Handles Key collecting
 */
public class Key {
    private Position position;
    private boolean pickedUp;

    /**
     * Set the {@link Key}s {@link Position}. Only used during game loading
     * @param position
     */
    public void setPosition(Position position) {
        this.position = position;
        pickedUp = false;
    }

    /**
     * Pickup the key. Set the key to the player and toggle the view on the hud
     * @param screen Current {@link GameScreen}
     * @return true if key was picked up, false if the {@link Character} already has the {@link Key}
     */
    public boolean pickUp(GameScreen screen) {
        if (screen.getCharacter().hasKey()) return false; // check if key already collected

        screen.getHud().collectedKey(true);
        screen.getCharacter().setHasKey(true);
        screen.getMaze().removeKey(position);
        pickedUp = true;
        return true;
    }

    public Position getPosition() {
        return position;
    }

    public boolean isPickedUp() {
        return pickedUp;
    }
}
