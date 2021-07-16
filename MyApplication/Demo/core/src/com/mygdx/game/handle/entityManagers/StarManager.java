package com.mygdx.game.handle.entityManagers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.entities.other.Barrier;
import com.mygdx.game.entities.other.Star;
import com.mygdx.game.handle.GameVars;
import static com.mygdx.game.handle.GameVars.starVolume;
import com.mygdx.game.interfaces.StarAction;

import java.util.LinkedList;
import java.util.Random;

public class StarManager implements StarAction {
    LinkedList<Star> starList;
    LinkedList<Star> destroyStar;
    short starNumber = 0;
    World world;
    public int collectedStar;
    public StarManager(World world){
        starList = new LinkedList<Star>();
        destroyStar = new LinkedList<Star>();
        this.world = world;
        collectedStar = 0;
    }

    public void drawStarList(SpriteBatch batch,float parentAlpha){
        for(Star star : starList){
            star.draw(batch,parentAlpha);
        }
    }

    private void createStar(float x,float y){
        BodyDef bodyDef = new BodyDef();

        FixtureDef fixtureDef = new FixtureDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;

        bodyDef.position.set(x,y);

        Body body = world.createBody(bodyDef);

        fixtureDef.isSensor = true;
        CircleShape shape = new CircleShape();
        shape.setRadius(GameVars.starVolume/2);
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = GameVars.BIT_STAR;
        fixtureDef.filter.maskBits = GameVars.BIT_PLAYER;
        body.createFixture(fixtureDef).setUserData("@Star" + starNumber);

        Star star = new Star(body);
        star.starCode = "@Star" + starNumber;
        starList.add(star);
        starNumber++;
    }



    private void updateStarList(){
        for(int index = 0;index < starList.size();index++){
            Body b = starList.get(index).getStarBody();
            if(b.getPosition().x < 0 - GameVars.starVolume){
                world.destroyBody(b);
                starList.remove(index);
            }
            else {
                starList.get(index).update();
            }
        }
    }

    public void update(){
        updateStarList();
        for(Star star : destroyStar){
            world.destroyBody(star.getStarBody());
            starList.remove(star);
        }
        destroyStar.clear();

    }

    private void creatorStar1(float height,float x){
        int numberStar = new Random().nextInt(5);
        float sY[] = new float[numberStar];
        float sX[] = new float[numberStar];
        for (int index = 0;index < numberStar;index++){
            sY[index] = ((float) Math.random()*(110 - starVolume))
                    + height + starVolume/2 + 5;
            sX[index] = x + 25 - (float) Math.random()*50;
            createStar(sX[index],sY[index]);
        }



    }

    private void creatorStar2(float height,float x,float y){
        int randomStar = new Random().nextInt(5);
        for (int index = 0;index < randomStar;index++) {
            int rndmStar = new Random().nextInt(2); //this number is for determine star position top xor bottom
            if (rndmStar == 0/*top star*/) {
                float starY = ((float) Math.random() * (110 - starVolume - 20)
                        + starVolume / 2)
                        + (y + height / 2);
                float newX = x + 25 - (float) Math.random()*50;
                createStar(newX, starY);
            } else if (rndmStar == 1/*bottom star*/) {
                float starY = (y - height / 2) - ((float) Math.random() * (110 - starVolume - 20)
                        + starVolume / 2);
                float newX = x + 25 - (float) Math.random()*50;
                createStar(newX, starY);
            }
        }
    }

    @Override
    public void createStar(Barrier barrier) {
        if (barrier.getBarrierType() == Barrier.BarrierType.bottomBarrier){
            creatorStar1(barrier.getHeight(),barrier.getBrrBody().getPosition().x);

        }else if (barrier.getBarrierType() == Barrier.BarrierType.singleBarrier){
            creatorStar2(barrier.getHeight(),
                    barrier.getBrrBody().getPosition().x,
                    barrier.getBrrBody().getPosition().y);
        }
    }

    @Override
    public void collectStar(String code) {
        for(int index = 0;index < starList.size();index++){
            if (starList.get(index).starCode.equals(code)){
                destroyStar.add(starList.get(index));
            }
        }

        collectedStar++;
    }

}
