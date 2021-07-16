package com.mygdx.game.entities.other;


import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.handle.GameVars;


import static com.mygdx.game.handle.GameVars.starVolume;

public class Star extends Actor {
    private Sprite sprite;
    private Body starBody;
    public String starCode;
    public Star(Body starBody){
        sprite = new Sprite(GameVars.resourceManager.getTexture(GameVars.STAR));
        sprite.setSize(starVolume,starVolume);
        this.starBody = starBody;
        starBody.setLinearVelocity(GameVars.G_VELOCITY,0f);
        this.setX(this.starBody.getPosition().x);
        this.setY(this.starBody.getPosition().y);
        this.setWidth(starVolume);
        this.setHeight(starVolume);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        sprite.draw(batch,parentAlpha);

    }

    public void update(){
        this.setPosition(starBody.getPosition().x-starVolume/2,
                starBody.getPosition().y-starVolume/2);
        sprite.setPosition(this.getX(),this.getY());
        this.starBody.setLinearVelocity(GameVars.G_VELOCITY,0);
    }

    public Body getStarBody(){return starBody;}
}
