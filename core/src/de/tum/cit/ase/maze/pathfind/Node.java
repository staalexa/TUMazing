package de.tum.cit.ase.maze.pathfind;

import de.tum.cit.ase.maze.util.Position;

import java.util.List;

/**
 * This class represents a Node in the {@link Grid}. It contains the {@link Position} of the Node, if it is an obstacle, the gCost, hCost and fCost of the Node, its parent Node and its neighbors.
 */
public class Node implements Comparable<Node>{

    private Position position;
    private boolean isObstacle;
    private float gCost, hCost, fCost;
    private Node parent;
    private List<Node> neighbors;

    /**
     * Constructor of the Node. Initializes its {@link Position} and if it is an obstacle.
     * The Node's neighbor nodes are initially set to null, as they are not yet discovered.
     * @param position {@link Position} of the Node
     * @param isObstacle {@link Boolean} value if the Node represents an obstacle.
     */
    public Node(Position position, boolean isObstacle) {
        this.position = position;
        this.isObstacle = isObstacle;
        this.neighbors = null;
    }

    public void calculateFCost() {
        fCost = gCost + hCost;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public boolean isObstacle() {
        return isObstacle;
    }

    public void setObstacle(boolean obstacle) {
        isObstacle = obstacle;
    }

    public float getGCost() {
        return gCost;
    }

    public void setGCost(float gCost) {
        this.gCost = gCost;
    }

    public void setHCost(float hCost) {
        this.hCost = hCost;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public List<Node> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(List<Node> neighbors) {
        this.neighbors = neighbors;
    }


    /**
     * Compares this Node to another Node. The comparison is based on the fCost and hCost of the Nodes.
     * @param other the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(Node other) {
        int fCostDiff = Float.compare(this.fCost, other.fCost);

        if (fCostDiff != 0) return fCostDiff; // lowest fCost (shortest distance) is preferred.

        return Float.compare(this.hCost, other.hCost); // if fCost is equal, lowest hCost (shortest distance to target) is preferred.
    }

}