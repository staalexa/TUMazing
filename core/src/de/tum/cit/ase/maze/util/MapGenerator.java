package de.tum.cit.ase.maze.util;

import java.util.Arrays;
import java.util.Random;

public class MapGenerator {
    private int mapsize;
    private int[][] mapData;

    public MapGenerator(int mapsize) {
        this.mapsize = mapsize;
        this.mapData = new int[mapsize][mapsize];

        genEmptyMapData(mapData);

        genOuterWall();

        printMap();
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
}
