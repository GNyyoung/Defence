package com.example.young.defence;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupWindow;

public class MainActivity extends AppCompatActivity {
    GameView gameView;
    GameManager gameManager;
    private float deviceDpi;
    private View decorView;
    private int uiOption;
    EndDialog endDialog = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameManager = new GameManager(this);
        gameManager.start();

//        surfaceGameView = new SurfaceGameView(this);
        setContentView(R.layout.activity_main);
        gameView = findViewById(R.id.gameview);
        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        deviceDpi = outMetrics.densityDpi;
        gameView.setDp(deviceDpi);
        decorView = getWindow().getDecorView();
        setFullScreen();
        Log.i("MainActivity", "DPI : " + Float.toString(deviceDpi));
        decorView.setOnSystemUiVisibilityChangeListener(
            new View.OnSystemUiVisibilityChangeListener() {
                @Override
                public void onSystemUiVisibilityChange(int visibility) {
                    setFullScreen();
                }
            });
    }

//    전체화면으로 만드는 메소드
//    내비게이션바, 상태바 제거하고 맨 위쪽 또는 아래쪽 드래그시 잠시 나온 후 사라진다.
//    액션바는 제거하지 못함. styles.xml에서 따로 제거
    private void setFullScreen(){
        uiOption = getWindow().getDecorView().getSystemUiVisibility();
        uiOption |= gameView.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        uiOption |= gameView.SYSTEM_UI_FLAG_FULLSCREEN;
        uiOption |= gameView.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        uiOption |= gameView.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
        uiOption |= gameView.SYSTEM_UI_FLAG_LAYOUT_STABLE;

        decorView.setSystemUiVisibility( uiOption );
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        if( hasFocus ) {
            decorView.setSystemUiVisibility( uiOption );
        }
    }

   public void onClick_Base(View view){
       setFullScreen();
       Tower tower = GameManager.towerArrayList.get(gameView.getClickedTower());
       tower.towerImage = BitmapFactory.decodeResource(getResources(), R.drawable.turret_base);
       GameManager.towerArrayList.get(gameView.getClickedTower()).towerState=1;
       GameManager.towerArrayList.get(gameView.getClickedTower()).activate();
       gameView.popupWindow_ground.dismiss();
       GameManager.towerArrayList.get(gameView.getClickedTower()).activate();
   }
   public void onClick_Evo1(View view){
       setFullScreen();
       Tower tower = GameManager.towerArrayList.get(gameView.getClickedTower());
       tower.towerImage = BitmapFactory.decodeResource(getResources(), R.drawable.turret_e1);
       GameManager.towerArrayList.get(gameView.getClickedTower()).towerState=2;
       gameView.popupWindow_base.dismiss();

   }
    public void onClick_Evo2(View view){
        setFullScreen();
        Tower tower = GameManager.towerArrayList.get(gameView.getClickedTower());
        tower.towerImage = BitmapFactory.decodeResource(getResources(), R.drawable.turret_e2);
        GameManager.towerArrayList.get(gameView.getClickedTower()).towerState=3;
        gameView.popupWindow_base.dismiss();
    }

    protected void onPause(){
        super.onPause();
        gameManager.isRun = false;
    }
    protected void onResume(){
        super.onResume();
        gameManager.isRun = true;
    }
    @Override
    protected void onStop() {
        super.onStop();
        gameManager.isRun = false;
    }
    protected void onRestart(){
        super.onRestart();
        gameManager.isRun = true;
    }
    protected void onDestroy(){
        super.onDestroy();
    }

    public void onBackPressed(){
        /*AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("종료하시겠습니까?");
        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                System.exit(0);
            }
        });
        AlertDialog exitDialog = builder.create();
        exitDialog.show();*/
            endDialog = new EndDialog(this);
            endDialog.setCancelable(false);
            endDialog.show();
    }
}
