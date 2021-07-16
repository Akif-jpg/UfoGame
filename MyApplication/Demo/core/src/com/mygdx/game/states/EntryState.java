package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.handle.managers.GameStateManager;

public class EntryState extends GameStates {
    private Texture bckgrnd;
    private OrthographicCamera camera;

    int screenWidth;
    int screenHeight;
    public EntryState(){
        bckgrnd = new Texture(Gdx.files.internal("EnterMenu.jpg"));
        screenHeight = Gdx.graphics.getHeight();
        screenWidth = Gdx.graphics.getWidth();
        camera = new OrthographicCamera(screenWidth,screenHeight);
        camera.setToOrtho(false,screenWidth,screenHeight);
        camera.position.set(screenWidth/2,screenHeight/2,0);
    }
    @Override
    public void render(SpriteBatch batch) {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(bckgrnd,45,45,screenWidth-90,screenWidth/2-90);
        batch.end();
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void dispose() {
        bckgrnd.dispose();
    }

    @Override
    public void handleInput() {
        if(Gdx.input.isTouched()){
            GameStateManager.pushState(new PlayState());
        }
    }
}
