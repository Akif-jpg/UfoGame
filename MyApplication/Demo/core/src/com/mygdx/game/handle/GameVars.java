package com.mygdx.game.handle;

import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.handle.managers.ResourceManager;

public class GameVars {
    public final static int V_WIDTH = 400;
    public final static int V_HEIGHT = 320;

    public final static short BIT_PLAYER = 1;
    public final static short BIT_BARRIER = 2;
    public final static short BIT_BOUNDARIES = 4;
    public final static short BIT_BULLET = 8;
    public final static short BIT_STAR = 16;
    public final static short BIT_MISSILE = 32;

    public final  static String WALL = "@Wall";
    public final  static String STAR = "@Star";
    public final  static String HELICOPTER = "@Helicopter";
    public final static String BOUNDARIES = "@Boundaries";
    public final static String MISSILE = "@Miss";
    public final static String WARNING = "@Warning";
    public final static String FIREBALL = "@Fireball";

    public static float G_VELOCITY = -8f;
    public static float starVolume = 10f;
    public static ResourceManager resourceManager;
    public static World world;

    public static float missileDamage = 10f;
}
