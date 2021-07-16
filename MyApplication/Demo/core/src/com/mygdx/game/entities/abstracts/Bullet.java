package com.mygdx.game.entities.abstracts;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.handle.GameVars;

public abstract class Bullet {
    protected Animation<TextureRegion> beginAnimation;
    protected Animation<TextureRegion> crashAnimation;
    protected Animation<TextureRegion> normalAnimation;
    protected short maskBits;
    protected short categoryBits;
    protected int Suspensive = 0;
    protected float maxRange = 100;
    protected float x = 0;
    protected float y = 0;

    public boolean wasDestroy;
    public boolean willDestroy;
    public Vector2 position;
    public Vector2 direction;
    public float rotate;

    private float wentWay = 0;
    private Object userData;
    private PolygonShape polygonShape;
    private BodyDef bDef;
    private FixtureDef fDef;
    private Body body;
    private World world;
    public Bullet(Vector2 position, Vector2 direction, PolygonShape polygonShape){
        bDef = new BodyDef();
        fDef = new FixtureDef();
        this.polygonShape = polygonShape;

        this.position = position;
        this.direction = direction;
        this.world = GameVars.world;

        //create body
        bDef.position.set(position);
        bDef.type = BodyDef.BodyType.StaticBody;
        fDef.shape = polygonShape;
        fDef.restitution = 0.7f;
        this.body = world.createBody(bDef);
        body.createFixture(fDef);
        //end


        this.willDestroy = false;
        this.wasDestroy = false;


    }



   public abstract void draw(SpriteBatch spriteBatch,float parentAlpha);

    public void destroy(){
        world.destroyBody(this.body);
    }

   public void update(){
        wentWay += direction.dst(0,0);
       position.set(position.x + direction.x,position.y + direction.y);
       world.destroyBody(body);
       bDef.position.set(position);
       body = world.createBody(bDef);
       body.createFixture(fDef).setUserData(this.userData);

       x = body.getPosition().x;
       y = body.getPosition().y;

    }

    public int getSuspensive() {
        return this.Suspensive;
    }
    public float getMaxRange(){return maxRange;}
    public float getWentWay(){return wentWay;}
    public void setUserData(Object userData){
        this.userData = userData;

    }
    public Object getUserData(){
        return userData;
    }
    public void setFilter(short maskBits,short categoryBits){
        this.maskBits = maskBits;
        this.categoryBits = categoryBits;
        fDef.filter.maskBits = maskBits;
        fDef.filter.categoryBits = categoryBits;
    }
}
