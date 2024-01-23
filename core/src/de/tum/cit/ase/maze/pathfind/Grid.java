package de.tum.cit.ase.maze.pathfind;

import de.tum.cit.ase.maze.util.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a Node in the {@link Grid}. It contains the {@link Position} of the Node, if it is an obstacle, the gCost, hCost and fCost of the Node, its parent Node and its neighbors.
 */
public class Grid {
    private Node[][] grid;
    private int width, height;

    /**
     * Constructor of the Grid. Initializes its width and height.
     *
     * @param size Size of the Grid
     */
    public Grid(int size) {
        this.width = size;
        this.height = size;
        grid = new Node[width][height];
    }

    /**
     * Fills the grid with nodes and their neighbors
     *
     * @param mapData 2d array of 0s and 1s, where 0 represents a free space and 1 represents an obstacle
     */
    public void fillGrid(int[][] mapData) {
        // Initialize nodes
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (mapData[x][y] == 0 || mapData[x][y] == 3)
                    grid[x][y] = new Node(new Position(x, y, false), true);
                else grid[x][y] = new Node(new Position(x, y, false), false);
            }
        }

        // Initialize neighbors
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                grid[x][y].setNeighbors(calculateNeighbors(x, y));
            }
        }
    }

    /**
     * Calculates the neighbors of a {@link Node} at a given position
     *
     * @param x x coordinate of the {@link Node}
     * @param y y coordinate of the {@link Node}
     * @return {@link List<Node>} of neighbors
     */
    private List<Node> calculateNeighbors(int x, int y) {
        List<Node> neighbors = new ArrayList<>();
        int[] dx = {0, 0, 1, -1}; // Horizontal
        int[] dy = {1, -1, 0, 0}; // Vertical

        // Check all 4 neighbors
        for (int i = 0; i < 4; i++) {
            int checkX = x + dx[i];
            int checkY = y + dy[i];

            // Check if the neighbor is within the grid
            if (checkX >= 0 && checkX < width && checkY >= 0 && checkY < height) {
                neighbors.add(grid[checkX][checkY]);
            }
        }

        return neighbors;
    }

    /**
     * Gets the {@link Node} at a given position
     *
     * @param x x coordinate of the {@link Node}
     * @param y y coordinate of the {@link Node}
     * @return {@link Node} at the given position
     */
    public Node getNode(int x, int y) {
        return grid[x][y];
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
