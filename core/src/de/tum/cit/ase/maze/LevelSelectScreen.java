package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
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

/**
 * Responsible for level selection
 */
public class LevelSelectScreen implements Screen {
    private final Stage stage;

    public LevelSelectScreen(MazeRunnerGame game) {
        var camera = new OrthographicCamera();
        camera.zoom = 1.5f;
        Viewport viewport = new ScreenViewport(camera);
        stage = new Stage(viewport, game.getSpriteBatch());

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        table.add(new Label("Select level", game.getSkin(), "title")).padBottom(80).row();

        listLevels(game, table);
    }
    @Override
    public void show() {
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

    /**
     * Prints a button list with all levels from the maps folder
     * @param game MazeRunnerGame
     * @param table Table to print the buttons
     */
    private void listLevels(MazeRunnerGame game, Table table) {
        // Obtain the file handle for the folder
        FileHandle folder = Gdx.files.internal("maps/");

        // Check if the folder exists and is a directory
        if (folder.exists() && folder.isDirectory()) {
            // List all files in the folder
            FileHandle[] files = folder.list();

            // Iterate through the files and print their names
            for (FileHandle file : files) {
                final String levelName = file.nameWithoutExtension(); // Get file name without extension
                TextButton button = new TextButton(levelName, game.getSkin());
                table.add(button).width(300).padBottom(30).row();
                button.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        game.goToGame(levelName); // Change to the game screen when button is pressed
                    }
                });
            }
        } else {
            System.err.println("Folder not found or is not a directory.");
        }
    }
}