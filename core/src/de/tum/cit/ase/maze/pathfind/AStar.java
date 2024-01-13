package de.tum.cit.ase.maze.pathfind;

import de.tum.cit.ase.maze.util.Position;
import java.util.*;

/**
 * This class implements the A* searching algorithm for pathfinding. It implements adapted code from <a href="https://en.wikipedia.org/wiki/A*_search_algorithm">Wikipedia</a>
 */
public class AStar {

    /**
     * implementation of A* searching algorithm. We use the distance as the "cost" of the heuristic function h
     * <br><code>gCost(n)</code> stands for the shortest path from start to n known Nodes
     * <br><code>hCost(n)</code> returns an approximation of the remaining distance to the final node, this is used to guide the search towards the target node
     * <br><code>fCost(n)</code> returns the estimated total cost from source to target through node n. This is used to prioritize nodes for exploration.
     * @param grid Grid of {@link Node}
     * @param start The {@link Position} of the starting Point.
     * @param end The {@link Position} of the destination Point.
     * @return Returns a {@link List<Node>} with every {@link Node} of the given path from beginning to end.
     */
    public static List<Node> findPath(Grid grid, Position start, Position end) {
        Node startNode = getNodeFromPosition(grid, start);
        Node endNode = getNodeFromPosition(grid, end);

        PriorityQueue<Node> openList = new PriorityQueue<>();
        Set<Node> closedList = new HashSet<>();

        openList.add(startNode);

        while (!openList.isEmpty()) {
            Node currentNode = openList.poll();

            closedList.add(currentNode);

            if (currentNode.equals(endNode)) return retracePath(startNode, endNode);

            // Check all neighbors of the current node
            for (Node neighbor : getNeighbors(currentNode)) {
                if (closedList.contains(neighbor) || neighbor.isObstacle()) continue; // if the neighbor has already been visited or is an obstacle

                // Calculate the new cost to the neighbor
                float newCostToNeighbor = currentNode.getGCost() + calculateDistance(currentNode, neighbor);

                // If the new cost is lower than the current cost to the neighbor or the neighbor is not in the open list
                if (newCostToNeighbor < neighbor.getGCost() || !openList.contains(neighbor)) {
                    neighbor.setGCost(newCostToNeighbor); // set the new cost to the neighbor
                    neighbor.setHCost(calculateDistance(neighbor, endNode)); // set the heuristic cost to the neighbor
                    neighbor.calculateFCost(); // calculate the total cost to the neighbor
                    neighbor.setParent(currentNode); // set the parent of the neighbor to the current node

                    // If the neighbor is not in the open list, add it
                    if (!openList.contains(neighbor)) openList.add(neighbor);
                }
            }
        }
        return null; // returns empty when no path is found
    }

    /**
     * Helper method: After the destination {@link Node} is discovered, the path from the starting point to the ending point is retraced.
     * @param startNode The {@link Node} at the beginning {@link Position}
     * @param endNode The {@link Node} at the destination's {@link Position}
     * @return Returns an ordered {@link List<Node>} of all {@link Node}s of the path
     */
    private static List<Node> retracePath(Node startNode, Node endNode) {
        List<Node> path = new ArrayList<>();
        Node currentNode = endNode;

        while (!currentNode.equals(startNode)) {
            path.add(currentNode);
            currentNode = currentNode.getParent();
        }

        Collections.reverse(path);
        return path;
    }

    /**
     * Calculates the distance between two {@link Node}s using Manhattan distance calculation (not considering diagonal distance).
     * @param nodeA {@link Node} 1
     * @param nodeB {@link Node} 2
     * @return Returns the distance between two {@link Node}s
     */
    private static float calculateDistance(Node nodeA, Node nodeB) {
        int dx = Math.abs(nodeA.getPosition().getX() - nodeB.getPosition().getX());
        int dy = Math.abs(nodeA.getPosition().getY() - nodeB.getPosition().getY());

        return dx + dy;
    }


    /**
     * Gets the Node at a given {@link Position} on a {@link Grid}.
     * @param grid Grid of {@link Node}
     * @param position {@link Position} to find in the {@link Grid}
     * @return returns a {@link Node} at the given {@link Position}
     */
    private static Node getNodeFromPosition(Grid grid, Position position) {
        int x = position.getX();
        int y = position.getY();

        x = Math.max(0, Math.min(x, grid.getWidth() - 1)); // Ensure x is within grid bounds
        y = Math.max(0, Math.min(y, grid.getHeight() - 1)); // Ensure y is within grid bounds
        return grid.getNode(x, y);
    }

    /**
     * Getter to get neighbor {@link Node}
     * @param node Source {@link Node}
     * @return Returns a {@link List<Node>} with all neighbors of a given {@link Node}
     */
    private static List<Node> getNeighbors(Node node) {
        return node.getNeighbors();
    }
}
