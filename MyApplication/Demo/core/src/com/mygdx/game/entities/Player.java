package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Player extends Actor {
    private Body flpBody;
    private Texture flappy;
    private Sprite spr;
    private float forceVal = 10;
    public Player(Body flpBody){
        this.flpBody = flpBody;
        setX(flpBody.getPosition().x);
        setY(flpBody.getPosition().y);
        flappy = new Texture(Gdx.files.internal("flappy.png"));
        spr = new Sprite(flappy);
    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        spr.setPosition(getX(),getY());
        spr.setScale(10f,10f);
        spr.draw(batch,parentAlpha);
    }

    public Body getFlpBody(){return flpBody;}

    public void jump(){
        flpBody.applyForceToCenter(new Vector2(0f,forceVal),true);
    }

    public void down(){
        flpBody.applyForceToCenter(new Vector2(0f,-forceVal),true);
    }

    public void update() {
        setPosition(flpBody.getPosition().x, flpBody.getPosition().y);
    }
}
