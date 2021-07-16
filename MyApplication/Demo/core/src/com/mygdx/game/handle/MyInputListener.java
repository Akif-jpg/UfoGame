package com.mygdx.game.handle;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.interfaces.PlayerActions;

public class MyInputListener implements InputProcessor {
    private Vector2 drag1;
    private Vector2 drag2;
    private Vector2 deltaDrag;
    private PlayerActions playerActions;

    public MyInputListener(PlayerActions playerActions){
        drag1 = new Vector2();
        drag2 = new Vector2();
        deltaDrag = new Vector2();
        this.playerActions = playerActions;
    }
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(pointer == 0) {
            drag2.set(screenX,screenY);
        }

       return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if(pointer == 0) {
            deltaDrag.set(0,0);
        }

        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if(pointer == 0) {
            drag1.set(screenX, screenY);
            deltaDrag.set(drag1.x - drag2.x, drag2.y - drag1.y);
            drag2.set(screenX, screenY);

        }
        return false;
    }

    public void movePlayer(){
        playerActions.movePlayer(deltaDrag);
    }

    //unnecessary -------------------------
    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
