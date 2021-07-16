package com.mygdx.game.handle.managers;

import com.mygdx.game.states.GameStates;

import java.util.Stack;

public class GameStateManager {
    private static Stack<GameStates> states = new Stack<GameStates>();

    public static void pushState(GameStates state){
        states.push(state);
    }

    public static GameStates getState(){return states.peek();}

    public  static  void popState(){states.pop();}

    public static void clear(){states.clear();}

    public static boolean isEmpty(){return states.isEmpty();}
}
