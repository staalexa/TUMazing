package de.tum.cit.ase.maze.util;

import de.tum.cit.ase.maze.map.Maze;

import java.util.Objects;

/**
 * Position class which represents a position on the map in pixels
 */
public class Position {
    private int x, y;

    /**
     * a constructor of the Position, which takes the x and the y values, and also a scale variable
     *
     * @param x     x coordinates
     * @param y     y coordinates
     * @param scale it is supposed to be true when the value you pass in is the map value, and it is needed to be scaled by 16
     */
    public Position(int x, int y, boolean scale) {
        if (scale) {
            this.x = x * 16;
            this.y = y * 16;
        } else {
            this.x = x;
            this.y = y;
        }
    }

    /**
     * a constructor of the Position, which takes the x and the y values, and also a scale variable
     *
     * @param x     x coordinates
     * @param y     y coordinates
     * @param scale it is supposed to be true when the value you pass in is the map value, and it is needed to be scaled by 16
     */
    public Position(float x, float y, boolean scale) {
        if (scale) {
            this.x = Math.round(x * 16);
            this.y = Math.round(y * 16);
        } else {
            this.x = Math.round(x);
            this.y = Math.round(y);
        }
    }

    /**
     * Moves the current location by given values
     *
     * @param direction UP, DOWN, LEFT, RIGHT
     * @param speed     The amount of pixels the entity needs to move
     * @param maze      an instance of the current maze
     */
    public void move(Direction direction, int speed, Maze maze) {
        switch (direction) {
            case UP:
                if (!maze.isWall(x, y + speed + 10))
                    y += speed;
                break;
            case DOWN:
                if (!maze.isWall(x, y - speed))
                    y -= speed;
                break;
            case LEFT:
                if (!maze.isWall(x - speed, y))
                    x -= speed;
                break;
            case RIGHT:
                if (!maze.isWall(x + speed + 10, y))
                    x += speed;
                break;
        }
    }

    /**
     * Calculates threashold distance between two positions
     * @param other Another position
     * @return true if the two positions have max the range 5 between them (translates to when the positions are the same)
     */
    public boolean isWithinRange(Position other) {
        float xDifference = Math.abs(this.getX() - other.getX());
        float yDifference = Math.abs(this.getY() - other.getY());

        return xDifference <= 5 && yDifference <= 5;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
