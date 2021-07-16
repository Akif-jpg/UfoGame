package com.mygdx.game.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class GameStates {
    public abstract void render(SpriteBatch batch);
    public abstract void update(float dt);
    public abstract void dispose();
    public abstract void handleInput();
}
