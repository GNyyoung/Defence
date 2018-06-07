package com.example.young.defence;

import android.graphics.Bitmap;

/**
 * Created by young on 2018-05-03.
 * 해결할것
 * 1. 타겟에게 맞아도 이미지가 사라지지 않음.
 */

public class Projectile {

    int damage;
    float speed;
    float posX, posY;
    Monster target;
    private boolean isLived = true;
    //    투사체 이미지
    public Bitmap projectileImage;


    public Projectile(int damage, float speed, float towerPosX, float towerPosY, Monster target){
        this.damage = damage;
        this.speed = speed;
        posX = towerPosX;
        posY = towerPosY;
        this.target = target;
    }

//    thread에서 반복 실행
    public void followTarget(){
        float targetPosX = target.getPosX();
        float targetPosY = target.getPosY();


        float distanceX = targetPosX - posX;
        float distanceY = targetPosY - posY;

        float hypotenuse = (float)Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));
        float speedX, speedY;

        speedX = speed * distanceX / hypotenuse;
        speedY = speed * distanceY / hypotenuse;
        posX += speedX;
        posY += speedY;

        monsterCollisionCheck(target);
    }

    private void monsterCollisionCheck(Monster target){
        if(target.CollisionCheck(posX, posY)){
            target.damaged(damage);
            isLived = false;
        }
    }

    public float getPosX(){
        return posX;
    }
    public float getPosY(){
        return posY;
    }

    public boolean getLive(){
        return isLived;
    }
}
