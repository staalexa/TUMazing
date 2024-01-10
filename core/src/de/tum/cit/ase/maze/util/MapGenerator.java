package de.tum.cit.ase.maze.util;

import java.util.*;

public class MapGenerator {
    private int mapsize;
    private int[][] mapData;
    private Random rand;
    private Node entry, exit;

    public MapGenerator(int mapsize) {
        rand = new Random();
        this.mapsize = mapsize;
        this.mapData = new int[mapsize][mapsize];

        genEmptyMapData(mapData);

        genMap();

        printMap();

    }

    public void genMap() {
        genOuterWall();
        genInnerWalls();
        spawnMobs();
        placeTraps();
        spawnKey();
    }

    private double distanceBetween(Node a, Node b) {
        int xDifference = Math.abs(a.getX() - b.getX());
        int yDifference = Math.abs(a.getY() - b.getY());

        return Math.sqrt(Math.pow(xDifference, 2) + Math.pow(yDifference, 2));
    }

    private void findPath() {
        Node[][] nodes = new Node[mapsize][mapsize];
        for (int i = 0; i < mapsize; i++)
            for (int j = 0; j < mapsize; j++)
                nodes[i][j] = new Node(i, j, mapData[i][j]);
        Node startNode = entry;
        Node goalNode = exit;
    }

    public static List<Node> AStar(int[][] grid, Node startNode, Node goalNode) {
        Set<Node> openSet = new HashSet<>(); // Set of nodes to be evaluated
        openSet.add(startNode);

        Map<Node, Node> cameFrom = new HashMap<>(); // Mapping of nodes and their predecessors
        Map<Node, Double> gScore = new HashMap<>(); // Map to store gScore for each node
        Map<Node, Double> fScore = new HashMap<>(); // Map to store fScore for each node

        gScore.put(startNode, 0.0); // gScore for start node is 0
        fScore.put(startNode, calculateHeuristic(startNode, goalNode)); // Initial fScore for start node

        while (!openSet.isEmpty()) {
            Node current = getNodeWithLowestFScore(openSet, fScore); // Get node with lowest fScore

            if (current.equals(goalNode)) {
                return reconstructPath(cameFrom, current); // Goal node reached, reconstruct path
            }

            openSet.remove(current);

            for (Node neighbor : getNeighbors(current, grid)) {
                double tentative_gScore = gScore.getOrDefault(current, Double.POSITIVE_INFINITY)
                        + calculateDistance(current, neighbor); // Calculate tentative gScore

                if (tentative_gScore < gScore.getOrDefault(neighbor, Double.POSITIVE_INFINITY)) {
                    cameFrom.put(neighbor, current);
                    gScore.put(neighbor, tentative_gScore);
                    fScore.put(neighbor, tentative_gScore + calculateHeuristic(neighbor, goalNode));

                    openSet.add(neighbor); // Add neighbor to openSet
                }
            }
        }

        return null; // Open set is empty but goal was not reached
    }

    private static Node getNodeWithLowestFScore(Set<Node> openSet, Map<Node, Double> fScore) {
        Node nodeWithLowestFScore = null;
        double lowestFScore = Double.POSITIVE_INFINITY;

        // Iterate through each node in the open set
        for (Node node : openSet) {
            // Check if the node's fScore is lower than the lowest found so far
            double nodeFScore = fScore.getOrDefault(node, Double.POSITIVE_INFINITY);
            if (nodeFScore < lowestFScore) {
                lowestFScore = nodeFScore;
                nodeWithLowestFScore = node;
            }
        }

        return nodeWithLowestFScore; // Return node with the lowest fScore
    }

    // Helper method to calculate heuristic (distance estimation from node to goal)
    private static double calculateHeuristic(Node node, Node goalNode) {
        // Implement your heuristic calculation here (e.g., Euclidean distance, Manhattan distance, etc.)
        // Return an estimated distance from 'node' to 'goalNode'
    }

    // Helper method to reconstruct the path from 'cameFrom' map
    private static List<Node> reconstructPath(Map<Node, Node> cameFrom, Node current) {
        List<Node> path = new ArrayList<>();
        path.add(current);

        while (cameFrom.containsKey(current)) {
            current = cameFrom.get(current);
            path.add(0, current); // Prepend current node to the path
        }

        return path;
    }

    // Helper method to get neighbors of a node in the grid
    private static List<Node> getNeighbors(Node node, int[][] grid) {
        // Implement logic to get neighbors of 'node' from the 'grid'
        // Return a list of neighboring nodes
    }

    // Helper method to calculate distance between nodes
    private static double calculateDistance(Node node1, Node node2) {
        // Implement your logic to calculate distance between 'node1' and 'node2'
        // Return the calculated distance
    }

    /**
     * Fills a 2d array with empty fields
     *
     * @param mapData an int[][]
     */
    private void genEmptyMapData(int[][] mapData) {
        for (int[] row : mapData)
            Arrays.fill(row, 9);
    }

    /**
     * create outer walls all around the map, and create the entry and exit doors
     */
    private void genOuterWall() {
        for (int i = 0; i < mapsize; i++) {
            mapData[0][i] = 0;
            mapData[i][0] = 0;
            mapData[mapsize - 1][i] = 0;
            mapData[i][mapsize - 1] = 0;
        }

        createDoor(1);

        createDoor(2);
    }

    /**
     * create one door
     *
     * @param type 1 for entry and 2 for exit
     */
    private void createDoor(int type) {
        int x;
        int y;
        switch (rand.nextInt(4)) {
            case 0:
                x = 0;
                y = rand.nextInt(0, mapsize - 2);
                break;
            case 1:
                y = 0;
                x = rand.nextInt(0, mapsize - 2);
                break;
            case 2:
                x = mapsize - 1;
                y = rand.nextInt(0, mapsize - 2);
                break;
            case 3:
                y = mapsize - 1;
                x = rand.nextInt(0, mapsize - 2);
                break;
            default:
                x = 0;
                y = 0;
        }
        mapData[x][y] = type;
        if (type == 1) entry = new Node(x, y, type);
        else exit = new Node(x, y, type);
    }

    public void genInnerWalls() {
        genRandomMapObject(3, 0);
    }

    public void spawnMobs() {
        genRandomMapObject(20, 4);
    }

    public void placeTraps() {
        genRandomMapObject(15, 3);
    }

    public void spawnKey() {
        int x = rand.nextInt(0, mapsize - 2);
        int y = rand.nextInt(0, mapsize - 2);

        if (mapData[x][y] == 9) mapData[x][y] = 5;
        else spawnKey();
    }


    public void genRandomMapObject(int possibility, int objectType) {
        for (int i = 0; i < mapsize; i++)
            for (int j = 0; j < mapsize; j++)
                if (rand.nextInt(possibility) == 0)
                    if (mapData[i][j] == 9) mapData[i][j] = objectType;
    }

    /**
     * For testing, prints the map into console
     */
    public void printMap() {
        for (int y = mapData[1].length - 1; y >= 0; y--) {
            for (int x = 0; x < mapData[0].length; x++) {
                switch (mapData[x][y]) {
                    case 0 -> System.out.print("x");
                    case 1 -> System.out.print("E");
                    case 2 -> System.out.print("D");
                    case 3 -> System.out.print("F");
                    case 4 -> System.out.print("M");
                    case 5 -> System.out.print("K");
                    default -> System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    public void printMapData() {
        for (int y = mapData[1].length - 1; y >= 0; y--) {
            for (int x = 0; x < mapData[0].length; x++) {
                switch (mapData[x][y]) {
                    case 0 -> System.out.println(x + "," + y + "=" + 0);
                    case 1 -> System.out.println(x + "," + y + "=" + 1);
                    case 2 -> System.out.println(x + "," + y + "=" + 2);
                    case 3 -> System.out.println(x + "," + y + "=" + 3);
                    case 4 -> System.out.println(x + "," + y + "=" + 4);
                    case 5 -> System.out.println(x + "," + y + "=" + 5);
                }
            }
        }
    }

    public int[][] getMapData() {
        return mapData;
    }
}
