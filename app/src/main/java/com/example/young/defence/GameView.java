package com.example.young.defence;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by young on 2018-04-13.
 * 이미지는 모두 여기서 그립니다.
 * 밑의 로봇 클래스는 테스트용으로 작성해놓은 것이고,
 * 앞으로는 다른 파일에 움직임 등 작성하고 여기서 불러와 그립니다.
 */

public class GameView extends View{
    public GameView(Context context){
        super(context);
        setBackgroundResource(R.drawable.map1);
        Log.i("GameView", Float.toString(GameManager.checkPointList.get(0).getPosX()));
    }

    Robot robot = new Robot();
    Tower tower = new Tower(getContext(), 10, 10,10,50,50);

    protected void onDraw(Canvas canvas){
        for(int i = 0; i < GameManager.monsterArrayList.size(); i++){
            Monster monster = GameManager.monsterArrayList.get(i);
            canvas.drawBitmap(tower.towerImage,50,50,null);
//        canvas.drawBitmap(robot.bot, robot.posX, canvas.getHeight() / 2, null);
            canvas.drawBitmap(monster.monsterImage,monster.getPosX(),monster.getPosY(),null);
        }
//        Log.i("GameView", Float.toString(GameManager.monsterArrayList.get(0).getPosX()));
        invalidate();
    }


    private class Robot{
        float posX, posY;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.robot);
        Bitmap bot = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() / 3, bitmap.getHeight() / 3, false);

    }

    public void createMonster(Monster monster){

    }
}

