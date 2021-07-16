package com.mygdx.game.handle.managers;

import com.badlogic.gdx.graphics.Texture;

import java.util.LinkedList;

public class ResourceManager {
    private Texture[] textures;
    private LinkedList<String> nameList;
    private int count = 0;
    public ResourceManager(){
        textures = new Texture[12];
        nameList = new LinkedList<String>();
    }
    public void addTexture(Texture texture,String keyCode){
        boolean isSame = false;
        for(String str : nameList){
            if(keyCode.equals(str)){
                isSame = true;
                break;
            }
        }
        assert !isSame : "this keyCode was used before";
        textures[count] = texture;
        nameList.add(keyCode);
        count++;


    }

    public Texture getTexture(int count){
        return textures[count];
    }

    public Texture getTexture(String name){
       int index = 0;
       for(String e : nameList){
           if (e.equals(name)){
               return getTexture(index);
           }
           index+=1;
       }
       return null;
    }

    public void dispose(){
        for (int index = 0;index < textures.length; index++){
            if (textures[index] != null) {
                textures[index].dispose();
            }
        }
    }

}
