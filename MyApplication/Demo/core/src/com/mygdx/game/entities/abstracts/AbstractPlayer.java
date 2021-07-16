package com.mygdx.game.entities.abstracts;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.interfaces.PlayerActions;

public class AbstractPlayer extends Actor implements PlayerActions {
    protected Body flpBody;
    protected Texture ufoTexture;
    protected Sprite spr;
    protected float health = 20;

    public Body getFlpBody(){
        return flpBody;
    }

    public void update() {
        setPosition(flpBody.getPosition().x, flpBody.getPosition().y);
        spr.setPosition(getX()-spr.getWidth()/2,getY()-spr.getHeight()/2);
    }

    public float getVelocity(){
        return flpBody.getLinearVelocity().dst(0,0);
    }

    public Vector2 getLinearVelocity(){return flpBody.getLinearVelocity();}

    @Override
    public void movePlayer(Vector2 moveVector) {

    }

    public boolean isAlive(){
        return health > 0;
    }

    @Override
    public void takeDamage(float damage) {
        health -= damage;
        System.err.println("Can: " + health + "\n" + "Alınan hasar: "+ damage);
    }


}
