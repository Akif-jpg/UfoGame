package com.mygdx.game.entities.other;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.handle.GameVars;

public class Warning {
    private Sprite sprite;
    public Warning(float y){
        sprite = new Sprite(GameVars.resourceManager.getTexture(GameVars.WARNING));
        sprite.setPosition(0,y);
        sprite.setSize(GameVars.V_WIDTH,10);
    }

    public void draw(SpriteBatch spriteBatch){
        sprite.draw(spriteBatch,0.5f);

    }
}
