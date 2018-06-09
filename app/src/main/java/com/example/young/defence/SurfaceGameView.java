//package com.example.young.defence;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Paint;
//import android.util.Log;
//import android.view.Gravity;
//import android.view.MotionEvent;
//import android.view.Surface;
//import android.view.SurfaceHolder;
//import android.view.SurfaceView;
//import android.view.View;
//import android.widget.ImageButton;
//import android.widget.PopupWindow;
//
///**
// * Created by YONG on 2018-06-09.
// */
//
// class SurfaceThread extends Thread {
//    SurfaceHolder mholder;
//
//    private Paint paint = new Paint();
//    float dpX, dpY;
//    public SurfaceThread(Context context, SurfaceHolder holder){
//        mholder = holder;
//        Log.i("GameView", Float.toString(GameManager.checkPointList.get(0).getPosX()));
//
//
//    }
//    public void run(){
//        while(true){
//            Canvas c = null;
//            try{
//                c = mholder.lockCanvas(null);
//                synchronized (mholder){
//                doDraw(c);
//                sleep(200);
//                }
//            }catch (Exception e){
//
//            }finally {
//                if(c != null){
//                    mholder.unlockCanvasAndPost(c);
//                }
//            }
//        }
//    }
//    private void doDraw(Canvas canvas){
//        paint.setColor(Color.RED);
//        paint.setAlpha(44);
//        for(int i = 0; i<GameManager.towerArrayList.size();i++){
//            canvas.drawBitmap(
//                    GameManager.towerArrayList.get(i).towerImage,
//                    Data.towerPosX[i] * dpX,
//                    Data.towerPosY[i] * dpY,null);
//            if(GameManager.towerArrayList.get(i).towerState > 0){
//                canvas.drawCircle(
//                        Data.towerPosX[i] + (GameManager.towerArrayList.get(i).towerImage.getWidth() / 2),
//                        Data.towerPosY[i] + (GameManager.towerArrayList.get(i).towerImage.getWidth() / 2),
//                        400, paint);
//            }
//        }
//        for(int i = 0; i < GameManager.monsterArrayList.size(); i++){
//            Monster monster = GameManager.monsterArrayList.get(i);
//            float monsterPosX = monster.getPosX() * dpX - (monster.monsterImage.getWidth() / 2);
//            float monsterPosY = monster.getPosY() * dpY - (monster.monsterImage.getHeight() / 2);
//
//            canvas.drawBitmap(monster.monsterImage, monsterPosX, monsterPosY,null);
//
//
//        }
//        for(int i = 0; i < GameManager.projectileArrayList.size(); i++){
//            Projectile projectile = GameManager.projectileArrayList.get(i);
//            float projectilePosX = projectile.getPosX() * dpX - (projectileBitmap.getWidth() / 2);
//            float projectilePosY = projectile.getPosY() * dpY - (projectileBitmap.getHeight() / 2);
//            canvas.drawBitmap(projectileBitmap, projectilePosX, projectilePosY, null);
//        }
////        Log.i("GameView", Float.toString(GameManager.monsterArrayList.get(0).getPosX()));
//        canvas.drawBitmap(heart,30 * dpX, 20 * dpY,null);
//        canvas.drawBitmap(pause,2000 * dpX,50 * dpY,null);
////        canvas.drawBitmap(heart,30,20,null);
//        canvas.drawBitmap(money,700 * dpX,20 * dpY,null);
//
//    }
//
//
//}
//
//public class SurfaceGameView extends SurfaceView implements SurfaceHolder.Callback{
//    SurfaceThread surfaceThread;
//    private float deviceDpi;
//    ImageButton base, evolution1, evolution2;
//    SurfaceGameView surfaceGameView;
//
//    PopupWindow popupWindow_ground, popupWindow_base;
//    View popupview_ground, popupview_base;
//    Bitmap stop, play, pause;
//    int clickedTower;
//    Bitmap monsterImage, monsterBitmap;
//    protected Bitmap projectileImage, projectileBitmap;
//    Bitmap heart, heartBitmap, money, moneyBitmap;
//    public SurfaceGameView(Context context){
//        super(context);
//        SurfaceHolder holder = getHolder();
//        holder.addCallback(this);
//        surfaceThread = new SurfaceThread(context, holder);
//
//        setBackgroundResource(R.drawable.map1);
//        base = findViewById(R.id.base);
//        evolution1 = findViewById(R.id.evolution1);
//        evolution2 = findViewById(R.id.evolution2);
//        popupview_ground = View.inflate(context, R.layout.popup_ground, null);
//        popupview_base = View.inflate(context, R.layout.popup_base, null);
//        popupWindow_ground = new PopupWindow(popupview_ground,270,150,true);
//        popupWindow_base = new PopupWindow(popupview_base,270,150,true);
//
////        monsterBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.right1);
//        //monsterImage = Bitmap.createScaledBitmap(monsterBitmap, monsterBitmap.getWidth() / 3, monsterBitmap.getHeight() / 3, false);
//        projectileBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.projectile);
//
//        pause = stop;
//        money = BitmapFactory.decodeResource(context.getResources(),R.drawable.money);
////        projectileImage = Bitmap.createScaledBitmap(projectileBitmap, projectileBitmap.getWidth() / 10, projectileBitmap.getHeight() / 10, false);
//        pause = stop;
//    }
//    public void surfaceCreated(SurfaceHolder holder){
//        surfaceThread.start();
//    }
//    public void surfaceDestroyed(SurfaceHolder holder){
//        boolean retry = true;
//
//        while(retry){
//            try{
//                surfaceThread.join();
//                retry = false;
//            }catch (Exception e){
//
//            }
//        }
//    }
//    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){
//
//    }
//    public void setDp(int Dpi){
//        deviceDpi = Dpi;
//        surfaceThread.dpX = (float)(deviceDpi / 420 * 16 / 18.5);
//        surfaceThread.dpY = deviceDpi / 420;
//    }
//    public boolean onTouchEvent(MotionEvent event){
//        float x = event.getX();
//        float y = event.getY();
//
//        Log.i("GameView", Float.toString(x) + "," +Float.toString(y));
//
//        if(event.getAction()==MotionEvent.ACTION_DOWN){
//            Log.i("GameView", Integer.toString(GameManager.towerArrayList.size()));
//            Log.i("touchPosition", x + " , " + y);
//            for(int i=0;i<GameManager.towerArrayList.size();i++) {
//                if (x >Data.towerPosX[i]&&x<(Data.towerPosX[i]+150)&&y>Data.towerPosY[i]&&y<Data.towerPosY[i]+150){
////                        타워가 없는 상태일 때 클릭하면 1단계 타워로 올릴 수 있는 UI가 뜬다.
//                    if(GameManager.towerArrayList.get(i).towerState==0) {
//                        popupWindow_ground.setAnimationStyle(android.R.style.Animation_Translucent);
//                        popupWindow_ground.showAtLocation(surfaceGameView, Gravity.NO_GRAVITY,
//                                (int) Data.towerPosX[i] - 50, (int) Data.towerPosY[i] + 150);
//                        Log.i("touch", "X: " + x + "Y: " + y);
//                        clickedTower = i;
//                        Log.i("GameView", "clickedTower= " + clickedTower);
//                        return true;
//                    }
////                        타워가 1단계일 때 클릭하면 2단계 타워로 업그레이드할 수 있는 UI가 뜬다.
//                    else if(GameManager.towerArrayList.get(i).towerState==1){
//                        popupWindow_base.setAnimationStyle(android.R.style.Animation_Translucent);
//                        popupWindow_base.showAtLocation(surfaceGameView, Gravity.NO_GRAVITY,
//                                (int) Data.towerPosX[i] - 50, (int) Data.towerPosY[i] + 150);
//                        Log.i("touch", "X: " + x + "Y: " + y);
//                        clickedTower = i;
//                        Log.i("GameView", "clickedTower= " + clickedTower);
//                        return true;
//
//                    }
//                }
//
//            }
////
//            if(x > 2000 && x < 2000 + pause.getWidth() && y > 50 && y < 50 + pause.getHeight()){
//                if(pause == stop) {
//                    pause = play;
//                    Data.pause = true;
//                }
//                else {
//                    pause = stop;
//                    Data.pause = false;
//                }
//            }
//        }
//        return false;
//    }
//    public int getClickedTower(){
//        return this.clickedTower;
//    }
//}
