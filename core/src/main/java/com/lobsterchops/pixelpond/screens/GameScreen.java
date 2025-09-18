package com.lobsterchops.pixelpond.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lobsterchops.pixelpond.world.GameWorld;

public class GameScreen implements Screen {
    private SpriteBatch batch;
    private GameWorld gameWorld;

    @Override
    public void show() {
        batch = new SpriteBatch();
        gameWorld = new GameWorld();

        System.out.println("Game started!");
        System.out.println("Controls: WASD/Arrow Keys to move player fish");
    }

    @Override
    public void render(float delta) {
        // Update game logic
        gameWorld.update(delta);

        // Clear screen to blue (water color)
        Gdx.gl.glClearColor(0.2f, 0.5f, 0.8f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Render everything
        batch.begin();
        gameWorld.render(batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        gameWorld.resize(width, height);
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
        if (batch != null) batch.dispose();
        if (gameWorld != null) gameWorld.dispose();
    }
}
