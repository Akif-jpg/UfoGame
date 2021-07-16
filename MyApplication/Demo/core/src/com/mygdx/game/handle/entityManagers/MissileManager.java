package com.mygdx.game.handle.entityManagers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.game.entities.abstracts.AbstractPlayer;
import com.mygdx.game.entities.enemies.standart.Missile;
import com.mygdx.game.entities.other.Warning;
import com.mygdx.game.handle.GameVars;

import java.util.LinkedList;
import java.util.Random;

public class MissileManager{
    com.mygdx.game.handle.entityManagers.BulletManager bulletManager;
    LinkedList<Missile> missiles;
    LinkedList<Warning> warnings;
    LinkedList<Float> yFloat;

    short scoreTimer;
    short countMissile;

    int counter;
    int alertTime;
    int maxMissile;
    int score;

    PolygonShape polygonShape;
    AbstractPlayer player;

    public MissileManager(BulletManager bulletManager){

        missiles = new LinkedList<>();
        warnings = new LinkedList<>();
        yFloat = new LinkedList<>();

        countMissile = 0;
        scoreTimer = 0;

        counter = 0;
        alertTime = 10;
        maxMissile = 1;
        score = 0;

        this.bulletManager = bulletManager;

        polygonShape = new PolygonShape();
        polygonShape.setAsBox(Missile.Width/2,Missile.Height/2);
    }

    private int getSeconds(int time){
        return time*60;
    }

    private short getSignum(long a){
        if (a%2 == 0)
            return 1;
        return -1;
    }


    private void createMissile(float y){
        Missile missile = new Missile(new Vector2(GameVars.V_WIDTH + Missile.Width,y),
                new Vector2(-5f,0f),polygonShape);
        missile.setUserData(GameVars.MISSILE + new Integer(countMissile).toString());
        countMissile++;
        bulletManager.addBullet(missile);
        missiles.add(missile);
    }

    public void draw(SpriteBatch spriteBatch,float parentAlpha){
        for (Warning w : warnings){
            w.draw(spriteBatch);
        }
        for (Missile m: missiles){
            m.draw(spriteBatch,parentAlpha);
        }
    }

    private void createRandomMissile(){
        if (counter == getSeconds(alertTime + 2)){
            for (Float f : yFloat){
                createMissile(f.floatValue());
            }
            yFloat.clear();
            warnings.clear();
            counter = 0;
        }
    }

    private void missileWarning(){
        if (counter == getSeconds(alertTime)){
          for (int i = 0;i < maxMissile;i++){
              float y = player.getY();
              float dY;
              while (true) {
                  dY = getSignum(new Random().nextLong())*((float) Math.random())*100f;
                  if (y + dY < GameVars.V_HEIGHT||y + dY > 0)
                      break;
              }
              yFloat.add(new Float(y+dY));
              warnings.add(new Warning(y + dY));
          }

        }
    }


    private void ScoreTimer(){
        if(score > 30&&scoreTimer == 0){
            scoreTimer = 1;
            alertTime = 25;
        }
        if(score > 60&&scoreTimer == 1){
            scoreTimer = 2;
            alertTime = 20;
            maxMissile = 2;
        }
        if(score > 90&&scoreTimer == 2){
            scoreTimer = 3;
            alertTime = 20;
            maxMissile = 3;
        }
        if(score > 120&&scoreTimer == 3){
            scoreTimer = 4;
            alertTime = 15;
            maxMissile = 4;
        }

    }

    public void update(){
        missileWarning();
        createRandomMissile();
        ScoreTimer();
        counter++;
        for (int index = 0;index < missiles.size();index++){
            if (missiles.get(index).wasDestroy){
                missiles.remove(index);
            }
        }
    }

    public void setAbstractPlayer(AbstractPlayer player){
        this.player = player;

    }

    public void setScore(int score){
        this.score = score;
    }
}
