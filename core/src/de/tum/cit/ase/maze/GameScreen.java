package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import de.tum.cit.ase.maze.entitity.Character;
import de.tum.cit.ase.maze.entitity.Mob;
import de.tum.cit.ase.maze.hud.HUD;
import de.tum.cit.ase.maze.map.Maze;
import de.tum.cit.ase.maze.map.Key;
import de.tum.cit.ase.maze.util.GameState;
import de.tum.cit.ase.maze.util.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * The GameScreen class is responsible for rendering the gameplay screen.
 * It handles the game logic and rendering of the game elements.
 */
public class GameScreen implements Screen {

    private final MazeRunnerGame game;
    private final OrthographicCamera camera;
    private final BitmapFont font;
    private float sinusInput = 0f;
    private final SpriteBatch spriteBatch;
    private Maze maze;
    private Texture tiles, objects, mobs;
    private Character character;
    private HUD hud;
    private Key key;

    /**
     * Constructor for GameScreen. Sets up the camera and font.
     *
     * @param game The main game class, used to access global resources and methods.
     */
    public GameScreen(MazeRunnerGame game, String levelName) {
        this.game = game;

        // Create and configure the camera for the game view
        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        camera.zoom = 0.15f;

        // Get the font from the game's skin
        font = game.getSkin().getFont("font");

        //Get textures
        tiles = new Texture(Gdx.files.internal("assets/basictiles.png"));
        objects = new Texture(Gdx.files.internal("assets/objects.png"));
        mobs = new Texture(Gdx.files.internal("assets/mobs.png"));

        spriteBatch = game.getSpriteBatch();
        key = new Key();
        maze = new Maze(levelName, key, game); // set level here for now
        hud = new HUD(spriteBatch, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), objects, 5);
        character = new Character(maze.getEntryLocation(), game, hud); //TODO make this look nice at some point
    }

    // Screen interface methods with necessary functionality
    @Override
    public void render(float delta) {
        // Check for escape key press to go back to the menu
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) game.goToMenu();

        ScreenUtils.clear(0, 0, 0, 1); // Clear the screen

        camera.update(); // Update the camera

        sinusInput += delta;

        renderMaze();
        drawSomething(character.drawCharacter, sinusInput);

        character.moveCharacter(maze);
        camera.position.set(character.getPosition().getX(), character.getPosition().getY(), 0);
        hud.draw();
        checkEnvironment();
        moveMobs();
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        tiles.dispose();
        objects.dispose();
        mobs.dispose();
    }

    // Additional methods and logic can be added as needed for the game screen

    /**
     * Renders the Maze
     */
    private void renderMaze() {
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();

        maze.drawMaze(spriteBatch, tiles, objects, mobs);
        spriteBatch.end();
    }

    /**
     * Method to draw anything in the screen
     *
     * @param drawFunction pass in a function, with the thing that you want to draw
     * @param sinusInput   is derived from the delta value, needed for the walking effect
     */
    public void drawSomething(BiConsumer<Float, MazeRunnerGame> drawFunction, float sinusInput) {
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        drawFunction.accept(sinusInput, game);
        spriteBatch.end();
    }

    /**
     * Check for environmental influence on the placer
     */
    public void checkEnvironment() {
        // Handles Key
        if (character.getPosition().isWithinRange(key.getPosition()))
            key.pickUp(this);

        // Handles Traps
        List<Position> activatedTraps = new ArrayList<>();
        for (Position trapPosition : maze.getTrapLocations()) {
            if (character.getPosition().isWithinRange(trapPosition)) {
                maze.activateTrap(trapPosition);
                activatedTraps.add(trapPosition);
                character.looseLife();
            }
        }
        // Cleanup
        for (Position trapPosition : activatedTraps)
            maze.getTrapLocations().remove(trapPosition);

        // Handles Mobs
        for (Mob mob : maze.getMobs())
            if (character.getPosition().isWithinRange(mob.getPosition()) && (System.currentTimeMillis() - mob.getLastKill()) >= 3000) {
                character.looseLife();
                mob.setLastKill(System.currentTimeMillis());
            }
        //Handles Exit
        for (Position exit : maze.getExitLocations()) {
            if (character.getPosition().isWithinRange(exit))
                game.goToEnd(GameState.VICTORY);
        }
    }

    /**
     * Call the movement methods of the mobs
     */
    public void moveMobs() {
        for (Mob mob : maze.getMobs())
            mob.moveMob(maze);
    }

    public HUD getHud() {
        return hud;
    }


    public Maze getMaze() {
        return maze;
    }

    public Character getCharacter() {
        return character;
    }
}
