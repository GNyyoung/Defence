package com.example.young.defence;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Created by young on 2018-06-09.
 */

public class SurfaceView extends android.view.SurfaceView implements SurfaceHolder.Callback{

    PopupWindow popupWindow_ground, popupWindow_base;
    View popupview_ground, popupview_base;
    ImageButton base, evolution1, evolution2;
    Bitmap stop, play, pause;
    int clickedTower;
    Bitmap monsterImage, monsterBitmap;
    Bitmap projectileImage, projectileBitmap;
    Bitmap heart, heartBitmap, money, moneyBitmap;
    private float deviceDpi;
    private Paint paint = new Paint();
    Paint textPaint = new Paint();
    float dpX, dpY;
//    TextView moneyText;
    Context context;
    SurfaceThread surfaceThread;
    SurfaceHolder mHolder;
    Bitmap map, mapBitmap;
    ConstraintLayout constraintLayout;
    public SurfaceView(Context context, AttributeSet attrs){
        super(context, attrs);
        this.context = context;
        mHolder = getHolder();
        mHolder.addCallback(this);
        base = findViewById(R.id.base);
        map = BitmapFactory.decodeResource(getResources(), R.drawable.map1);
        popupview_ground = SurfaceView.inflate(getContext(), R.layout.popup_ground, null);
        popupview_base = SurfaceView.inflate(getContext(), R.layout.popup_base, null);
        popupWindow_ground = new PopupWindow(popupview_ground,250,260,true);
        popupWindow_base = new PopupWindow(popupview_base,250,260,true);
        base = findViewById(R.id.base);
        evolution1 = findViewById(R.id.evolution1);
        evolution2 = findViewById(R.id.evolution2);
        projectileBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.projectile);
        heart = BitmapFactory.decodeResource(getResources(),R.drawable.heart);
        heartBitmap = Bitmap.createScaledBitmap(heart, heart.getWidth()/2, heart.getHeight()/2, false);
        stop = BitmapFactory.decodeResource(getResources(),R.drawable.stop);
        play = BitmapFactory.decodeResource(getResources(),R.drawable.play);
        pause = stop;
        money = BitmapFactory.decodeResource(getResources(),R.drawable.money);
        moneyBitmap = Bitmap.createScaledBitmap(money, money.getWidth()/2, money.getHeight()/2, false);
        constraintLayout = findViewById(R.id.con);
//        moneyText = new TextView(context);
//        moneyText.setTextColor(Color.WHITE);

        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(100);
        paint.setColor(Color.RED);
        paint.setAlpha(44);
        Log.i("SurfaceView", "서피스뷰 시작");


    }

    public void surfaceCreated(SurfaceHolder holder){
        Log.i("SurfaceView", "서피스뷰 생성");
        surfaceThread = new SurfaceThread();
        surfaceThread.start();

    }
    public void surfaceChanged(SurfaceHolder arg0, int format, int width, int height){
        Log.i("SurfaceView", "서피스뷰 변경");
    }
    public void surfaceDestroyed(SurfaceHolder holder){
        Log.i("SurfaceView", "서피스뷰 종료");
    }

    class SurfaceThread extends Thread{
//        SurfaceHolder mHolder;

//        public SurfaceThread(SurfaceHolder holder, Context context){
//            mHolder = holder;
//        }

        public void run(){
            Log.i("SurfaceThread", "스레드 동작");
            Canvas canvas = null;

            while (true){
                canvas = mHolder.lockCanvas();
                onDraw(canvas);
                mHolder.unlockCanvasAndPost(canvas);
            }
        }

        public void onDraw(Canvas canvas){
//            ((ConstraintLayout)this.getParent()).removeView(moneyText);
//            canvas.drawBitmap(map,0,0,null);
            mapBitmap = Bitmap.createScaledBitmap(map, canvas.getWidth(), canvas.getHeight(), false);
            canvas.drawBitmap(mapBitmap,0,0,null);
//            moneyText.setText(""+Data.playerMoney);
//            moneyText.setX(850 * dpX);
//            moneyText.setY(10 * dpY);
//            moneyText.setTextSize(30);
            canvas.drawText(""+Data.playerMoney,850*dpX,90*dpY,textPaint);
//            ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
//            moneyText.setLayoutParams(lp);
//            ((ConstraintLayout)this.getParent()).addView(moneyText);

            for(int i = 0; i<GameManager.towerArrayList.size();i++){
                canvas.drawBitmap(
                        GameManager.towerArrayList.get(i).towerImage,
                        Data.towerPosX[i] * dpX,
                        Data.towerPosY[i] * dpY,null);
                if(GameManager.towerArrayList.get(i).towerState > 0){
                    canvas.drawCircle(
                            Data.towerPosX[i]*dpX + (GameManager.towerArrayList.get(i).towerImage.getWidth() / 2),
                            Data.towerPosY[i]*dpY + (GameManager.towerArrayList.get(i).towerImage.getWidth() / 2),
                            400, paint);
                }
            }
            for(int i = 0; i < GameManager.monsterArrayList.size(); i++){
                Monster monster = GameManager.monsterArrayList.get(i);
                float monsterPosX = monster.getPosX() * dpX - (monster.monsterImage.getWidth() / 2);
                float monsterPosY = monster.getPosY() * dpY - (monster.monsterImage.getHeight() / 2);

                canvas.drawBitmap(monster.monsterImage, monsterPosX, monsterPosY,null);

            }
            for(int i = 0; i < GameManager.projectileArrayList.size(); i++){
                Projectile projectile = GameManager.projectileArrayList.get(i);
                float projectilePosX = projectile.getPosX() * dpX - (projectileBitmap.getWidth() / 2);
                float projectilePosY = projectile.getPosY() * dpY - (projectileBitmap.getHeight() / 2);
                canvas.drawBitmap(projectileBitmap, projectilePosX, projectilePosY, null);
            }
//        Log.i("GameView", Float.toString(GameManager.monsterArrayList.get(0).getPosX()));
            for(int i = 0; i < Data.playerHP ; i++){
                canvas.drawBitmap(heartBitmap,heartBitmap.getWidth() * dpX * (i+1) - heartBitmap.getWidth()/2, 20 * dpY,null);
            }

            canvas.drawBitmap(pause,2000 * dpX,50 * dpY,null);
//        canvas.drawBitmap(heart,30,20,null);
            canvas.drawBitmap(moneyBitmap,700 * dpX,20 * dpY,null);
        }
    }


    public boolean onTouchEvent(MotionEvent event){
        float x = event.getX();
        float y = event.getY();

        Log.i("GameView", Float.toString(x) + "," +Float.toString(y));

        if(event.getAction()==MotionEvent.ACTION_DOWN){
            Log.i("GameView", Integer.toString(GameManager.towerArrayList.size()));
            Log.i("touchPosition", x + " , " + y);
            for(int i=0;i<GameManager.towerArrayList.size();i++) {
                if (x >Data.towerPosX[i]*dpX&&x<(Data.towerPosX[i]*dpX+GameManager.towerArrayList.get(i).towerImage.getWidth())
                        &&y>Data.towerPosY[i]*dpY&&y<Data.towerPosY[i]*dpY+GameManager.towerArrayList.get(i).towerImage.getHeight()){
//                        타워가 없는 상태일 때 클릭하면 1단계 타워로 올릴 수 있는 UI가 뜬다.
                    if(GameManager.towerArrayList.get(i).towerState==0) {
                        popupWindow_ground.setAnimationStyle(android.R.style.Animation_Translucent);
                        popupWindow_ground.showAtLocation(this, Gravity.NO_GRAVITY,
                                (int) (Data.towerPosX[i]*dpX - 50*dpX), (int) ((Data.towerPosY[i]*dpY) + 100*dpY));
                        Log.i("touch", "X: " + x + "Y: " + y);
                        clickedTower = i;
                        Log.i("GameView", "clickedTower= " + clickedTower);
                        return true;
                    }
//                        타워가 1단계일 때 클릭하면 2단계 타워로 업그레이드할 수 있는 UI가 뜬다.
                    else if(GameManager.towerArrayList.get(i).towerState==1){
                        popupWindow_base.setAnimationStyle(android.R.style.Animation_Translucent);
                        popupWindow_base.showAtLocation(this, Gravity.NO_GRAVITY,
                                (int) (Data.towerPosX[i]*dpX - 50*dpX), (int) ((Data.towerPosY[i]*dpY) + 110*dpY));
                        Log.i("touch", "X: " + x + "Y: " + y);
                        clickedTower = i;
                        Log.i("GameView", "clickedTower= " + clickedTower);
                        return true;

                    }
                }

            }
//
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

    public void setDp(float Dpi){
        deviceDpi = Dpi;
        dpX = (float)(deviceDpi / 420 * 16 / 18.5);
        dpY = deviceDpi / 420;
    }
}
