package de.tum.cit.ase.maze.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This Class takes a levelName and loads the Map from the level file.
 */
public class MazeLoader {
    private final int[][] mapData;
    private int width, height;
    private TextureRegion wallTexture, pathTexture, entryTexture, exitTexture, trapTexture, keyTexture;
    private int[] entryLocation, keyLocation;
    private List<int[]> trapLocations, exitLocations, enemyLocations;

    public MazeLoader(String levelName) {
        width = 20;
        height = 20;

        trapLocations = new ArrayList<>();
        exitLocations = new ArrayList<>();
        enemyLocations = new ArrayList<>();

        getProperties(levelName);
        mapData = new int[width][height];
        for(int[] row : mapData) {
            Arrays.fill(row, 9);
        }
        loadMap(levelName);
    }

    /**
     * Looks for the Height and Width values in the map file
     * @param levelName
     */
    private void getProperties(String levelName) {
        FileHandle fileHandle = Gdx.files.internal("maps/" + levelName + ".properties");
        BufferedReader reader = new BufferedReader(fileHandle.reader());
        try {
            String line;
            while((line = reader.readLine()) != null) {
                if(line.startsWith("H") || line.startsWith("W")) {
                    String[] splitData = line.split("=");
                    if(splitData[0].equals("Height")) height = Integer.parseInt(splitData[1]);
                    if(splitData[0].equals("Width")) width = Integer.parseInt(splitData[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load the map from the level name. Assumes that level files are like the given template
     * @param levelName
     */
    private void loadMap(String levelName) {
        FileHandle fileHandle = Gdx.files.internal("maps/" + levelName + ".properties");
        BufferedReader reader = new BufferedReader(fileHandle.reader());

        try {
            String line;
            while((line = reader.readLine()) != null) {
                String[] splitData = line.split("=");
                String[] coordinates = splitData[0].split(",");
                if(splitData[0].startsWith("W") || splitData[0].startsWith("H")) continue; // filter out the metadata
                int x = Integer.parseInt(coordinates[0]);
                int y = Integer.parseInt(coordinates[1]);
                int type = Integer.parseInt(splitData[1]);
                mapData[x][y] = type;
                switch (type) {
                    case 1 -> entryLocation = new int[]{x,y};
                    case 2 -> exitLocations.add(new int[]{x,y});
                    case 3 -> trapLocations.add(new int[]{x,y});
                    case 4 -> enemyLocations.add(new int[]{x,y});
                    case 5 -> keyLocation = new int[]{x,y};
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Draws a maze out of a given Map. Creates predefined texture regions.
     * @param spriteBatch SpriteBatch
     * @param tiles Texture of all possible Tiles
     * @param objects Texture of all possible Objects
     * @param screenHeight Screen height
     * @param screenWidth Screen width
     */
    public void drawMaze(SpriteBatch spriteBatch, Texture tiles, Texture objects, int screenWidth, int screenHeight) {
        int scale = 5;
        // Get all textures
        TextureRegion[][] textureRegions = TextureRegion.split(tiles, 16, 16);
        TextureRegion[][] objectRegions = TextureRegion.split(objects, 16, 16);
        wallTexture = textureRegions[0][0];
        pathTexture = textureRegions[0][5];
        entryTexture = textureRegions[6][2];
        exitTexture = textureRegions[6][0];
        trapTexture = textureRegions[6][5];
        keyTexture = objectRegions[4][1];

        int mapWidth = mapData.length * 16 * scale; // 16 as textures are 16x16px
        int mapHeight = mapData[0].length * 16 * scale;

        // Calculate the offsets to center the map on the screen
        float xOffset = (screenWidth - mapWidth) / 2f;
        float yOffset = (screenHeight - mapHeight) / 2f;

        for (int y = mapData[1].length - 1; y >= 0; y--) {
            for(int x = 0; x < mapData[0].length; x++) {
                float xPos = x * wallTexture.getRegionWidth() * scale; // Calculate x position
                float yPos = y * wallTexture.getRegionHeight() * scale; // Calculate y position

                switch (mapData[x][y]) {
                    case 0 -> drawTextureWithScale(spriteBatch, xPos, yPos, wallTexture, scale);
                    //case 1 -> drawTextureWithScale(spriteBatch, xPos, yPos, entryTexture, scale);
                    case 2 -> drawTextureWithScale(spriteBatch, xPos, yPos, exitTexture, scale);
                    case 3 -> drawTextureWithScale(spriteBatch, xPos, yPos, trapTexture, scale);
                    //case 4 -> System.out.print("M"); //TODO create the mobs later
                    case 5 -> drawTextureWithScale(spriteBatch, xPos, yPos, keyTexture, scale);
                    //default -> drawTextureWithScale(spriteBatch, xPos, yPos, pathTexture, scale);
                }
            }
        }
    }

    /**
     * Helper method to make drawing textures easier
     * @param batch SpriteBatch
     * @param xPos x Position
     * @param yPos y Position
     * @param region Texture part
     * @param scale Scale
     */
    private void drawTextureWithScale(SpriteBatch batch, float xPos, float yPos, TextureRegion region, int scale) {
        batch.draw(region, xPos, yPos, region.getRegionWidth() * scale, region.getRegionHeight() * scale);
    }

    // Getters
    public boolean isWall(int x, int y) {
        return mapData[x][y] == 0;
    }

    public int[] getEntryLocation() {
        return entryLocation;
    }

    public int[] getKeyLocation() {
        return keyLocation;
    }

    public List<int[]> getTrapLocations() {
        return trapLocations;
    }

    public List<int[]> getExitLocations() {
        return exitLocations;
    }

    public List<int[]> getEnemyLocations() {
        return enemyLocations;
    }

    public int[][] getMapData() {
        return mapData;
    }

    /**
     * TESTING: Prints mapdata
     */
    public void printMapData() {
        for (int y = mapData[1].length - 1; y >= 0; y--) {
            System.out.println("Data for row "  + y);
            for(int x = 0; x < mapData[0].length; x++) {
                if(mapData[x][y] == 0)
                    System.out.println("Wall at pos: " + x + "," + y);
            }
            System.out.println(); // new line for formatting
        }
    }

    /**
     * TESTING: Generates a preview of a given map in console
     */
    public void printMap() {
        for (int y = mapData[1].length - 1; y >= 0; y--) {
            for(int x = 0; x < mapData[0].length; x++) {
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