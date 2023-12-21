package de.tum.cit.ase.maze.hud;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import de.tum.cit.ase.maze.util.TextureUtil;
import de.tum.cit.ase.maze.entitity.Character;

/**
 * Handles and displays the HUD
 */
public class HUD {
    private Stage stage;
    private Image[] lifeImages;
    private Image keyImage;
    private int remainingLives;

    public HUD(SpriteBatch batch, int screenWidth, int screenHeight, Texture objects, int scale) {
        stage = new Stage(new FitViewport(screenWidth, screenHeight), batch);
        lifeImages = new Image[Character.getMaxLives()];
        remainingLives = Character.getMaxLives();

        // Textures
        TextureRegion[][] textureRegions = TextureUtil.getTextures(objects);
        TextureRegion lifeTexture = textureRegions[0][4];
        TextureRegion keyTexture = textureRegions[4][1];

        // create the lives
        for (int i = 0; i < Character.getMaxLives(); i++) {
            lifeImages[i] = new Image(lifeTexture);
            lifeImages[i].setSize(lifeImages[i].getWidth() * scale, lifeImages[i].getHeight() * scale);
            lifeImages[i].setPosition(20 + i * (lifeImages[i].getWidth() + 10), screenHeight - lifeImages[i].getHeight() - 20);
            stage.addActor(lifeImages[i]);
        }

        // Create key image
        keyImage = new Image(keyTexture);
        keyImage.setSize(keyImage.getWidth() * scale, keyImage.getHeight() * scale);
        keyImage.setPosition(screenHeight - keyImage.getWidth() - 20, screenHeight - keyImage.getHeight() - 20);
        stage.addActor(keyImage);

        collectedKey(false);
    }

    /**
     * reduces the heart image by one.
     * NOT SAFE! (security will be handled by Lives class)
     */
    public void loseLife() {
        if (remainingLives > 0)
            lifeImages[--remainingLives].setVisible(false);
    }

    /**
     * Set the display value of the Key
     *
     * @param hasKey
     */
    public void collectedKey(boolean hasKey) {
        keyImage.setVisible(hasKey);
    }

    /**
     * Draw the HUD
     */
    public void draw() {
        stage.act();
        stage.draw();
    }

    /**
     * Cleanup method
     */
    public void dispose() {
        stage.dispose();
    }
}