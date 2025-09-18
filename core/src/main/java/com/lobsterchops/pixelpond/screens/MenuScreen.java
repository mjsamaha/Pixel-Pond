package com.lobsterchops.pixelpond.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.lobsterchops.pixelpond.utils.Constants;
import com.lobsterchops.pixelpond.utils.MenuInputHandler;

public class MenuScreen implements Screen {

    private Game game;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private BitmapFont titleFont;
    private BitmapFont subtitleFont;
    private BitmapFont buttonFont;
    private OrthographicCamera camera;
    private Viewport viewport;
    private GlyphLayout layout;
    private MenuInputHandler inputHandler;

    private Texture backgroundTexture;

    private Rectangle playButton;
    private Rectangle exitButton;

    public MenuScreen(Game game){
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        camera = new OrthographicCamera();
        viewport = new FitViewport(Constants.WIDTH, Constants.HEIGHT, camera);
        layout = new GlyphLayout();
        inputHandler = new MenuInputHandler();

        backgroundTexture = new Texture(Gdx.files.internal("textures/background/sky.png"));

        float buttonWidth = 120;
        float buttonHeight = 40;
        float buttonX = (Constants.WIDTH - buttonWidth) / 2;

        playButton = new Rectangle(buttonX, Constants.HEIGHT / 2 - 80, buttonWidth, buttonHeight);
        exitButton = new Rectangle(buttonX, Constants.HEIGHT / 2 - 130, buttonWidth, buttonHeight);

        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("fonts/04B_03__.TTF"));
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();

        // Title
        param.size = 28;
        param.color = Color.WHITE;
        param.magFilter = Texture.TextureFilter.Nearest;
        param.minFilter = Texture.TextureFilter.Nearest;
        titleFont = gen.generateFont(param);

        // Subtitle
        param.size = 18;
        param.color = Color.WHITE;
        subtitleFont = gen.generateFont(param);

        param.size = 18;
        param.color = Color.WHITE;
        buttonFont = gen.generateFont(param);

        gen.dispose();
    }

    @Override
    public void render(float delta) {
        inputHandler.update();

        // Handle button interactions
        if (inputHandler.isMouseOver(playButton)) {
            inputHandler.setSelectedOption(MenuInputHandler.MenuOption.PLAY);
        }
        if (inputHandler.isMouseOver(exitButton)) {
            inputHandler.setSelectedOption(MenuInputHandler.MenuOption.EXIT);
        }

        // Handle button clicks
        if (inputHandler.isButtonClicked(playButton) ||
            (inputHandler.getSelectedOption() == MenuInputHandler.MenuOption.PLAY && inputHandler.isEnterPressed())) {
            game.setScreen(new GameScreen());
            System.out.println("Starting game...");
        }
        if (inputHandler.isButtonClicked(exitButton) ||
            (inputHandler.getSelectedOption() == MenuInputHandler.MenuOption.EXIT && inputHandler.isEnterPressed())) {
            Gdx.app.exit();
        }

        // Clear screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);

        // Draw background FIRST
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Constants.WIDTH, Constants.HEIGHT);
        batch.end();

        // Draw button backgrounds
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Play button
        if (inputHandler.getSelectedOption() == MenuInputHandler.MenuOption.PLAY) {
            shapeRenderer.setColor(0.3f, 0.3f, 0.7f, 1f); // Blue when selected
        } else {
            shapeRenderer.setColor(0.2f, 0.2f, 0.2f, 1f); // Dark gray
        }
        shapeRenderer.rect(playButton.x, playButton.y, playButton.width, playButton.height);

        // Exit button
        if (inputHandler.getSelectedOption() == MenuInputHandler.MenuOption.EXIT) {
            shapeRenderer.setColor(0.7f, 0.3f, 0.3f, 1f); // Red when selected
        } else {
            shapeRenderer.setColor(0.2f, 0.2f, 0.2f, 1f); // Dark gray
        }
        shapeRenderer.rect(exitButton.x, exitButton.y, exitButton.width, exitButton.height);

        shapeRenderer.end();

        batch.begin();

        // Title and subtitle
        String titleText = "Pixel Pond";
        String subtitleText = "Made by Lobsterchops";

        layout.setText(titleFont, titleText);
        float titleX = (Constants.WIDTH - layout.width) / 2;
        float titleY = Constants.HEIGHT / 2 + 50;

        layout.setText(subtitleFont, subtitleText);
        float subtitleX = (Constants.WIDTH - layout.width) / 2;
        float subtitleY = Constants.HEIGHT / 2 - 20;

        titleFont.draw(batch, titleText, titleX, titleY);
        subtitleFont.draw(batch, subtitleText, subtitleX, subtitleY);

        // Button text
        String playText = "Play";
        String exitText = "Exit";

        layout.setText(buttonFont, playText);
        float playTextX = playButton.x + (playButton.width - layout.width) / 2;
        float playTextY = playButton.y + (playButton.height + layout.height) / 2;

        layout.setText(buttonFont, exitText);
        float exitTextX = exitButton.x + (exitButton.width - layout.width) / 2;
        float exitTextY = exitButton.y + (exitButton.height + layout.height) / 2;

        buttonFont.draw(batch, playText, playTextX, playTextY);
        buttonFont.draw(batch, exitText, exitTextX, exitTextY);

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);

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
        if (shapeRenderer != null) shapeRenderer.dispose();
        if (titleFont != null) titleFont.dispose();
        if (subtitleFont != null) subtitleFont.dispose();
        if (buttonFont != null) buttonFont.dispose();
    }
}
