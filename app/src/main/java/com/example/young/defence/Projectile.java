package com.example.young.defence;

import android.graphics.Bitmap;

/**
 * Created by young on 2018-05-03.
 */

public class Projectile {

    int damage;
    float speed;
    float posX, posY;
    //    투사체 이미지
    public Bitmap projectileImage;


    public Projectile(int damage, float speed, float towerPosX, float towerPosY){
        this.damage = damage;
        this.speed = speed;
        posX = towerPosX;
        posY = towerPosY;
    }

//    thread에서 반복 실행
    private void followTarget(Monster target){
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
        }
    }
}
