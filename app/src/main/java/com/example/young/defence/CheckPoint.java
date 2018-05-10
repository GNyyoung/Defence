package com.example.young.defence;

/**
 * Created by young on 2018-04-14.
 */

public class CheckPoint {
    private float posX, posY;
    private float collisionRadius = 1;
    private int number;

    public CheckPoint(float posX, float posY, int number){
        this.posX = posX;
        this.posY = posY;
        this.number = number;
    }

    public float getPosX(){
        return posX;
    }
    public float getPosY(){
        return posY;
    }

    public boolean CollisionCheck(float monsterPosX, float monsterposY){
        if(monsterPosX > posX - collisionRadius
                && monsterPosX < posX + collisionRadius
                && monsterposY > posY - collisionRadius
                && monsterposY < posY + collisionRadius)
            return true;
        else
            return false;
    }

    public int getNumber(){
        return number;
    }
}
