package com.lobsterchops.pixelpond.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.lobsterchops.pixelpond.utils.Constants;


public class PlayerFish extends Fish {
    private Texture fishTexture;
    private TextureRegion fishRegion;
    private float rotation; // Current rotation angle in degrees
    private float targetRotation; // Target rotation angle
    private Vector2 inputVelocity; // Input-based movement

    // Rotation smoothing
    private static final float ROTATION_SPEED = 360f; // degrees per second
    private static final float MOVEMENT_SMOOTHING = 8f; // how quickly fish accelerates/decelerates

    public PlayerFish() {
        // Initialize from parent Fish class
        position = new Vector2();
        velocity = new Vector2();
        inputVelocity = new Vector2();
        size = Constants.PLAYER_BASE_SIZE;
        type = FishType.PLAYER;

        // Start in center of screen
        position.set(Constants.WORLD_WIDTH / 2, Constants.WORLD_HEIGHT / 2);

        rotation = 0f;
        targetRotation = 0f;

        // Load fish texture
        try {
            fishTexture = new Texture(Gdx.files.internal("textures/fish/PlayerFish.png"));
            fishRegion = new TextureRegion(fishTexture);
            System.out.println("Player fish texture loaded: " + fishTexture.getWidth() + "x" + fishTexture.getHeight());
        } catch (Exception e) {
            System.err.println("Failed to load player fish texture: " + e.getMessage());
            // Create a simple fallback rectangle texture
            fishTexture = new Texture(1, 1, com.badlogic.gdx.graphics.Pixmap.Format.RGBA8888);
            fishRegion = new TextureRegion(fishTexture);
        }
    }

    @Override
    public void update(float delta) {
        handleInput(delta);
        updateMovement(delta);
        updateRotation(delta);
        keepInBounds();
    }

    private void handleInput(float delta) {
        inputVelocity.setZero();

        // WASD and Arrow Key input
        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
            inputVelocity.y += 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            inputVelocity.y -= 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            inputVelocity.x -= 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            inputVelocity.x += 1;
        }

        // Normalize diagonal movement (so moving diagonally isn't faster)
        if (inputVelocity.len() > 0) {
            inputVelocity.nor().scl(Constants.PLAYER_SPEED);

            // Calculate target rotation based on movement direction
            targetRotation = inputVelocity.angleDeg();
        }
    }

    private void updateMovement(float delta) {
        // Smooth movement interpolation
        velocity.lerp(inputVelocity, MOVEMENT_SMOOTHING * delta);

        // Apply movement
        position.add(velocity.x * delta, velocity.y * delta);
    }

    private void updateRotation(float delta) {
        if (inputVelocity.len() > 0) { // Only rotate when moving
            // Calculate shortest rotation path
            float rotationDiff = targetRotation - rotation;

            // Handle wrapping (e.g., from 350° to 10°)
            while (rotationDiff > 180) rotationDiff -= 360;
            while (rotationDiff < -180) rotationDiff += 360;

            // Smoothly rotate towards target
            if (Math.abs(rotationDiff) > 1f) { // Only rotate if difference is significant
                float rotationStep = ROTATION_SPEED * delta;
                if (Math.abs(rotationDiff) < rotationStep) {
                    rotation = targetRotation;
                } else {
                    rotation += Math.signum(rotationDiff) * rotationStep;
                }
            }

            // Keep rotation in [0, 360) range
            rotation = rotation % 360;
            if (rotation < 0) rotation += 360;
        }
    }

    private void keepInBounds() {
        // Keep fish within world bounds
        if (position.x < size/2) {
            position.x = size/2;
            velocity.x = Math.max(0, velocity.x); // Stop moving left
        }
        if (position.x > Constants.WORLD_WIDTH - size/2) {
            position.x = Constants.WORLD_WIDTH - size/2;
            velocity.x = Math.min(0, velocity.x); // Stop moving right
        }
        if (position.y < size/2) {
            position.y = size/2;
            velocity.y = Math.max(0, velocity.y); // Stop moving down
        }
        if (position.y > Constants.WORLD_HEIGHT - size/2) {
            position.y = Constants.WORLD_HEIGHT - size/2;
            velocity.y = Math.min(0, velocity.y); // Stop moving up
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        if (fishTexture == null) return;

        // Calculate origin point (center of fish sprite)
        float originX = size / 2;
        float originY = size / 2;

        // Draw the fish with rotation
        batch.draw(fishRegion,
            position.x - originX,  // x position
            position.y - originY,  // y position
            originX,               // origin x (rotation point)
            originY,               // origin y (rotation point)
            size,                  // width
            size,                  // height
            1f,                    // scale x
            1f,                    // scale y
            rotation);             // rotation in degrees
    }

    @Override
    public boolean collidesWith(Fish other) {
        if (other == null) return false;

        float distance = position.dst(other.position);
        float combinedRadius = (size + other.size) / 2 * Constants.COLLISION_TOLERANCE;

        return distance < combinedRadius;
    }

    // Getters
    public Vector2 getPosition() { return position; }
    public float getSize() { return size; }
    public float getRotation() { return rotation; }

    // Growth methods for when fish eats others
    public void grow() {
        size = Math.min(size * Constants.PLAYER_GROWTH_RATE, Constants.PLAYER_MAX_SIZE);
    }

    public void shrink() {
        size = Math.max(size / Constants.PLAYER_GROWTH_RATE, Constants.PLAYER_MIN_SIZE);
    }

    public void dispose() {
        if (fishTexture != null) {
            fishTexture.dispose();
        }
    }
}
