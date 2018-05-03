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

    private void followTarget(Monster target){
        float targetPosX = target.getPosX();
        float targetPosY = target.getPosY();


    }
}
