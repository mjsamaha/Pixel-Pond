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

/* TODO : Other Fish
- **Other Fish:** Create pixel art -> 8x8px (small prey) to 32x32px (large predators)
- **Add other fish** - Small prey fish and larger predator fish
- **Implement collision system** - So your fish can eat smaller fish and grow || other predatorial fishes can eat you : cause game over
 */
