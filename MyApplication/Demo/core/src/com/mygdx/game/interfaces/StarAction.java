package com.mygdx.game.interfaces;

import com.mygdx.game.entities.other.Barrier;

public interface StarAction {
    public void createStar(Barrier barrier);
    public  void collectStar(String code);
}
