package com.example.young.defence;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupWindow;

public class MainActivity extends AppCompatActivity {
    GameView v ;
    GameManager gameManager = new GameManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreen();
        gameManager.start();
        v=new GameView(this);
        setContentView(v);
// setContentView(R.layout.activity_main);
//        popupView = View.inflate(this,R.layout.popup_ground,null);
//        popupWindow = new PopupWindow(popupView,270,150,true);
//        for(int i=0;i<GameManager.towerArrayList.size();i++){
//            popupWindow.setAnimationStyle(android.R.style.Animation_Translucent);
//            popupWindow.showAtLocation(popupView, Gravity.NO_GRAVITY,(int)Data.towerPosX[i]-50,(int)Data.towerPosY[i]+100);

//        }
    }

//    전체화면으로 만드는 메소드
//    내비게이션바, 상태바 제거하고 맨 위쪽 또는 아래쪽 드래그시 잠시 나온 후 사라진다.
//    액션바는 제거하지 못함. styles.xml에서 따로 제거
    private void setFullScreen(){
        int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        boolean isImmersiveModeEnabled = ((uiOptions | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == uiOptions);
        if(!isImmersiveModeEnabled){
            newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
            newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
        }
    }

   /* protected class GameView extends View{
        public GameView(Context context){
            super(context);
            setBackgroundColor(Color.GRAY);
        }

        protected void onDraw(Canvas canvas){
//            Paint paint = new Paint();
//            paint.setColor(Color.RED);
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.robot);
            Bitmap robot = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() / 3, bitmap.getHeight() / 3, false);
            canvas.drawBitmap(robot, canvas.getWidth() / 2, canvas.getWidth() / 4 - bitmap.getWidth() / 2, null);

        }
    }*/
   public void onClick_Base(View view){

       Tower tower = GameManager.towerArrayList.get(v.getClickedTower());
       tower.towerImage = BitmapFactory.decodeResource(getResources(), R.drawable.turret_base);
       GameManager.towerArrayList.get(v.getClickedTower()).towerState=1;
       v.popupWindow_ground.dismiss();

   }
   public void onClick_Evo1(View view){
       Tower tower = GameManager.towerArrayList.get(v.getClickedTower());
       tower.towerImage = BitmapFactory.decodeResource(getResources(), R.drawable.turret_e1);
       GameManager.towerArrayList.get(v.getClickedTower()).towerState=2;
       v.popupWindow_base.dismiss();

   }
    public void onClick_Evo2(View view){
        Tower tower = GameManager.towerArrayList.get(v.getClickedTower());
        tower.towerImage = BitmapFactory.decodeResource(getResources(), R.drawable.turret_e2);
        GameManager.towerArrayList.get(v.getClickedTower()).towerState=3;
        v.popupWindow_base.dismiss();
    }
    @Override
    protected void onStop() {
        super.onStop();

        if(gameManager.isAlive())
            gameManager.interrupt();
    }
}
