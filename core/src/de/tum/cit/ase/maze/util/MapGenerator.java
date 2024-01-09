package de.tum.cit.ase.maze.util;

import java.util.*;

public class MapGenerator {
    private int mapsize;
    private int[][] mapData;
    private Random rand;

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

        if(!checkValidMap()) genMap();
    }

    private boolean checkValidMap() {
            // Initialize the priority queue, the distances map, and the visited set
            PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a[2]));
            Map<String, Integer> distances = new HashMap<>();
            Set<String> visited = new HashSet<>();

            // Find the entry and exit points
            int[] entry = null, exit = null, key = null;
            for (int i = 0; i < mapsize; i++) {
                for (int j = 0; j < mapsize; j++) {
                    if (mapData[i][j] == 1) entry = new int[]{i, j, 0};
                    else if (mapData[i][j] == 2) exit = new int[]{i, j};
                    else if (mapData[i][j] == 5) key = new int[]{i, j};
                    distances.put(i + "," + j, Integer.MAX_VALUE);
                }
            }

            // Add the entry point to the queue with distance 0
            queue.add(entry);
            distances.put(entry[0] + "," + entry[1], 0);

            // Process the nodes in the queue
            while (!queue.isEmpty()) {
                int[] node = queue.poll();
                visited.add(node[0] + "," + node[1]);

                // Visit all the neighbors
                for (int[] neighbor : new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}}) {
                    int newX = node[0] + neighbor[0], newY = node[1] + neighbor[1];
                    if (newX >= 0 && newX < mapsize && newY >= 0 && newY < mapsize && !visited.contains(newX + "," + newY)) {
                        int newDist = distances.get(node[0] + "," + node[1]) + 1;
                        if (newDist < distances.get(newX + "," + newY)) {
                            distances.put(newX + "," + newY, newDist);
                            queue.add(new int[]{newX, newY, newDist});
                        }
                    }
                }
            }

            // Check if there is a path from the entry to the key and from the key to the exit
            return distances.get(key[0] + "," + key[1]) != Integer.MAX_VALUE && distances.get(exit[0] + "," + exit[1]) != Integer.MAX_VALUE;
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

        if(mapData[x][y] == 9) mapData[x][y] = 5;
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
