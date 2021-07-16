package com.mygdx.game.entities.spaceships;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.entities.abstracts.AbstractPlayer;
import com.mygdx.game.interfaces.PlayerActions;

public class UfoModel1f extends AbstractPlayer {
    float maxVelocity = 16f;
    public UfoModel1f(Body flpBody){
        this.flpBody = flpBody;
        setX(flpBody.getPosition().x);
        setY(flpBody.getPosition().y);
        ufoTexture = new Texture(Gdx.files.internal("ufo.png"));
        spr = new Sprite(ufoTexture);
        spr.setSize(45,45);
    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        spr.draw(batch,parentAlpha);
    }

    @Override
    public void movePlayer(Vector2 moveVector) {
        this.flpBody.applyForceToCenter(moveVector,true);
        if (this.getVelocity()>maxVelocity){
            this.flpBody.setLinearVelocity(this.flpBody.getLinearVelocity().nor().x*maxVelocity,
                    this.flpBody.getLinearVelocity().nor().y*maxVelocity);
        }
    }

    @Override
    public void takeDamage(float damage) {
        super.takeDamage(damage);
    }
}
