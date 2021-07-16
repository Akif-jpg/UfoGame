package com.mygdx.game.entities.enemies.standart;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.game.entities.abstracts.Bullet;
import com.mygdx.game.handle.GameVars;

public class Missile extends Bullet {
    public final static float Height = GameVars.V_HEIGHT/16;
    public final static float Width = GameVars.V_HEIGHT/20;
    float deltaTime = 0;
    public Missile(Vector2 position, Vector2 direction, PolygonShape polygonShape){
        super(position,direction,polygonShape);
        this.maskBits = GameVars.BIT_PLAYER;
        this.categoryBits = GameVars.BIT_MISSILE;
        this.setFilter(maskBits,categoryBits);
        this.Suspensive = 60*1/2;
        this.maxRange = 500;
        Texture texture = GameVars.resourceManager.getTexture(GameVars.FIREBALL);

        TextureRegion[][] currentFrame = TextureRegion.split(texture,texture.getWidth()/2,texture.getHeight()/6);
        TextureRegion[] frames = new TextureRegion[12];
        int counter = 0;
        for (int index = 0;index < 6;index++){
            for (int index2 = 0;index2 < 2;index2++){
                frames[counter] = currentFrame[index][index2];
                counter++;
            }
        }
        normalAnimation = new Animation<>(1/12f,frames);
        normalAnimation.setPlayMode(Animation.PlayMode.LOOP);

    }

    @Override
    public void draw(SpriteBatch spriteBatch,float parentAlpha) {
        deltaTime += Gdx.graphics.getDeltaTime();
        spriteBatch.draw(normalAnimation.getKeyFrame(deltaTime),x-Missile.Width*2f/2,y-Missile.Height*1.5f/2,Missile.Width*2f,Missile.Height*1.5f);

    }
}
