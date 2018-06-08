package com.example.young.defence;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.Toast;

/**
 * Created by young on 2018-04-13.
 * 이미지는 모두 여기서 그립니다.
 * 밑의 로봇 클래스는 테스트용으로 작성해놓은 것이고,
 * 앞으로는 다른 파일에 움직임 등 작성하고 여기서 불러와 그립니다.
 *
 * 해야할 일
 * 1. 체력, 돈 표시하는 뷰 추가
 * 2. 기준좌표가 왼쪽 위라서 생기는 위치 문제 해결
 */

public class GameView extends View {
    PopupWindow popupWindow_ground, popupWindow_base;
    View popupview_ground, popupview_base;
    ImageButton base, evolution1, evolution2;
    Bitmap stop, play, pause;
    int clickedTower;
    Bitmap monsterImage, monsterBitmap;
    Bitmap projectileImage, projectileBitmap;
    Bitmap heart, heartBitmap;
    private int deviceDpi;
    private Paint paint = new Paint();
    int dp;

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
        monsterBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.right1);
        //monsterImage = Bitmap.createScaledBitmap(monsterBitmap, monsterBitmap.getWidth() / 3, monsterBitmap.getHeight() / 3, false);
        projectileBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.projectile);
        heart = BitmapFactory.decodeResource(getResources(),R.drawable.heart);
        heartBitmap = Bitmap.createScaledBitmap(heart,heart.getWidth()/20,heart.getHeight()/20,false);
        stop = BitmapFactory.decodeResource(getResources(),R.drawable.stop);
        play = BitmapFactory.decodeResource(getResources(),R.drawable.play);
        pause = stop;
    }

    protected void onDraw(Canvas canvas){
        paint.setColor(Color.RED);
        paint.setAlpha(44);
        for(int i = 0; i<GameManager.towerArrayList.size();i++){
            canvas.drawBitmap(
                    GameManager.towerArrayList.get(i).towerImage,
                    Data.towerPosX[i] * (deviceDpi / 420),
                    Data.towerPosY[i] * (deviceDpi / 420),null);
            if(GameManager.towerArrayList.get(i).towerState > 0){
                canvas.drawCircle(
                        Data.towerPosX[i] + (GameManager.towerArrayList.get(i).towerImage.getWidth() / 2),
                        Data.towerPosY[i] + (GameManager.towerArrayList.get(i).towerImage.getWidth() / 2),
                        400, paint);
            }
        }
        for(int i = 0; i < GameManager.monsterArrayList.size(); i++){
            Monster monster = GameManager.monsterArrayList.get(i);
            float monsterPosX = monster.getPosX() * dp - (monsterBitmap.getWidth() / 2);
            float monsterPosY = monster.getPosY() * dp - (monsterBitmap.getHeight() / 2);
//        canvas.drawBitmap(robot.bot, robot.posX, canvas.getHeight() / 2, null);
            canvas.drawBitmap(monsterBitmap, monsterPosX, monsterPosY,null);
        }
        for(int i = 0; i < GameManager.projectileArrayList.size(); i++){
            Projectile projectile = GameManager.projectileArrayList.get(i);
            float projectilePosX = projectile.getPosX() * dp - (projectileBitmap.getWidth() / 2);
            float projectilePosY = projectile.getPosY() * dp - (projectileBitmap.getHeight() / 2);
            canvas.drawBitmap(projectileBitmap, projectilePosX, projectilePosY, null);
        }
//        Log.i("GameView", Float.toString(GameManager.monsterArrayList.get(0).getPosX()));
        canvas.drawBitmap(heartBitmap,30 * dp, 20 * dp,null);
        canvas.drawBitmap(pause,2000,50,null);
        invalidate();
    }

//    타워 설치와 업그레이드에 관한 이벤트
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
                if(x > 2000 && x < 2000 + pause.getWidth() && y > 50 && y < 50 + pause.getHeight()){
                    if(pause == stop) {
                        pause = play;
                        Data.pause = true;
                    }
                    else {
                        pause = stop;
                        Data.pause = false;
                    }
                }
            }
        return false;
    }

    public int getClickedTower(){
        return this.clickedTower;
    }
//    public void onClick_base(View view){
//        Tower tower = GameManager.towerArrayList.get(clickedTower);
//        tower.towerImage = BitmapFactory.decodeResource(getResources(), R.drawable.turret_base);
//    }

    public void setDp(int Dpi){
        deviceDpi = Dpi;
        dp = deviceDpi / 420;
    }
}

