package com.mygdx.game.handle.entityManagers;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.entities.other.Barrier;
import com.mygdx.game.handle.GameVars;
import com.mygdx.game.interfaces.StarAction;

import java.util.LinkedList;
import java.util.Random;

public class BarrierManager {
    private World world;
    private LinkedList<Barrier> brrList;
    private int timeLoop = 0;
    private StarAction starAction;
    private int screenWidth;
    private int screenHeight;
    public BarrierManager(World world){
        this.world = world;
        brrList = new LinkedList<Barrier>();
        screenWidth = GameVars.V_WIDTH;
        screenHeight = GameVars.V_HEIGHT;
    }

    private void createDblBrr(){//create top barrier and create bottom barrier
        float width = (float) (Math.random()*50+50);
        float height = (float) (Math.random()*150+20);
        createBarrier(screenWidth+width/2,height/2,width,height, Barrier.BarrierType.bottomBarrier);
        float height2 = screenHeight-(height+110);
        float y2 = height2/2 + height+110;
        createBarrier(screenWidth+width/2,y2,width,height2, Barrier.BarrierType.topBarrier);

    }

    private void createSingleBrr(){//create one Barrier
        float width = (float) Math.random()*50 + 50;
        float height = (float) Math.random()*50 + 50;
        float x = GameVars.V_WIDTH + width;
        float y = (float) Math.random()*((screenHeight - 60) -(height + 60)) + (height/2 + 60);
        createBarrier(x,y,width,height, Barrier.BarrierType.singleBarrier);

    }

    private void rndmBarrier(){
       int rndmBarrier =  new Random().nextInt(2);
        if(!brrList.isEmpty()){
            if(brrList.get(brrList.size()-1).getX() < 200f) {
                if(rndmBarrier == 0)
                    createDblBrr();
                else if(rndmBarrier == 1)
                    createSingleBrr();
                timeLoop = 0;
            }
        }else{
            if(rndmBarrier == 0)
                createDblBrr();
            else if(rndmBarrier == 1)
                createSingleBrr();
            timeLoop = 0;
        }
        timeLoop++;
    }
    private void createBarrier(float x,float y,float width,float height,Barrier.BarrierType barrierType){
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();

        bdef.position.set(x,y);
        bdef.type = BodyDef.BodyType.KinematicBody;

        PolygonShape ps = new PolygonShape();
        ps.setAsBox(width/2.0f,height/2.0f);
        fdef.shape = ps;
        fdef.density = 1.0f;
        fdef.friction = 0f;
        fdef.restitution = 0.5f;
        fdef.filter.categoryBits = GameVars.BIT_BARRIER;
        fdef.filter.maskBits = GameVars.BIT_PLAYER;
        Body b = world.createBody(bdef);
        b.createFixture(fdef).setUserData(GameVars.WALL);
        Barrier brr = new Barrier(b,width,height);
        brr.setBarrierType(barrierType);
        brrList.add(brr);
        starAction.createStar(brr);
    }
    private void removeBrr(){
        for(int index = 0;index < brrList.size();index++){
            Body b = brrList.get(index).getBrrBody();
            if(b.getPosition().x < 0 - brrList.get(index).getWidth()){
                world.destroyBody(b);
                brrList.remove(index);
            }
        }
    }
    private void updateBarrierList(){
        for(Barrier brr: brrList){
            brr.update();
        }
    }
    private void drawBarrierList(SpriteBatch batch,float parentAlpha){
        for(Barrier brr: brrList){
            brr.draw(batch,parentAlpha);
        }
    }

    public void draw(SpriteBatch batch){
        drawBarrierList(batch,1f);
    }
    public void update(){
        removeBrr();
        rndmBarrier();
        updateBarrierList();
    }

    public LinkedList<Barrier> getBrrList(){
        return brrList;
    }
    public void setStarAction(StarAction starAction) {
        this.starAction = starAction;
    }
}
