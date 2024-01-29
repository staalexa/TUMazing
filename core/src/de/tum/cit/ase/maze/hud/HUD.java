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
    private SpriteBatch keyBatch;

    /**
     * Constructor
     *
     * @param batch        SpriteBatch to draw the HUD
     * @param screenWidth  Screen width
     * @param screenHeight Screen height
     * @param objects      Texture with the objects
     * @param scale        Scale of the HUD
     */
    public HUD(SpriteBatch batch, int screenWidth, int screenHeight, Texture objects, int scale) {
        stage = new Stage(new FitViewport(screenWidth, screenHeight), batch);
        lifeImages = new Image[Character.getMaxLives()];
        remainingLives = Character.getMaxLives();
        keyBatch = new SpriteBatch();

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
        keyImage.setPosition(keyImage.getWidth() - 60, screenHeight - keyImage.getHeight() - 130);

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
     *  Sets the key image to visible or transparent
     *
     * @param hasKey true if the player has the key
     */
    public void collectedKey(boolean hasKey) {
        if (hasKey) keyImage.setColor(1, 1, 1, 1);
        else keyImage.setColor(1, 1, 1, 0.3f); // semi transparent
    }

    /**
     * Draw the HUD
     */
    public void draw() {
        stage.act();
        stage.draw();

        // Draw key separately to enable transparency
        keyBatch.begin();
        keyImage.draw(keyBatch, 1);
        keyBatch.end();
    }

    /**
     * Cleanup method
     */
    public void dispose() {
        stage.dispose();
        keyBatch.dispose();
    }
}