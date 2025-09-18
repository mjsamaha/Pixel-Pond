package com.lobsterchops.pixelpond.systems;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.lobsterchops.pixelpond.entities.PlayerFish;
import com.lobsterchops.pixelpond.utils.Constants;

public class GameCamera {
    private OrthographicCamera camera;
    private Viewport viewport;

    public GameCamera(float worldWidth, float worldHeight) {
        // Initialize camera and viewport
        camera = new OrthographicCamera();
        viewport = new FitViewport(Constants.WIDTH, Constants.HEIGHT, camera);

        camera.zoom = 0.5f;
        // Start camera at world center
        camera.position.set(worldWidth / 2f, worldHeight / 2f, 0);
    }

    public void followPlayer(PlayerFish player, float deltaTime) {
        if (player == null) return;

        Vector2 playerPos = player.getPosition();

        // Direct following - exactly like the example you found
        camera.position.set(playerPos.x, playerPos.y, 0);
        camera.update();
    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    // Getters
    public OrthographicCamera getCamera() {
        return camera;
    }

    public Viewport getViewport() {
        return viewport;
    }
}
