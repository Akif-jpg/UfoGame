package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.entities.abstracts.AbstractPlayer;
import com.mygdx.game.entities.other.Background;
import com.mygdx.game.entities.spaceships.UfoModel1f;
import com.mygdx.game.handle.MyInputListener;
import com.mygdx.game.handle.managers.ResourceManager;
import com.mygdx.game.handle.entityManagers.BarrierManager;
import com.mygdx.game.handle.entityManagers.BulletManager;
import com.mygdx.game.handle.GameVars;
import com.mygdx.game.handle.MyContactListener;
import com.mygdx.game.handle.managers.GameStateManager;
import com.mygdx.game.handle.entityManagers.MissileManager;
import com.mygdx.game.handle.entityManagers.StarManager;

public class PlayState extends GameStates {
    private World world;
    private BodyDef bdef;
    private FixtureDef fdef;
    private OrthographicCamera cam;
    private Box2DDebugRenderer b2dr;
    private BarrierManager brrManager;
    private BitmapFont scoreFont;

    private AbstractPlayer ufo;
    private Background bckgr;

    private MyInputListener inputListener;
    private MyContactListener cl;

    private BulletManager bulletManager;
    private MissileManager missileManager;
    private StarManager starManager;

    private short timeLoop2 = 0;
    private short handleTime = 0;
    private int gameScore = 0;


    public PlayState() {
        //set up GameVelocity
        GameVars.G_VELOCITY = -8f;


        bdef = new BodyDef();
        fdef = new FixtureDef();
        b2dr = new Box2DDebugRenderer();

        /*Creator Functions*/
        //set up world b2d
        createWorld();
        //create ResourceManager
        createResourceManager();
        //create BulletManager
        createBulletManager();
        //create MissileManager
        createMissileManager();
        //create Player
        createPlayer();
        //createStar
        createStar();
        //create Barrier
        createBarrier();
        //create Background
        createBackground();
        //create BitmapFont
        createScoreFont();



        //set up camera
        cam = new OrthographicCamera(GameVars.V_WIDTH, GameVars.V_HEIGHT);
        cam.position.set(GameVars.V_WIDTH / 2, GameVars.V_HEIGHT / 2, 0);
        cam.setToOrtho(false, GameVars.V_WIDTH, GameVars.V_HEIGHT);
        //set Up Inputs
        inputListener = new MyInputListener(ufo);
        Gdx.input.setInputProcessor(inputListener);


    }

    private void createBulletManager(){
        bulletManager = new BulletManager();
        cl.setBulletAction(bulletManager);
    }

     private void createResourceManager(){
        GameVars.resourceManager = new ResourceManager();

        GameVars.resourceManager.addTexture(new Texture(Gdx.files.internal("wall.png")),
                GameVars.WALL);
        GameVars.resourceManager.addTexture(new Texture(Gdx.files.internal("star.png")),
                GameVars.STAR);
        GameVars.resourceManager.addTexture(new Texture(Gdx.files.internal("helicopter.png")),
                GameVars.HELICOPTER);
        GameVars.resourceManager.addTexture(new Texture(Gdx.files.internal("redWarning-crop.png")),
                GameVars.WARNING);
        GameVars.resourceManager.addTexture(new Texture(Gdx.files.internal("fireBall.png")),
                GameVars.FIREBALL);
         GameVars.resourceManager.addTexture(new Texture(Gdx.files.internal("fireBall.png")),
                 GameVars.FIREBALL);


     }

     private void createMissileManager(){
        missileManager = new MissileManager(bulletManager);
     }

    private void createScoreFont() {
        scoreFont = new BitmapFont();
        scoreFont.setColor(Color.BLACK);
        scoreFont.getData().setScale(1);
    }

    private void createPlayer(){
        bdef.position.set(100,160);
        bdef.type = BodyDef.BodyType.DynamicBody;
        PolygonShape ps = new PolygonShape();
        Vector2[] vec = new Vector2[6];
        //Burada kaldım
        vec[0] = new Vector2(-10,-22);//Alt sol
        vec[1] = new Vector2(+10,-22);//Alt sağ
        vec[2] = new Vector2(-22,0);//Orta sol
        vec[3] = new Vector2(+22,0);//Orta sağ
        vec[4] = new Vector2(-12, + 22);//Üst sol
        vec[5] = new Vector2(+12, + 22);//üst sağ
        ps.set(vec);
        fdef.shape = ps;
        fdef.filter.categoryBits = GameVars.BIT_PLAYER;
        fdef.filter.maskBits = GameVars.BIT_BARRIER |GameVars.BIT_BOUNDARIES |GameVars.BIT_STAR | GameVars.BIT_MISSILE;
        ufo = new UfoModel1f(world.createBody(bdef));
        ufo.getFlpBody().createFixture(fdef).setUserData("@Player");
        cl.setPlayerAction(ufo);
        missileManager.setAbstractPlayer(ufo);

    }

    private void createStar() {
        starManager = new StarManager(this.world);
        this.cl.setStarAction(this.starManager);
    }



     private void createWorld(){
        cl = new MyContactListener();
         world = new World(new Vector2(0,-1.9f),true);
         world.setContactListener(cl);
         GameVars.world = this.world;

         //Definitions
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();

        //Top boundary
         bodyDef.position.set(GameVars.V_WIDTH/2,GameVars.V_HEIGHT+30);
         bodyDef.type = BodyDef.BodyType.StaticBody;

         PolygonShape box = new PolygonShape();
         box.setAsBox(GameVars.V_WIDTH/2,60/2);
         fixtureDef.shape = box;
         fixtureDef.friction = 0;

         fixtureDef.filter.categoryBits = GameVars.BIT_BOUNDARIES;
         fixtureDef.filter.maskBits = GameVars.BIT_PLAYER;
         world.createBody(bodyDef).createFixture(fixtureDef).setUserData(GameVars.BOUNDARIES);
         //Bottom boundary
         bodyDef.position.set(GameVars.V_WIDTH/2,-30f);
         world.createBody(bodyDef).createFixture(fixtureDef).setUserData(GameVars.BOUNDARIES);
        //Left side boundary
         bodyDef.position.set(-30f,GameVars.V_HEIGHT/2);

         box.setAsBox(60f/2,GameVars.V_HEIGHT/2);
         fixtureDef.shape = box;
         world.createBody(bodyDef).createFixture(fixtureDef).setUserData(GameVars.BOUNDARIES);
         //Right side boundary
         bodyDef.position.set(GameVars.V_WIDTH+30f,GameVars.V_HEIGHT/2);
         world.createBody(bodyDef).createFixture(fixtureDef).setUserData(GameVars.BOUNDARIES);
     }

     private void createBarrier(){
       brrManager = new BarrierManager(world);
       brrManager.setStarAction(starManager);
     }

     private void createBackground(){
        bckgr = new Background();
     }



    private void drawScore(SpriteBatch batch){
       String str = "score " + gameScore;
       int postpone = (str.length() -6) * 10;
       scoreFont.draw(batch,str,GameVars.V_WIDTH- scoreFont.getRegion().getRegionWidth()/2,
                GameVars.V_HEIGHT-15);
    }
    @Override
    public void render(SpriteBatch batch) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(cam.combined);
        //draw texture
        batch.begin();
        bckgr.draw(batch,0.85f);
        ufo.draw(batch,1f);
        starManager.drawStarList(batch,1f);
        brrManager.draw(batch);
        missileManager.draw(batch,1f);
        drawScore(batch);
        batch.end();

        //draw Box2DDebugRenderer
        b2dr.render(world,cam.combined);

    }

    @Override
    public void update(float dt) {
        handleInput();
        brrManager.update();
        starManager.update();
        ufo.update();
        missileManager.update();
        bulletManager.update();
        world.step(1/12f,6,2);
        updateGameVelocity();
        gameScore = starManager.collectedStar;
        missileManager.setScore(gameScore);

        if(!ufo.isAlive()) {
            GameStateManager.getState().dispose();
            GameStateManager.popState();
        }
    }

    @Override
    public void dispose() {
        b2dr.dispose();
        world.dispose();
        scoreFont.dispose();
        GameVars.resourceManager.dispose();

    }

    @Override
    public void handleInput() {

        if (handleTime > 2) {
            inputListener.movePlayer();
            handleTime = 0;
        }
        handleTime++;
    }

    private void updateGameVelocity(){
        timeLoop2++;
        if(timeLoop2 == 60*45){
            if(Math.abs(GameVars.G_VELOCITY) < 24f) {
                GameVars.G_VELOCITY -= 1f;
                timeLoop2 = 0;
            }

        }
    }
}
