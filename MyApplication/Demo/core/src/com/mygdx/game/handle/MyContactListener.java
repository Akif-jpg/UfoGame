package com.mygdx.game.handle;


import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.entities.other.Barrier;
import com.mygdx.game.interfaces.BulletAction;
import com.mygdx.game.interfaces.PlayerActions;
import com.mygdx.game.interfaces.StarAction;



public class MyContactListener implements ContactListener {
    StarAction starAction;
    BulletAction bulletAction;
    private PlayerActions playerAction;

    public MyContactListener() {

    }

    @Override
    public void beginContact(Contact contact) {
        Fixture A = contact.getFixtureA();
        Fixture B = contact.getFixtureB();
      if (A != null && B != null) {
          if(A.getUserData() != null && B.getUserData() != null) {
              isStar_Player(A, B);
              isMissile_Player(A, B);
              isBarrier_Player(A,B);
          }
        }

    }
    public void setStarAction(StarAction action){
        this.starAction = action;
    }
    public void setBulletAction(BulletAction action){this.bulletAction = action;}
    public void setPlayerAction(PlayerActions action){this.playerAction = action;}

    public void isBarrier_Player(Fixture A,Fixture B){
        if(A.getUserData().toString().equals(GameVars.WALL)){
            if(B.getUserData().toString().equals("@Player")){
                playerAction.takeDamage(Barrier.calculateDamage(B.getBody().getLinearVelocity(),A.getBody().getLinearVelocity()));
            }
        }
        if(B.getUserData().toString().equals(GameVars.WALL)){
            if(A.getUserData().toString().equals("@Player")){
                playerAction.takeDamage(Barrier.calculateDamage(A.getBody().getLinearVelocity(),B.getBody().getLinearVelocity()));
            }
        }

    }

    public void isStar_Player(Fixture A,Fixture B){
        if (A.getUserData().toString().substring(0, GameVars.STAR.length()).equals(GameVars.STAR)) {
            if (B.getUserData().toString().equals("@Player")) {
                starAction.collectStar(A.getUserData().toString());
            }
        }
        if (B.getUserData().toString().substring(0, GameVars.STAR.length()).equals(GameVars.STAR)) {
            if (A.getUserData().toString().equals("@Player")) {
                starAction.collectStar(B.getUserData().toString());

            }
        }
    }



    public void isMissile_Player(Fixture A,Fixture B){
        if (A.getUserData().toString().substring(0,GameVars.MISSILE.length()).equals(GameVars.MISSILE)){
            bulletAction.destroyBullet(A.getUserData().toString());
            if (B.getUserData().toString().equals("@Player")) {
                playerAction.takeDamage(GameVars.missileDamage);
            }
        }
        if (B.getUserData().toString().substring(0,GameVars.MISSILE.length()).equals(GameVars.MISSILE)){
            bulletAction.destroyBullet(B.getUserData().toString());
            if (A.getUserData().toString().equals("@Player")) {
                playerAction.takeDamage(GameVars.missileDamage);


            }
        }

    }

    //unnecessary
    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
