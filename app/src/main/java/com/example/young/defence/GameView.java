package com.example.young.defence;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupWindow;

/**
 * Created by young on 2018-04-13.
 * 이미지는 모두 여기서 그립니다.
 * 밑의 로봇 클래스는 테스트용으로 작성해놓은 것이고,
 * 앞으로는 다른 파일에 움직임 등 작성하고 여기서 불러와 그립니다.
 */

public class GameView extends View {
    PopupWindow popupWindow_ground, popupWindow_base;
    View popupview_ground, popupview_base;
    ImageButton base, evolution1, evolution2;
    int clickedTower;
    Bitmap monsterImage;

    public GameView(Context context){
        super(context);
        setBackgroundResource(R.drawable.map1);
        Log.i("GameView", Float.toString(GameManager.checkPointList.get(0).getPosX()));

        popupview_ground = View.inflate(getContext(), R.layout.popup_ground, null);
        popupview_base = View.inflate(getContext(), R.layout.popup_base, null);
        popupWindow_ground = new PopupWindow(popupview_ground,270,150,true);
        popupWindow_base = new PopupWindow(popupview_base,270,150,true);
        base = findViewById(R.id.base);
        evolution1 = findViewById(R.id.evolution1);
        evolution2 = findViewById(R.id.evolution2);
        Bitmap monsterBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.robot);
        monsterImage = Bitmap.createScaledBitmap(monsterBitmap, monsterBitmap.getWidth() / 3, monsterBitmap.getHeight() / 3, false);
    }

    protected void onDraw(Canvas canvas){
        for(int i = 0; i<GameManager.towerArrayList.size();i++){
            canvas.drawBitmap(GameManager.towerArrayList.get(i).towerImage,Data.towerPosX[i],Data.towerPosY[i],null);

        }
        for(int i = 0; i < GameManager.monsterArrayList.size(); i++){
            Monster monster = GameManager.monsterArrayList.get(i);
//        canvas.drawBitmap(robot.bot, robot.posX, canvas.getHeight() / 2, null);
            canvas.drawBitmap(monsterImage,monster.getPosX(),monster.getPosY(),null);
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
                Log.i("touchPosition", x + " , " + y);
                for(int i=0;i<GameManager.towerArrayList.size();i++) {
                    if (x >Data.towerPosX[i]&&x<(Data.towerPosX[i]+150)&&y>Data.towerPosY[i]&&y<Data.towerPosY[i]+150){
//                        타워가 없는 상태일 때 클릭하면 1단계 타워로 올릴 수 있는 UI가 뜬다.
                        if(GameManager.towerArrayList.get(i).towerState==0) {
                            popupWindow_ground.setAnimationStyle(android.R.style.Animation_Translucent);
                            popupWindow_ground.showAtLocation(this, Gravity.NO_GRAVITY,
                                    (int) Data.towerPosX[i] - 50, (int) Data.towerPosY[i] + 150);
                            Log.i("touch", "X: " + x + "Y: " + y);
                            clickedTower = i;
                            Log.i("GameView", "clickedTower= " + clickedTower);
                            return true;
                        }
//                        타워가 1단계일 때 클릭하면 2단계 타워로 업그레이드할 수 있는 UI가 뜬다.
                        else if(GameManager.towerArrayList.get(i).towerState==1){
                            popupWindow_base.setAnimationStyle(android.R.style.Animation_Translucent);
                            popupWindow_base.showAtLocation(this, Gravity.NO_GRAVITY,
                                    (int) Data.towerPosX[i] - 50, (int) Data.towerPosY[i] + 150);
                            Log.i("touch", "X: " + x + "Y: " + y);
                            clickedTower = i;
                            Log.i("GameView", "clickedTower= " + clickedTower);
                            return true;
                        }
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
    public int getClickedTower(){
        return this.clickedTower;
    }
//    public void onClick_base(View view){
//        Tower tower = GameManager.towerArrayList.get(clickedTower);
//        tower.towerImage = BitmapFactory.decodeResource(getResources(), R.drawable.turret_base);
//    }
}

