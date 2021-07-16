package com.mygdx.game.handle.entityManagers;

import com.mygdx.game.entities.abstracts.Bullet;
import com.mygdx.game.interfaces.BulletAction;

import java.util.LinkedList;

public class BulletManager implements BulletAction {
    LinkedList<Bullet> bullets;
    LinkedList<Bullet> destroyBullets;
    LinkedList<Integer> destroyTimer;

    public BulletManager(){
        bullets = new LinkedList<>();
        destroyBullets = new LinkedList<>();
        destroyTimer = new LinkedList<>();

    }

    public void addBullet(Bullet bullet){
        this.bullets.add(bullet);
    }

    @Override
    public void destroyBullet(String id) {
        for (Bullet b : bullets){
            if (b.getUserData().toString().equals(id)){
                destroyTimer.add(new Integer(b.getSuspensive()));
                destroyBullets.add(b);
                b.willDestroy = true;
                bullets.remove(b);
                break;

            }
        }
    }

    public void update(){
       for (int index = 0;index < destroyBullets.size();index++){
            if (destroyTimer.get(index).intValue() > 0){
                Integer i = destroyTimer.get(index);
                destroyTimer.remove(index);
                int a = i.intValue();
                i = new Integer(a- 1);
                destroyTimer.add(index,i);
            }else {
                destroyBullets.get(index).wasDestroy = true;
                destroyBullets.get(index).destroy();
                destroyBullets.remove(index);
                destroyTimer.remove(index);

            }
        }

       for (int index = 0;index < bullets.size();index++){
           Bullet b = bullets.get(index);
           b.update();
           if (b.getWentWay() > b.getMaxRange()){
               destroyBullets.add(b);
               destroyTimer.add(b.getSuspensive());
               b.willDestroy = true;
               bullets.remove(b);
           }
       }
    }
}
