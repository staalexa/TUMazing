package de.tum.cit.ase.maze.util;

import java.util.Arrays;

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
     * @param mapData an int[][]
     */
    private void genEmptyMapData(int[][] mapData) {
        for (int[] row : mapData)
            Arrays.fill(row, 9);
    }

    private void genOuterWall() {
        for (int i = 0; i < mapsize; i++) {
            mapData[0][i] = 0;
            mapData[i][0] = 0;
            mapData[mapsize - 1][i] = 0;
            mapData[i][mapsize - 1] = 0;
        }

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
