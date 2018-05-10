package com.example.young.defence;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreen();
        GameView v = new GameView(this);
        setContentView(v);
// setContentView(R.layout.activity_main);

        GameManager gameManager = new GameManager();
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


}
