package de.tum.cit.ase.maze.util;

import de.tum.cit.ase.maze.pathfind.AStar;
import de.tum.cit.ase.maze.pathfind.Grid;
import de.tum.cit.ase.maze.pathfind.Node;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class generates a random map with the following properties:
 * <ul>
 *     <li>The map is surrounded by walls</li>
 *     <li>The map has an entry and an exit door</li>
 *     <li>The map has a valid path from the entry to the exit</li>
 *     <li>The map has a valid path from the entry to the key</li>
 *     <li>The map has a valid path from the key to the exit</li>
 * </ul>
 */
public class MapGenerator {
    private final int mapSize;
    private final int[][] mapData;
    private final Random rand;
    private Position entry, exit, key;
    private Grid grid;

    /**
     * Constructor of the MapGenerator. Initializes the mapSize and the mapData.
     * @param mapSize the size of the map. We assume that the map is a square.
     */
    public MapGenerator(int mapSize) {
        rand = ThreadLocalRandom.current();
        this.mapSize = mapSize;
        this.mapData = new int[mapSize][mapSize];
        this.grid = new Grid(mapSize);

        // Try generating a valid map
        while (!generateValidMap()) {
            // Retry if the generated map is invalid
            System.out.println("Map generation failed. Retrying...");
        }

        printMap();
    }


    /**
     * Generates a valid map with the following properties:
     * <ul>
     *     <li>The map is surrounded by walls</li>
     *     <li>The map has an entry and an exit door</li>
     *     <li>The map has a valid path from the entry to the exit</li>
     *     <li>The map has a valid path from the entry to the key</li>
     *     <li>The map has a valid path from the key to the exit</li>
     * </ul>
     * @return true if the map is valid, false otherwise
     */
    private boolean generateValidMap() {
        genEmptyMapData(mapData);
        genOuterWall();
        genInnerWalls();
        spawnMobs();
        placeTraps();
        spawnKey();

        grid.fillGrid(mapData);

        List<Node> result = AStar.findPath(grid, entry, key);

        if(!checkPath(result)) return false;

        result = AStar.findPath(grid, key, exit);
        if(!checkPath(result)) return false;

        return true;
    }

    /**
     * Checks if the given path is valid
     * @param result the path to check
     * @return true if the path is valid, false otherwise
     */
    private boolean checkPath(List<Node> result) {
        return result != null && !result.isEmpty();
    }

    /**
     * fill the map with 9s, which means empty
     * @param mapData the map to fill
     */
    private void genEmptyMapData(int[][] mapData) {
        for (int[] row : mapData)
            Arrays.fill(row, 9);
    }

    /**
     * create outer walls all around the map, and create the entry and exit doors
     */
    private void genOuterWall() {
        for (int i = 0; i < mapSize; i++) {
            mapData[0][i] = 0;
            mapData[i][0] = 0;
            mapData[mapSize - 1][i] = 0;
            mapData[i][mapSize - 1] = 0;
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
                y = rand.nextInt(0, mapSize - 2);
                break;
            case 1:
                y = 0;
                x = rand.nextInt(0, mapSize - 2);
                break;
            case 2:
                x = mapSize - 1;
                y = rand.nextInt(0, mapSize - 2);
                break;
            case 3:
                y = mapSize - 1;
                x = rand.nextInt(0, mapSize - 2);
                break;
            default:
                x = 0;
                y = 0;
        }
        mapData[x][y] = type;
        if (type == 1) entry = new Position(x, y, false);
        else exit = new Position(x, y, false);
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

    /**
     * spawn the key in the map
     */
    public void spawnKey() {
        while (true) {
            int x = rand.nextInt(0, mapSize - 2);
            int y = rand.nextInt(0, mapSize - 2);

            // Check that the key is not placed on a wall
            if (mapData[x][y] == 9) {
                mapData[x][y] = 5;
                key = new Position(x, y, false);
                break;
            }
        }
    }

    /**
     * generate random objects in the map
     * @param possibility the possibility of generating an object (1/possibility)
     * @param objectType the type of the object to generate
     */
    public void genRandomMapObject(int possibility, int objectType) {
        for (int i = 0; i < mapSize; i++)
            for (int j = 0; j < mapSize; j++)
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

    /**
     * For testing, prints the map data into console, this can be used as a level generator
     */
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

}
