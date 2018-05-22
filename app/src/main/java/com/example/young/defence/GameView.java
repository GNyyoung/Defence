package com.example.young.defence;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import java.util.ArrayList;

/**
 * Created by young on 2018-04-13.
 * 이미지는 모두 여기서 그립니다.
 * 밑의 로봇 클래스는 테스트용으로 작성해놓은 것이고,
 * 앞으로는 다른 파일에 움직임 등 작성하고 여기서 불러와 그립니다.
 */

public class GameView extends View {
    PopupWindow popupWindow;
    View popupview;
    ImageButton base;
    int clickedTower;
    public GameView(Context context){
        super(context);
        setBackgroundResource(R.drawable.map1);
        Log.i("GameView", Float.toString(GameManager.checkPointList.get(0).getPosX()));

        popupview = View.inflate(getContext(), R.layout.popup, null);
        popupWindow = new PopupWindow(popupview,270,150,true);
        base = findViewById(R.id.base);
//        base.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                Tower tower = GameManager.towerArrayList.get(clickedTower);
//                tower.towerImage = BitmapFactory.decodeResource(getResources(), R.drawable.turret_base);
//            }
//        });
    }

    Robot robot = new Robot();

    protected void onDraw(Canvas canvas){
        for(int i = 0; i<GameManager.towerArrayList.size();i++){
            canvas.drawBitmap(GameManager.towerArrayList.get(i).towerImage,Data.towerPosX[i],Data.towerPosY[i],null);

        }
        for(int i = 0; i < GameManager.monsterArrayList.size(); i++){
            Monster monster = GameManager.monsterArrayList.get(i);

//        canvas.drawBitmap(robot.bot, robot.posX, canvas.getHeight() / 2, null);
            canvas.drawBitmap(monster.monsterImage,monster.getPosX(),monster.getPosY(),null);
        }
//        Log.i("GameView", Float.toString(GameManager.monsterArrayList.get(0).getPosX()));

        invalidate();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
        float x = event.getX();
        float y = event.getY();

        Log.i("GameView", Float.toString(x) + "," +Float.toString(y));

            if(event.getAction()==MotionEvent.ACTION_DOWN){
                Log.i("GameView", Integer.toString(GameManager.towerArrayList.size()));
                for(int i=0;i<GameManager.towerArrayList.size();i++) {
                    if (x >Data.towerPosX[i]&&x<(Data.towerPosX[i]+100)&&y>Data.towerPosY[i]&&y<Data.towerPosY[i]+100){
                        popupWindow.setAnimationStyle(android.R.style.Animation_Translucent);
                        popupWindow.showAtLocation(this, Gravity.NO_GRAVITY,
                                (int)Data.towerPosX[i]-50, (int)Data.towerPosY[i]+100);
                        Log.i("touch","X: "+x+"Y: "+y);
                        clickedTower = i;

                        return true;
                    }

                }
            }

        return false;
    }


    private class Robot{
        float posX, posY;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.robot);
        Bitmap bot = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() / 3, bitmap.getHeight() / 3, false);

    }

    public void createMonster(Monster monster){

    }

//    public void onClick_base(View view){
//        Tower tower = GameManager.towerArrayList.get(clickedTower);
//        tower.towerImage = BitmapFactory.decodeResource(getResources(), R.drawable.turret_base);
//    }
}

