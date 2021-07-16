package com.mygdx.game.entities.other;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.handle.GameVars;

public class Background {
    private Texture bckgr;
    private Sprite spr;
    public Background(){
        bckgr = new Texture(Gdx.files.internal("sky-bckgr.jpg"));
        spr = new Sprite(bckgr);
        spr.setPosition(0,0);
        spr.setSize(GameVars.V_WIDTH,GameVars.V_HEIGHT);
    }

    public void draw(SpriteBatch batch,float parentAlpha){
        spr.draw(batch,parentAlpha);
    }
}
