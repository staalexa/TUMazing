package de.tum.cit.ase.maze.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.tum.cit.ase.maze.MazeRunnerGame;

/**
 * The PauseScreen is responsible for the pause of the game.
 */
public class PauseScreen implements Screen {
    private final Stage stage;

    /**
     * Constructor of the class, creates two buttons to go to the menu and resume the game
     *
     * @param game MazeRunnerGame
     */
    public PauseScreen(MazeRunnerGame game) {
        var camera = new OrthographicCamera();
        camera.zoom = 1.5f;
        Viewport viewport = new ScreenViewport(camera);
        stage = new Stage(viewport, game.getSpriteBatch());

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        table.add(new Label("Game paused", game.getSkin(), "title")).padBottom(80).row();

        TextButton resumeGameButton = new TextButton("resume game", game.getSkin());
        table.add(resumeGameButton).width(300).padBottom(30).row();
        resumeGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.resumeGame(); // Change to the game screen when button is pressed
            }
        });

        TextButton goToMenuButton = new TextButton("exit game", game.getSkin());
        table.add(goToMenuButton).width(300).padBottom(30).row();
        goToMenuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.goToMenu(); // Change to the menu screen when button is pressed
            }
        });

        TextButton goToLevelButton = new TextButton("change level", game.getSkin());
        table.add(goToLevelButton).width(300).padBottom(30).row();
        goToLevelButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.goToLevel(); // Change to the level screen when button is pressed
            }
        });
    }

    @Override
    public void show() {
        // Set the input processor so the stage can receive input events
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear the screen
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f)); // Update the stage
        stage.draw(); // Draw the stage
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true); // Update the stage viewport on resize
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
