package com.lobsterchops.pixelpond.systems;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.lobsterchops.pixelpond.entities.PlayerFish;
import com.lobsterchops.pixelpond.utils.Constants;

public class GameCamera {
    private OrthographicCamera camera;
    private Viewport viewport;

    private float worldWidth;
    private float worldHeight;

    private float minX, maxX, minY, maxY;

    public GameCamera(float worldWidth, float worldHeight) {
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;

        // Initialize camera and viewport
        camera = new OrthographicCamera();
        viewport = new FitViewport(Constants.WIDTH, Constants.HEIGHT, camera);

        camera.zoom = 0.5f;

        // Calculate camera boundaries based on zoom and viewport
        calculateCameraBounds();

        // Start camera at world center (clamped to boundaries)
        float startX = MathUtils.clamp(worldWidth / 2f, minX, maxX);
        float startY = MathUtils.clamp(worldHeight / 2f, minY, maxY);
        camera.position.set(startX, startY, 0);
    }

    private void calculateCameraBounds() {
        // Calculate effective viewport size with zoom
        float effectiveWidth = Constants.WIDTH * camera.zoom;
        float effectiveHeight = Constants.HEIGHT * camera.zoom;

        // Calculate camera boundaries
        // Camera center cannot go closer to edges than half the effective viewport
        minX = effectiveWidth / 2f;
        maxX = worldWidth - (effectiveWidth / 2f);
        minY = effectiveHeight / 2f;
        maxY = worldHeight - (effectiveHeight / 2f);

        // If world is smaller than effective viewport, center the camera
        if (worldWidth < effectiveWidth) {
            minX = maxX = worldWidth / 2f;
        }
        if (worldHeight < effectiveHeight) {
            minY = maxY = worldHeight / 2f;
        }
    }


    public void followPlayer(PlayerFish player, float deltaTime) {
        if (player == null) return;

        Vector2 playerPos = player.getPosition();

        // Clamp camera position to stay within world boundaries
        float targetX = MathUtils.clamp(playerPos.x, minX, maxX);
        float targetY = MathUtils.clamp(playerPos.y, minY, maxY);

        // Direct following with boundary constraints
        camera.position.set(targetX, targetY, 0);
        camera.update();
    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
        // Recalculate bounds in case viewport changed
        calculateCameraBounds();
    }

    // Debug method to get camera bounds info
    public String getCameraBoundsInfo() {
        return String.format("Camera bounds: X[%.1f - %.1f], Y[%.1f - %.1f]", minX, maxX, minY, maxY);
    }

    // Getters
    public OrthographicCamera getCamera() {
        return camera;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public float getWorldWidth() { return worldWidth; }
    public float getWorldHeight() { return worldHeight; }
}
