package com.example.young.defence;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    SurfaceView surfaceView;
    GameManager gameManager;
    private View decorView;
    private int uiOption;
    EndDialog endDialog = null;
    GameoverDialog gameoverDialog = null;
    WinDialog winDialog = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager clsWindowManager = (WindowManager)getSystemService( WINDOW_SERVICE );
        Display clsDisplay = clsWindowManager.getDefaultDisplay();
        Point clsSize = new Point();
        clsDisplay.getSize( clsSize );
        Data.deviceWidth = clsSize.x;
        Data.deviceHeight = clsSize.y;

        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        Data.deviceDpi = outMetrics.densityDpi;
        Data.widthPixel = outMetrics.widthPixels;
        Data.heightPixel = outMetrics.heightPixels;

        gameManager = new GameManager(this);
        gameManager.start();
        setContentView(R.layout.activity_main);
        surfaceView = findViewById(R.id.surfaceView);

        decorView = getWindow().getDecorView();
        setFullScreen();
        decorView.setOnSystemUiVisibilityChangeListener(
            new View.OnSystemUiVisibilityChangeListener() {
                @Override
                public void onSystemUiVisibilityChange(int visibility) {
                    setFullScreen();
                }
            });

        gameoverDialog = new GameoverDialog(this);
        winDialog = new WinDialog(this);
    }

//    전체화면으로 만드는 메소드
//    내비게이션바, 상태바 제거하고 맨 위쪽 또는 아래쪽 드래그시 잠시 나온 후 사라진다.
//    액션바는 제거하지 못함. styles.xml에서 따로 제거
    private void setFullScreen(){
        uiOption = getWindow().getDecorView().getSystemUiVisibility();
        uiOption |= surfaceView.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        uiOption |= surfaceView.SYSTEM_UI_FLAG_FULLSCREEN;
        uiOption |= surfaceView.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        uiOption |= surfaceView.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
        uiOption |= surfaceView.SYSTEM_UI_FLAG_LAYOUT_STABLE;

        decorView.setSystemUiVisibility( uiOption );
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        if( hasFocus ) {
            decorView.setSystemUiVisibility( uiOption );
        }
    }

    public void onClick_Base(View view){
        if(Data.playerMoney >= 20){
            Data.playerMoney -= 20;
            Tower tower = GameManager.towerArrayList.get(surfaceView.getClickedTower());
            tower.towerImage = BitmapFactory.decodeResource(getResources(), R.drawable.turret_base);
            GameManager.towerArrayList.get(surfaceView.getClickedTower()).towerState=1;
            GameManager.towerArrayList.get(surfaceView.getClickedTower()).activate();
            surfaceView.popupWindow_ground.dismiss();
            GameManager.towerArrayList.get(surfaceView.getClickedTower()).activate();
        }
        else
            Toast.makeText(this, "금액이 부족합니다.", Toast.LENGTH_SHORT).show();

    }
    public void onClick_Evo1(View view){
       if(Data.playerMoney >= 30){
           Data.playerMoney -= 30;
           Tower tower = GameManager.towerArrayList.get(surfaceView.getClickedTower());
           tower.towerImage = BitmapFactory.decodeResource(getResources(), R.drawable.turret_e1);
           GameManager.towerArrayList.get(surfaceView.getClickedTower()).towerState=2;
           surfaceView.popupWindow_base.dismiss();
       }
       else
           Toast.makeText(this, "금액이 부족합니다.", Toast.LENGTH_SHORT).show();

    }
    public void onClick_Evo2(View view){
       if(Data.playerMoney >= 30){
           Data.playerMoney -= 30;
           Tower tower = GameManager.towerArrayList.get(surfaceView.getClickedTower());
           tower.towerImage = BitmapFactory.decodeResource(getResources(), R.drawable.turret_e2);
           GameManager.towerArrayList.get(surfaceView.getClickedTower()).towerState=3;
           surfaceView.popupWindow_base.dismiss();
       }
       else
           Toast.makeText(this, "금액이 부족합니다.", Toast.LENGTH_SHORT).show();

    }

    protected void onPause(){
        super.onPause();
        Data.pause = true;
    }
    protected void onResume(){
        super.onResume();
        Data.pause = false;
    }
    protected void onStop() {
        super.onStop();
        Log.i("MainActivity", "액티비티 정지");
        Data.pause = true;
    }
    protected void onRestart(){
        super.onRestart();
        Log.i("MainActivity", "액티비티 재시작");
        Data.pause = false;
    }
    protected void onDestroy(){
        super.onDestroy();
        Log.i("MainActivity", "액티비티 종료");
    }

    public void onBackPressed(){
            endDialog = new EndDialog(this);
            endDialog.setCancelable(false);
            endDialog.show();
    }

    private void reset(){
        Data.playerMoney = 50;
        Data.killedCount = 0;
        Data.pause = false;
        Data.playerHP = 5;
        GameManager.towerArrayList.clear();
        GameManager.monsterArrayList.clear();
        GameManager.projectileArrayList.clear();
        GameManager.checkPointList.clear();
    }

    protected void onNewIntent(Intent intent){
        super.onNewIntent(intent);
        reset();
        recreate();
    }
}
