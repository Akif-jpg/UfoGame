package com.mygdx.game.entities.enemies.standart;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.entities.abstracts.AbstractPlayer;
import com.mygdx.game.entities.other.Barrier;
import com.mygdx.game.handle.GameVars;

import java.util.LinkedList;


public class  Helicopter extends Actor {

    private short health;
    private Sprite heliSprite;
    private float fastVelocity;
    private float mediumVelocity;
    private float slowVelocity;
    private Body heliBody;
    public static float sizeWidth = 40;
    public  static float sizeHeight = 24;
    private enum MoveType {fastMove,slowMove,mediumMove,stopMove};
    public  Helicopter(Body heliBody){
        super();

        health = 100;
        this.heliBody = heliBody;
        fastVelocity = 14;
        mediumVelocity = 8;
        slowVelocity = 2;

        this.setPosition(this.heliBody.getPosition().x,
                this.heliBody.getPosition().y);
        this.setWidth(Helicopter.sizeWidth);
        this.setHeight(Helicopter.sizeHeight);

        heliSprite = new Sprite(GameVars.resourceManager.getTexture(GameVars.HELICOPTER));
        heliSprite.setSize(sizeWidth,sizeHeight);
        heliSprite.setPosition(this.getX()-this.getWidth()/2,this.getY()-this.getHeight()/2);



    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        heliSprite.draw(batch,parentAlpha);
    }

    public short getHealth() {
        return health;
    }

    public boolean move(Vector2 moveVector,MoveType moveType){
       switch (moveType){
           case fastMove:{
                Vector2 velocityVector2 = new Vector2(moveVector.nor().x*fastVelocity,
                        moveVector.nor().y*fastVelocity);
                heliBody.setLinearVelocity(velocityVector2);
                return true;

           }
           case mediumMove:{
               Vector2 velocityVector2 = new Vector2(moveVector.nor().x*mediumVelocity,
                       moveVector.nor().y*mediumVelocity);
               heliBody.setLinearVelocity(velocityVector2);
               return true;

           }
           case slowMove:{
               Vector2 velocityVector2 = new Vector2(moveVector.nor().x*slowVelocity,
                       moveVector.nor().y*slowVelocity);
               heliBody.setLinearVelocity(velocityVector2);
                return true;

           }
           case stopMove:{
               heliBody.setLinearVelocity(0f,0f);
                return true;


           }
           default:
               return true;
       }

    }

    private boolean moveUp(float deltaLength){

        Vector2 up = new Vector2(0f, 1f);
        if (Math.abs(deltaLength) > 60f) {
            move(up, MoveType.fastMove);
        }else if (Math.abs(deltaLength) > 30f){
           move(up,MoveType.mediumMove);
        }else if (Math.abs(deltaLength) > 5f) {
            move(up, MoveType.slowMove);
        } else {
            move(up, MoveType.stopMove);
        }
        return true;

    }
    private boolean moveDown(float deltaLength){
        Vector2 down= new Vector2(0f, -1f);
        if (Math.abs(deltaLength) > 60f) {
            move(down, MoveType.fastMove);
        }else if (Math.abs(deltaLength) > 30f){
            move(down,MoveType.mediumMove);
        }else if (Math.abs(deltaLength) > 5f) {
            move(down, MoveType.slowMove);
        } else {
            move(down, MoveType.stopMove);
        }
        return true;

    }

    private boolean moveDecision(LinkedList<Barrier> barrierLinkedList,AbstractPlayer target){

            for (int index = 0; index < barrierLinkedList.size(); index++) {
                Barrier cmpBrr = barrierLinkedList.get(index);
                float lengthX = cmpBrr.getX() + cmpBrr.getWidth()/2 - this.getX() -this.getWidth()/2;
                float deltaTime = lengthX/Math.abs(GameVars.G_VELOCITY);

                float BehindBorder = cmpBrr.getX() + cmpBrr.getWidth()/2 + this.getWidth()/2;
                if(cmpBrr.getX() + cmpBrr.getWidth()/2 + this.getWidth()/2 > this.getX()&&
                        (deltaTime < 20 || this.getX()+40 > BehindBorder)) {
                    if (cmpBrr.getBarrierType() == Barrier.BarrierType.bottomBarrier) {//if barrier type is bottom barrier
                        Barrier topBarrier = barrierLinkedList.get(index + 1);// get a top barrier
                        float barrierSpace = ((topBarrier.getY() - topBarrier.getHeight() / 2)
                                - (cmpBrr.getY() + cmpBrr.getHeight() / 2));//length  of between of barriers

                        float targetHeight = cmpBrr.getY() + cmpBrr.getHeight() / 2 + barrierSpace / 2;//calculate targetHeight for this helicopter

                        if (this.getY() > targetHeight) {//is this helicopter above
                            float deltaHeight = this.getY() - targetHeight;
                            return moveDown(deltaHeight);

                        } else if (this.getY() < targetHeight) {//is this helicopter below
                            float deltaHeight = -(this.getY() - targetHeight);
                            return moveUp(deltaHeight);
                        } else {//is same alignment
                            return move(new Vector2(0f, 0f), MoveType.stopMove);
                        }

                    } else if (cmpBrr.getBarrierType() == Barrier.BarrierType.singleBarrier) {//if barrier type is single barrier
                        //this is like a harder than previous
                        float aboveHeight = (GameVars.V_HEIGHT - cmpBrr.getY() - cmpBrr.getHeight()/2)/2 + cmpBrr.getY()+cmpBrr.getHeight()/2;
                        float belowHeight = (cmpBrr.getY() - cmpBrr.getHeight() / 2) / 2;
                        //which is more near
                        if (Math.abs(this.getY() - aboveHeight) < Math.abs(this.getY() - belowHeight)) {
                            float deltaHeight = this.getY() - aboveHeight;

                            if (deltaHeight > 0f) {

                                 return moveDown(deltaHeight);

                            } else if (deltaHeight < 0f) {

                                return moveUp(deltaHeight);
                            } else {

                                return move(new Vector2(0f, 0f), MoveType.stopMove);
                            }
                        } else {
                            float deltaHeight = this.getY() - belowHeight;

                            if (deltaHeight > 0) {

                               return moveDown(deltaHeight);
                            } else if (deltaHeight < 0) {

                                  return moveUp(deltaHeight);
                            } else {

                                return move(new Vector2(0f, 0f), MoveType.stopMove);
                            }
                        }
                    }
                    break;
                }
            }
            //if barrier list is empty or at above don't give loop a decision

            float deltaHeight = this.getY() - target.getY();
            if (deltaHeight > 0){
                moveDown(deltaHeight);
            }else {
                moveUp(deltaHeight);
            }

        return true;
    }

    private void fireDecision(AbstractPlayer target){

    }

    public void update(LinkedList<Barrier> barrierLinkedList,AbstractPlayer target){
        this.setPosition(this.heliBody.getPosition().x,
                this.heliBody.getPosition().y);
        heliSprite.setPosition(this.getX()-this.getWidth()/2,
                this.getY()-this.getHeight()/2);
        moveDecision(barrierLinkedList,target);

    }


}

