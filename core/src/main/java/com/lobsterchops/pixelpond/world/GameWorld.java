package com.lobsterchops.pixelpond.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.lobsterchops.pixelpond.entities.Fish;
import com.lobsterchops.pixelpond.entities.PlayerFish;
import com.lobsterchops.pixelpond.systems.CollisionSystem;
import com.lobsterchops.pixelpond.systems.GameCamera;
import com.lobsterchops.pixelpond.utils.Constants;

public class GameWorld {
    private Background background;
    private PlayerFish player;
    private Array<Fish> allFish;
    private FishSpawner spawner;
    private CollisionSystem collisionSystem;
    private GameCamera gameCamera;

    public GameWorld() {
        background = new Background();
        allFish = new Array<Fish>();

        // Initialize player fish
        player = new PlayerFish();

        gameCamera = new GameCamera(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
    }

    public void update(float deltaTime) {
        background.update(deltaTime);

        if (player != null) {
            player.update(deltaTime);
            // Camera follows player directly
            gameCamera.followPlayer(player, deltaTime);
        }

        // TODO: Update other game components
        // spawner.update(deltaTime);
        // collisionSystem.checkCollisions();
    }

    public void render(SpriteBatch batch) {
        // Set the camera's projection matrix to the batch
        batch.setProjectionMatrix(gameCamera.getCamera().combined);

        // Render background first (bottom layer)
        background.render(batch);

        if (player != null) {
            player.render(batch);
        }

        // TODO: Render other fish
        // for (Fish fish : allFish) {
        //     fish.render(batch);
        // }
    }

    public void resize(int width, int height) {
        gameCamera.resize(width, height);
    }

    public void dispose() {
        background.dispose();

        if (player != null){
            player.dispose();
        }
    }

    public PlayerFish getPlayer() { return player; }
    public GameCamera getCamera() { return gameCamera; }
}
