package com.lobsterchops.pixelpond.utils;



public class Constants {
    // Window
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final String TITLE = "Pixel Pond";

    // World
    public static final float WORLD_WIDTH = 800f;
    public static final float WORLD_HEIGHT = 600f;

    // Player
    public static final float PLAYER_BASE_SIZE = 16f;
    public static final float PLAYER_SPEED = 200f;
    public static final float PLAYER_GROWTH_RATE = 1.2f;
    public static final float PLAYER_MIN_SIZE = 8f;
    public static final float PLAYER_MAX_SIZE = 64f;

    // Fish
    public static final float SMALL_FISH_SIZE = 8f;
    public static final float MEDIUM_FISH_SIZE = 24f;
    public static final float LARGE_FISH_SIZE = 32f;
    public static final float FISH_BASE_SPEED = 100f;

    // Spawn
    public static final float SPAWN_RATE_PREY = 2f;      // seconds between prey spawns
    public static final float SPAWN_RATE_PREDATOR = 8f;  // seconds between predator spawns
    public static final int MAX_FISH_COUNT = 20;

    // Background Settings
    public static final float BACKGROUND_SCROLL_SPEED = 50f;
    public static final int WATER_TILE_SIZE = 64;

    // Camera Settings (simplified)
    public static final float CAMERA_FOLLOW_SPEED = 3.0f; // How smoothly camera follows player

    // UI Settings
    public static final float UI_PADDING = 20f;
    public static final int FONT_SIZE = 16;

    // Physics Settings
    public static final float COLLISION_TOLERANCE = 0.8f; // Multiply collision radius by this

    // Game Balance
    public static final int SCORE_PER_FISH = 10;
    public static final float DIFFICULTY_INCREASE_RATE = 0.1f; // Per 100 score points

}
