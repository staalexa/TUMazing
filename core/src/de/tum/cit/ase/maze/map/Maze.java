package de.tum.cit.ase.maze.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import de.tum.cit.ase.maze.entitity.Mob;
import de.tum.cit.ase.maze.util.Position;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This Class takes a levelName and loads the Map from the level file.
 */
public class Maze {
    private final int[][] mapData;
    private static int tileSize;
    private int width, height;
    private TextureRegion wallTexture, pathTexture, entryTexture, exitTexture, trapTexture,
    keyTexture, enemyTexture;
    private Position entryLocation, keyLocation;
    private List<Position> trapLocations, exitLocations;
    private List<Mob> mobs;
    private Key key;

    public Maze(String levelName, Key key) {
        this.key = key;
        width = 20;
        height = 20;

        trapLocations = new ArrayList<>();
        exitLocations = new ArrayList<>();
        mobs = new ArrayList<>();

        getProperties(levelName);
        mapData = new int[width][height];
        for (int[] row : mapData)
            Arrays.fill(row, 9);
        loadMap(levelName);
        tileSize = 16;
    }

    /**
     * Looks for the Height and Width values in the map file
     *
     * @param levelName
     */
    private void getProperties(String levelName) {
        FileHandle fileHandle = Gdx.files.internal("maps/" + levelName + ".properties");
        BufferedReader reader = new BufferedReader(fileHandle.reader());
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("H") || line.startsWith("W")) {
                    String[] splitData = line.split("=");
                    if (splitData[0].equals("Height")) height = Integer.parseInt(splitData[1]);
                    if (splitData[0].equals("Width")) width = Integer.parseInt(splitData[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load the map from the level name. Assumes that level files are like the given template
     *
     * @param levelName
     */
    private void loadMap(String levelName) {
        FileHandle fileHandle = Gdx.files.internal("maps/" + levelName + ".properties");
        BufferedReader reader = new BufferedReader(fileHandle.reader());

        try {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] splitData = line.split("=");
                String[] coordinates = splitData[0].split(",");
                if (splitData[0].startsWith("W") || splitData[0].startsWith("H")) continue; // filter out the metadata
                int x = Integer.parseInt(coordinates[0]);
                int y = Integer.parseInt(coordinates[1]);
                int type = Integer.parseInt(splitData[1]);
                mapData[x][y] = type;
                switch (type) {
                    case 1 -> entryLocation = new Position(x, y, true);
                    case 2 -> exitLocations.add(new Position(x, y, true));
                    case 3 -> trapLocations.add(new Position(x, y, true));
                    case 4 -> mobs.add(new Mob(new Position(x, y, true)));
                    case 5 -> key.setPosition(new Position(x, y, true)); // set the key position
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Draws a maze out of a given Map. Creates predefined texture regions.
     *
     * @param spriteBatch SpriteBatch
     * @param tiles       Texture of all possible Tiles
     * @param objects     Texture of all possible Objects
     */
    public void drawMaze(SpriteBatch spriteBatch, Texture tiles, Texture objects, Texture mobs) {
        // Get all textures
        TextureRegion[][] textureRegions = TextureRegion.split(tiles, tileSize, tileSize);
        TextureRegion[][] objectRegions = TextureRegion.split(objects, tileSize, tileSize);
        TextureRegion[][] mobRegions = TextureRegion.split(mobs, tileSize, tileSize);
        wallTexture = textureRegions[0][0];
        pathTexture = textureRegions[0][5];
        entryTexture = textureRegions[6][2];
        exitTexture = textureRegions[6][0];
        trapTexture = textureRegions[6][5];
        keyTexture = objectRegions[4][1];
        enemyTexture = mobRegions[5][9];

        int mobIndex = 0;

        for (int y = mapData[1].length - 1; y >= 0; y--) {
            for (int x = 0; x < mapData[0].length; x++) {
                float xPos = x * wallTexture.getRegionWidth(); // Calculate x position
                float yPos = y * wallTexture.getRegionHeight(); // Calculate y position
                switch (mapData[x][y]) {
                    case 0 -> drawTexture(spriteBatch, xPos, yPos, wallTexture);
                    //case 1 -> drawTexture(spriteBatch, xPos, yPos, entryTexture, scale);
                    case 2 -> drawTexture(spriteBatch, xPos, yPos, exitTexture);
                    case 3 -> drawTexture(spriteBatch, xPos, yPos, trapTexture);
                    case 4 -> {
                        Mob currentMob = this.mobs.get(mobIndex++);
                        currentMob.drawMob(this, spriteBatch, enemyTexture);
                    }
                    case 5 -> drawTexture(spriteBatch, xPos, yPos, keyTexture);
                    //default -> drawTexture(spriteBatch, xPos, yPos, pathTexture, scale);
                }
            }
        }
    }

    /**
     * Removes the key from given position
     * @param position The key's position
     */
    public void removeKey(Position position) {
        removeMapObject(position);
    }

    /**
     * Removes a trap from the map
     * @param position Position of the Trap
     */
    public void activateTrap(Position position) {
        removeMapObject(position);
    }

    /**
     * Generic method to remove stuff from the map
     * THIS IS PERMANENT!!!
     *
     * @param position Position of the object
     */
    private void removeMapObject(Position position) {
        mapData[position.getX() / 16][position.getY() / 16] = 9;
    }

    /**
     * Helper method to make drawing textures easier
     *
     * @param batch  SpriteBatch
     * @param xPos   x Position
     * @param yPos   y Position
     * @param region Texture part
     */
    public void drawTexture(SpriteBatch batch, float xPos, float yPos, TextureRegion region) {
        batch.draw(region, xPos, yPos, region.getRegionWidth(), region.getRegionHeight());
    }

    /**
     * Converts a number into a position of the map by multiplying by scale and tile size
     *
     * @param number the number that you want to convert
     * @return the converted number
     */
    public static int convertToMapPosition(float number) {
        return Math.round(number / tileSize);
    }

    // Getters

    /**
     * checks if a given position is a wall on the map
     *
     * @param x value x in the pixel position of the map
     * @param y value y in the pixel position of the map
     * @return a boolean variable, whether there is a wall there
     */
    public boolean isWall(int x, int y) {
        x /= 16;
        y /= 16;
        // check for out of bounds
        if (x < 0 || y < 0 || y > mapData.length || x > mapData[0].length) return true;
        // check for wall
        return mapData[x][y] == 0;
    }

    public Position getEntryLocation() {
        return entryLocation;
    }

    public Position getKeyLocation() {
        return keyLocation;
    }

    public List<Position> getTrapLocations() {
        return trapLocations;
    }

    public List<Position> getExitLocations() {
        return exitLocations;
    }

    public List<Mob> getMobs() {
        return mobs;
    }
    public void removeMob(Mob mob) {
        mobs.remove(mob);
    }

    public int[][] getMapData() {
        return mapData;
    }

    /**
     * TESTING: Prints mapdata
     */
    public void printMapData() {
        for (int y = mapData[1].length - 1; y >= 0; y--) {
            System.out.println("Data for row " + y);
            for (int x = 0; x < mapData[0].length; x++) {
                if (mapData[x][y] == 0)
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