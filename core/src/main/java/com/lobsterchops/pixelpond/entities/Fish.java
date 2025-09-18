package com.lobsterchops.pixelpond.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class Fish {
    protected Vector2 position;
    protected Vector2 velocity;
    protected float size;
    protected FishType type;

    public abstract void update(float delta);

    public abstract void render(SpriteBatch batch);

    public boolean collidesWith(Fish other) { return false; }

}
