package com.mygdx.game.entities.other;


import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.handle.GameVars;


public class Barrier extends Actor {
    public enum BarrierType { bottomBarrier, topBarrier, singleBarrier}

    public BarrierType getBarrierType() {
        return barrierType;
    }

    public void setBarrierType(BarrierType barrierType) {
        this.barrierType = barrierType;
    }



    BarrierType barrierType;
    private Body brrBody;
    private Sprite wall;

    public Barrier(Body brrBody,float width,float height){
            this.brrBody = brrBody;
            brrBody.setLinearVelocity(GameVars.G_VELOCITY,0f);
            this.setPosition(brrBody.getPosition().x,
            brrBody.getPosition().y);
            this.setWidth(width);
            this.setHeight(height);
            wall = new Sprite(GameVars.resourceManager.getTexture(GameVars.WALL));
            wall.setSize(this.getWidth(),this.getHeight());
     }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        wall.draw(batch,parentAlpha);

    }
    public Body getBrrBody(){return brrBody;}
    public void update(){
        float x = brrBody.getPosition().x;
        float y = brrBody.getPosition().y;
        this.setPosition(x,y);
        wall.setPosition(this.getX()-this.getWidth()/2,this.getY()-this.getHeight()/2);
        this.brrBody.setLinearVelocity(GameVars.G_VELOCITY,0f);
    }

    //statics
    public static float calculateDamage(Vector2 other, Vector2 brr){
        float yDamage = Math.abs(other.y*1.5f);
        float xDamage = Math.abs(other.x*0.80f) + brr.x*0.60f;
        return xDamage + yDamage;

    }

}
