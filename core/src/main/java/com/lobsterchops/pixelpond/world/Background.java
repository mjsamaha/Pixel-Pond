package com.lobsterchops.pixelpond.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
            backgroundTexture = new Texture(Gdx.files.internal("textures/background/background.png"));
            backgroundRegion = new TextureRegion(backgroundTexture);
            System.out.println("Background loaded successfully: " + backgroundTexture.getWidth() + "x" + backgroundTexture.getHeight());
        } catch (Exception e) {
            System.err.println("Failed to load background texture: " + e.getMessage());
            backgroundTexture = new Texture(1, 1, com.badlogic.gdx.graphics.Pixmap.Format.RGBA8888);
            backgroundRegion = new TextureRegion(backgroundTexture);
        }

        scrollX = 0;
        scrollY = 0;
    }

    public void update(float deltaTime) {
        scrollX += Constants.BACKGROUND_SCROLL_SPEED * deltaTime * 0.5f;
        scrollY += Constants.BACKGROUND_SCROLL_SPEED * deltaTime * 0.2f;

        // Wrap scrolling to prevent overflow
        if (scrollX > backgroundTexture.getWidth()) scrollX = 0;
        if (scrollY > backgroundTexture.getHeight()) scrollY = 0;
    }

    public void render(SpriteBatch batch) {
        renderStaticBackground(batch);
    }

    private void renderStaticBackground(SpriteBatch batch) {
        // Calculate how many tiles we need to cover the entire WORLD
        int tilesX = (int) Math.ceil(Constants.WORLD_WIDTH / backgroundTexture.getWidth()) + 2;
        int tilesY = (int) Math.ceil(Constants.WORLD_HEIGHT / backgroundTexture.getHeight()) + 2;

        // Draw tiled background to cover entire world
        for (int x = 0; x < tilesX; x++) {
            for (int y = 0; y < tilesY; y++) {
                float drawX = (x * backgroundTexture.getWidth()) - (scrollX % backgroundTexture.getWidth());
                float drawY = (y * backgroundTexture.getHeight()) - (scrollY % backgroundTexture.getHeight());

                batch.draw(backgroundRegion, drawX, drawY);
            }
        }
    }

    public void renderWithCamera(SpriteBatch batch, OrthographicCamera camera) {
        float cameraX = camera.position.x;
        float cameraY = camera.position.y;
        float viewWidth = Constants.WIDTH * camera.zoom;
        float viewHeight = Constants.HEIGHT * camera.zoom;

        // Calculate visible area bounds
        float leftEdge = cameraX - viewWidth / 2f;
        float rightEdge = cameraX + viewWidth / 2f;
        float bottomEdge = cameraY - viewHeight / 2f;
        float topEdge = cameraY + viewHeight / 2f;

        // Calculate which tiles are visible
        int startTileX = (int) Math.floor(leftEdge / backgroundTexture.getWidth()) - 1;
        int endTileX = (int) Math.ceil(rightEdge / backgroundTexture.getWidth()) + 1;
        int startTileY = (int) Math.floor(bottomEdge / backgroundTexture.getHeight()) - 1;
        int endTileY = (int) Math.ceil(topEdge / backgroundTexture.getHeight()) + 1;

        // Draw only visible tiles
        for (int x = startTileX; x <= endTileX; x++) {
            for (int y = startTileY; y <= endTileY; y++) {
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
