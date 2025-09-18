package com.lobsterchops.pixelpond.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.lobsterchops.pixelpond.utils.Constants;

public class Background {
    private Texture backgroundTexture;
    private TextureRegion backgroundRegion;
    private float scrollX, scrollY;

    public Background() {
        try {
            // Load your background image
            backgroundTexture = new Texture(Gdx.files.internal("textures/background/background.png"));
            backgroundRegion = new TextureRegion(backgroundTexture);
            System.out.println("Background loaded successfully: " + backgroundTexture.getWidth() + "x" + backgroundTexture.getHeight());
        } catch (Exception e) {
            System.err.println("Failed to load background texture: " + e.getMessage());
            // Create a fallback colored texture
            backgroundTexture = new Texture(1, 1, com.badlogic.gdx.graphics.Pixmap.Format.RGBA8888);
            backgroundRegion = new TextureRegion(backgroundTexture);
        }

        scrollX = 0;
        scrollY = 0;
    }

    public void update(float deltaTime) {
        // Optional: Add subtle background scrolling for water effect
        scrollX += Constants.BACKGROUND_SCROLL_SPEED * deltaTime * 0.5f;
        scrollY += Constants.BACKGROUND_SCROLL_SPEED * deltaTime * 0.2f;

        // Wrap scrolling to prevent overflow
        if (scrollX > backgroundTexture.getWidth()) scrollX = 0;
        if (scrollY > backgroundTexture.getHeight()) scrollY = 0;
    }

    public void render(SpriteBatch batch) {
        // Calculate how many tiles we need to cover the screen
        int tilesX = (int) Math.ceil(Constants.WORLD_WIDTH / backgroundTexture.getWidth()) + 1;
        int tilesY = (int) Math.ceil(Constants.WORLD_HEIGHT / backgroundTexture.getHeight()) + 1;

        // Draw tiled background
        for (int x = 0; x < tilesX; x++) {
            for (int y = 0; y < tilesY; y++) {
                float drawX = (x * backgroundTexture.getWidth()) - (scrollX % backgroundTexture.getWidth());
                float drawY = (y * backgroundTexture.getHeight()) - (scrollY % backgroundTexture.getHeight());

                batch.draw(backgroundRegion, drawX, drawY);
            }
        }
    }

    public void dispose() {
        if (backgroundTexture != null) {
            backgroundTexture.dispose();
        }
    }

}
