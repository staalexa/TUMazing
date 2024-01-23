package de.tum.cit.ase.maze;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import de.tum.cit.ase.maze.exception.MapFormatWrongException;
import de.tum.cit.ase.maze.menu.EndScreen;
import de.tum.cit.ase.maze.menu.LevelSelectScreen;
import de.tum.cit.ase.maze.menu.MenuScreen;
import de.tum.cit.ase.maze.menu.PauseScreen;
import de.tum.cit.ase.maze.util.GameState;
import games.spooky.gdx.nativefilechooser.NativeFileChooser;

/**
 * The MazeRunnerGame class represents the core of the Maze Runner game.
 * It manages the screens and global resources like SpriteBatch and Skin.
 */
public class MazeRunnerGame extends Game {
    // Screens
    private MenuScreen menuScreen;
    private GameScreen gameScreen;
    private PauseScreen pauseScreen;

    // Sprite Batch for rendering
    private SpriteBatch spriteBatch;

    // UI Skin
    private Skin skin;

    // Character animation downwards
    private Animation<TextureRegion> characterWalkingDownwardsAnimation;
    // Character animation left
    private Animation<TextureRegion> characterWalkingLeftAnimation;
    // Character animation right
    private Animation<TextureRegion> characterWalkingRightAnimation;
    // Character animation up
    private Animation<TextureRegion> characterWalkingUpAnimation;
    // Character animation standing
    private Animation<TextureRegion> characterStandingAnimation;
    private Music backgroundMusic;

    /**
     * Constructor for MazeRunnerGame.
     *
     * @param fileChooser The file chooser for the game, typically used in desktop environment.
     */
    public MazeRunnerGame(NativeFileChooser fileChooser) {
        super();
    }

    /**
     * Called when the game is created. Initializes the SpriteBatch and Skin.
     */
    @Override
    public void create() {
        spriteBatch = new SpriteBatch(); // Create SpriteBatch
        skin = new Skin(Gdx.files.internal("craft/craftacular-ui.json")); // Load UI skin
        loadCharacterAnimation(); // Load character animation

        // Play some background music
        // Background sound
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("assets/sounds/menu.wav"));
        backgroundMusic.setLooping(true);
        backgroundMusic.play();

        goToMenu(); // Navigate to the menu screen
    }

    /**
     * Switches to the menu screen.
     */
    public void goToMenu() {
        this.setScreen(new MenuScreen(this)); // Set the current screen to MenuScreen
        backgroundMusic.play();
        if (gameScreen != null) {
            gameScreen.dispose(); // Dispose the game screen if it exists
            gameScreen = null;
        }
    }

    /**
     * Switches to the game screen.
     */
    public void goToGame(String levelName) {
        // Try catch to check the map format and stop the maze from loading if the map file is wrong
        try {
            gameScreen = new GameScreen(this, levelName);
            backgroundMusic.stop();
            this.setScreen(gameScreen); // Set the current screen to GameScreen
        } catch (MapFormatWrongException e) {
            goToLevel(); //TODO display the message for the user on screen.
        }

        if (menuScreen != null) {
            menuScreen.dispose(); // Dispose the menu screen if it exists
            menuScreen = null;
        }
    }

    /**
     * Switches to the end screen
     */
    public void goToEnd(GameState endingState) {
        this.setScreen(new EndScreen(this, endingState));
        if (gameScreen != null) {
            gameScreen.dispose();
            gameScreen = null;
        }
    }

    public void pause() {
        pauseScreen = new PauseScreen(this);
        this.setScreen(pauseScreen);
    }

    public void resume() {
        this.setScreen(gameScreen);
        pauseScreen.dispose();
    }

    public void goToLevel() {
        this.setScreen(new LevelSelectScreen(this));
        if (menuScreen != null) {
            menuScreen.dispose();
            menuScreen = null;
        }
        if (gameScreen != null) {
            gameScreen.dispose();
            gameScreen = null;
        }
    }

    /**
     * Loads the character animation from the character.png file.
     */
    private void loadCharacterAnimation() {
        Texture walkSheet = new Texture(Gdx.files.internal("character.png"));

        int frameWidth = 16;
        int frameHeight = 32;
        int animationFrames = 4;

        // Down
        // libGDX internal Array instead of ArrayList because of performance
        Array<TextureRegion> walkFramesDown = new Array<>(TextureRegion.class);

        // Add all frames to the animation
        for (int col = 0; col < animationFrames; col++)
            walkFramesDown.add(new TextureRegion(walkSheet, col * frameWidth, 0, frameWidth, frameHeight));

        characterWalkingDownwardsAnimation = new Animation<>(0.1f, walkFramesDown);

        // Right

        Array<TextureRegion> walkFramesRight = new Array<>(TextureRegion.class);

        // Add all frames to the animation
        for (int col = 0; col < animationFrames; col++)
            walkFramesRight.add(new TextureRegion(walkSheet, col * frameWidth, 2 * frameWidth, frameWidth, frameHeight));

        characterWalkingRightAnimation = new Animation<>(0.1f, walkFramesRight);

        // Up

        Array<TextureRegion> walkFramesUp = new Array<>(TextureRegion.class);

        // Add all frames to the animation
        for (int col = 0; col < animationFrames; col++)
            walkFramesUp.add(new TextureRegion(walkSheet, col * frameWidth, 4 * frameWidth, frameWidth, frameHeight));

        characterWalkingUpAnimation = new Animation<>(0.1f, walkFramesUp);

        // Left

        Array<TextureRegion> walkFramesLeft = new Array<>(TextureRegion.class);

        // Add all frames to the animation
        for (int col = 0; col < animationFrames; col++)
            walkFramesLeft.add(new TextureRegion(walkSheet, col * frameWidth, 6 * frameWidth, frameWidth, frameHeight));

        characterWalkingLeftAnimation = new Animation<>(0.1f, walkFramesLeft);

        // Standing

        Array<TextureRegion> standFrames = new Array<>(TextureRegion.class);

        // Add all frames to the animation
        for (int col = 0; col < animationFrames; col++)
            standFrames.add(new TextureRegion(walkSheet, col, 0, frameWidth, frameHeight));

        characterStandingAnimation = new Animation<>(0.1f, standFrames);
    }

    /**
     * Cleans up resources when the game is disposed.
     */
    @Override
    public void dispose() {
        getScreen().hide(); // Hide the current screen
        getScreen().dispose(); // Dispose the current screen
        spriteBatch.dispose(); // Dispose the spriteBatch
        skin.dispose(); // Dispose the skin
    }

    /**
     * Resumes the game
     */
    public void resumeGame() {
        gameScreen.resume();
    }

    // Getter methods
    public Skin getSkin() {
        return skin;
    }


    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }

    public Animation<TextureRegion> getCharacterWalkingDownwardsAnimation() {
        return characterWalkingDownwardsAnimation;
    }

    public Animation<TextureRegion> getCharacterWalkingLeftAnimation() {
        return characterWalkingLeftAnimation;
    }

    public Animation<TextureRegion> getCharacterWalkingRightAnimation() {
        return characterWalkingRightAnimation;
    }

    public Animation<TextureRegion> getCharacterWalkingUpAnimation() {
        return characterWalkingUpAnimation;
    }

    public Animation<TextureRegion> getCharacterStandingAnimation() {
        return characterStandingAnimation;
    }
}
