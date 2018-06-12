package com.example.young.defence;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;


/**
 * Created by young on 2018-06-07.
 */

public class StartActivity extends AppCompatActivity {

    private View decorView;
    private int uiOption;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        decorView = getWindow().getDecorView();
        setContentView(R.layout.activity_start);
        setFullScreen();
    }

    public void onClickStart(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    public void onClickExit(View view){
        finish();
    }

    private void setFullScreen(){
        uiOption = getWindow().getDecorView().getSystemUiVisibility();
        uiOption |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        uiOption |= View.SYSTEM_UI_FLAG_FULLSCREEN;
        uiOption |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        uiOption |= View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
        uiOption |= View.SYSTEM_UI_FLAG_LAYOUT_STABLE;

        decorView.setSystemUiVisibility( uiOption );
    }

}
