package com.lobsterchops.pixelpond;

import com.badlogic.gdx.Game;
import com.lobsterchops.pixelpond.screens.MenuScreen;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class PixelPond extends Game {
    @Override
    public void create() {
        setScreen(new MenuScreen(this));
    }
}
