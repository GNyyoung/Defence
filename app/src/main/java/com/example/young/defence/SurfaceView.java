package com.example.young.defence;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupWindow;

/**
 * Created by young on 2018-06-09.
 */

public class SurfaceView extends android.view.SurfaceView implements SurfaceHolder.Callback{

    PopupWindow popupWindow_ground, popupWindow_base;
    View popupview_ground, popupview_base;
    ImageButton base, evolution1, evolution2;
    Bitmap stop, play, pause;
    int clickedTower;
    Bitmap projectileBitmap;
    Bitmap heart, heartBitmap, money, moneyBitmap;
    private Paint paint = new Paint();
    Paint textPaint = new Paint();
    float dpX, dpY;
    Context context;
    SurfaceThread surfaceThread;
    SurfaceHolder mHolder;
    Bitmap map, mapBitmap;
    ConstraintLayout constraintLayout;
    float x, y;
    int animationDelay = 5;
    int frame = 0;

    public SurfaceView(Context context, AttributeSet attrs){
        super(context, attrs);
        this.context = context;
        mHolder = getHolder();
        mHolder.addCallback(this);
        setDp();
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
        boolean done = true;
        while (done) {
            try {
                surfaceThread.join();
                done = false;
            } catch (InterruptedException e) {
                Log.e("interrupted", e + "");
            }
        }
    }

    class SurfaceThread extends Thread{
        int alpha = 0;
        public void run(){
            Log.i("SurfaceThread", "스레드 동작");
            Canvas canvas = null;
            x = mHolder.getSurfaceFrame().width();
            y = mHolder.getSurfaceFrame().height();
            setDp();

            while(true){
                while (Data.pause == false){
                    if(Data.destroyActivity){
                        Data.destroyActivity = false;
                        return;
                    }
                    else {
                        canvas = mHolder.lockCanvas();
                        doDraw(canvas);
                        startStage(canvas);

                        mHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }

        public void doDraw(Canvas canvas){
            mapBitmap = Bitmap.createScaledBitmap(map, canvas.getWidth(), canvas.getHeight(), false);
            canvas.drawBitmap(mapBitmap,0,0,null);
            canvas.drawText(""+Data.playerMoney,850*dpX,90*dpY,textPaint);

            drawTower(canvas);
            drawMonster(canvas);
            drawProjectile(canvas);

            for(int i = 0; i < Data.playerHP ; i++){
                canvas.drawBitmap(heartBitmap,heartBitmap.getWidth() * dpX * (i+1) - heartBitmap.getWidth()/2, 20 * dpY,null);
            }
            canvas.drawBitmap(pause,2000 * dpX,50 * dpY,null);
            canvas.drawBitmap(moneyBitmap,700 * dpX,20 * dpY,null);
        }

        public void startStage(Canvas canvas){
            if(Data.startStage == true){
                Paint stagePaint = new Paint();
                stagePaint.setColor(Color.WHITE);
                stagePaint.setTextSize(200);
                if(alpha < 255){
                    stagePaint.setAlpha(alpha);
                    alpha += 3;
                }
                canvas.drawText("스테이지" + Integer.toString(Data.stage), 750 * dpX, 550 * dpY, stagePaint);
            }
            else
                alpha = 0;
        }
    }



    public boolean onTouchEvent(MotionEvent event){
        float x = event.getX();
        float y = event.getY();

        Log.i("GameView", Float.toString(x) + "," +Float.toString(y));

        if(event.getAction() == MotionEvent.ACTION_DOWN){
            Log.i("GameView", Integer.toString(GameManager.towerArrayList.size()));
            Log.i("touchPosition", x + " , " + y);
            for(int i = 0; i < GameManager.towerArrayList.size(); i++) {
                if (x > Data.towerPosX[i] * dpX && x < (Data.towerPosX[i] * dpX + GameManager.towerArrayList.get(i).towerImage.getWidth())
                        && y > Data.towerPosY[i] * dpY && y < Data.towerPosY[i] * dpY + GameManager.towerArrayList.get(i).towerImage.getHeight()){
//                        타워가 없는 상태일 때 클릭하면 1단계 타워로 올릴 수 있는 UI가 뜬다.
                    if(GameManager.towerArrayList.get(i).towerState == 0) {
                        popupWindow_ground.setAnimationStyle(android.R.style.Animation_Translucent);
                        popupWindow_ground.showAtLocation(this, Gravity.NO_GRAVITY,
                                (int) (Data.towerPosX[i] * dpX - 50 * dpX), (int) ((Data.towerPosY[i] * dpY) + 100 * dpY));
                        Log.i("touch", "X: " + x + "Y: " + y);
                        clickedTower = i;
                        Log.i("GameView", "clickedTower= " + clickedTower);
                        return true;
                    }
//                        타워가 1단계일 때 클릭하면 2단계 타워로 업그레이드할 수 있는 UI가 뜬다.
                    else if(GameManager.towerArrayList.get(i).towerState == 1){
                        popupWindow_base.setAnimationStyle(android.R.style.Animation_Translucent);
                        popupWindow_base.showAtLocation(this, Gravity.NO_GRAVITY,
                                (int) (Data.towerPosX[i] * dpX - 50 * dpX), (int) ((Data.towerPosY[i] * dpY) + 110 * dpY));
                        Log.i("touch", "X: " + x + "Y: " + y);
                        clickedTower = i;
                        Log.i("GameView", "clickedTower= " + clickedTower);
                        return true;

                    }
                }
            }
//            일시정지 버튼을 눌렀을 때 아이콘이 바뀌고 게임을 일시정지 하도록 한다.
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

    public void setDp(){
        Log.i("SurfaceView", "가로 크기 : " + Data.deviceWidth);
        Log.i("SurfaceView", "세로 크기 : " + Data.deviceHeight);
        Log.i("SurfaceView", "가로 픽셀 : " + Data.widthPixel);
        Log.i("SurfaceView", "세로 픽셀 : " + Data.heightPixel);
        Log.i("SurfaceView", "DPI : " + Data.deviceDpi);
        dpX = Data.deviceDpi / 420f;
        dpY = Data.deviceDpi / 420f;
        Log.i("SurfaceView", "가로 크기 : " + Float.toString(dpX));
        Log.i("SurfaceView", "세로 크기 : " + Float.toString(dpY));
    }

    private void drawTower(Canvas canvas){
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
    }
    private void drawMonster(Canvas canvas){
        for(int i = 0; i < GameManager.monsterArrayList.size(); i++){
            Monster monster = GameManager.monsterArrayList.get(i);
            float monsterImagePosX;
            float monsterImagePosY;

//            몬스터 이동 방향에 따라 몬스터 이미지 변경
            switch (monster.state){
                case 0:
                    if (frame > (monster.monstersRight.length * 5) - 1)
                        frame = 0;
                    monsterImagePosX = monster.getPosX() * dpX - (monster.monstersRight[frame / animationDelay].getWidth() / 2);
                    monsterImagePosY = monster.getPosY() * dpY - (monster.monstersRight[frame / animationDelay].getHeight() / 2);
                    canvas.drawBitmap(monster.monstersRight[frame / animationDelay], monsterImagePosX, monsterImagePosY, null);
                    break;
                case 1:
                    if (frame > (monster.monstersBack.length * 5) - 1)
                        frame = 0;
                    monsterImagePosX = monster.getPosX() * dpX - (monster.monstersBack[frame / animationDelay].getWidth() / 2);
                    monsterImagePosY = monster.getPosY() * dpY - (monster.monstersBack[frame / animationDelay].getHeight() / 2);
                    canvas.drawBitmap(monster.monstersBack[frame / animationDelay], monsterImagePosX, monsterImagePosY, null);
                    break;
                case 2:
                    if (frame > (monster.monstersFront.length * 5) - 1)
                        frame = 0;
                    monsterImagePosX = monster.getPosX() * dpX - (monster.monstersFront[frame / animationDelay].getWidth() / 2);
                    monsterImagePosY = monster.getPosY() * dpY - (monster.monstersFront[frame / animationDelay].getHeight() / 2);
                    canvas.drawBitmap(monster.monstersFront[frame / animationDelay], monsterImagePosX, monsterImagePosY, null);
                    break;
            }
        }
        frame++;
    }
    private  void drawProjectile(Canvas canvas){
        for(int i = 0; i < GameManager.projectileArrayList.size(); i++){
            Projectile projectile = GameManager.projectileArrayList.get(i);
            float projectilePosX = projectile.getPosX() * dpX - (projectileBitmap.getWidth() / 2);
            float projectilePosY = projectile.getPosY() * dpY - (projectileBitmap.getHeight() / 2);
            canvas.drawBitmap(projectile.projectileImage, projectilePosX, projectilePosY, null);
        }
    }
}
