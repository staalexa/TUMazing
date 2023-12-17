package de.tum.cit.ase.maze.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Provides several texture related utility methods
 */
public class TextureUtil {

    /**
     * Helper method to cut out images of larger texture file maps.
     * @param texture A Texture
     * @return grid of textures with [x][y]=textureRegion
     */
    public static TextureRegion[][] getTextures(Texture texture) {
        return TextureRegion.split(texture, 16, 16);
    }
}
