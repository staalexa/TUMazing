package de.tum.cit.ase.maze.entitity;

import de.tum.cit.ase.maze.util.Position;

/**
 * Template for entities
 */
public abstract class Entity {
    private Position position;
    private int lives;
    private int speed;

    public abstract void moveEntity();

    //TODO as discussed
}
