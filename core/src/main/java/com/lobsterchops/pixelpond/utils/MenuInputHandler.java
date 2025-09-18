package com.lobsterchops.pixelpond.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class MenuInputHandler {

    public enum MenuOption {
        PLAY, EXIT
    }

    private MenuOption selectedOption = MenuOption.PLAY;
    private boolean enterPressed = false;
    private boolean upPressed = false;
    private boolean downPressed = false;
    private Vector2 mousePos = new Vector2();

    public void update() {
        // Reset single-press flags
        enterPressed = false;

        // Handle keyboard input
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            selectedOption = MenuOption.PLAY;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) || Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            selectedOption = MenuOption.EXIT;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            enterPressed = true;
        }

        // Handle mouse input
        mousePos.set(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY()); // Flip Y coordinate
    }

    public boolean isMouseOver(Rectangle buttonBounds) {
        return buttonBounds.contains(mousePos);
    }

    public boolean isButtonClicked(Rectangle buttonBounds) {
        return isMouseOver(buttonBounds) && Gdx.input.isButtonJustPressed(Input.Buttons.LEFT);
    }

    public MenuOption getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(MenuOption option) {
        this.selectedOption = option;
    }

    public boolean isEnterPressed() {
        return enterPressed;
    }
}
