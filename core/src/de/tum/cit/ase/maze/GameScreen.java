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
import de.tum.cit.ase.maze.hud.HUD;
import de.tum.cit.ase.maze.map.MazeLoader;

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
    private MazeLoader mazeLoader;
    private Texture tiles, objects;
    private Character character;
    private HUD hud;

    /**
     * Constructor for GameScreen. Sets up the camera and font.
     *
     * @param game The main game class, used to access global resources and methods.
     */
    public GameScreen(MazeRunnerGame game) {
        this.game = game;

        // Create and configure the camera for the game view
        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        camera.zoom = 1.5f;

        // Get the font from the game's skin
        font = game.getSkin().getFont("font");

        //Get textures
        tiles = new Texture(Gdx.files.internal("assets/basictiles.png"));
        objects = new Texture(Gdx.files.internal("assets/objects.png"));

        spriteBatch = game.getSpriteBatch();
        mazeLoader = new MazeLoader("level-1"); // set level here for now
        int[] entryLocation = mazeLoader.getEntryLocation();
        character = new Character(mazeLoader.multiplyByScale(entryLocation[0]), mazeLoader.multiplyByScale(entryLocation[1]));
        hud = new HUD(spriteBatch, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), objects, 5);
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


        character.moveCharacter(mazeLoader);
        camera.position.set(character.getX(), character.getY(), 0);
        hud.draw();
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
    }

    // Additional methods and logic can be added as needed for the game screen

    /**
     * Renders the Maze
     */
    private void renderMaze() {
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();

        mazeLoader.drawMaze(spriteBatch, tiles, objects, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        spriteBatch.end();
    }

    public void drawSomething(BiConsumer<Float, MazeRunnerGame> drawFunction, float sinusInput) {
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        drawFunction.accept(sinusInput, game);
        spriteBatch.end();
    }

    public HUD getHud() {
        return hud;
    }
}
