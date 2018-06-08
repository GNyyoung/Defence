package com.example.young.defence;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;


/**
 * Created by young on 2018-06-07.
 */

public class StartActivity extends AppCompatActivity {

    private ImageButton game_start, exit;
    private ImageView background, title;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        background = findViewById(R.id.background);
        title = findViewById(R.id.title);
        game_start = findViewById(R.id.game_start);
        exit = findViewById(R.id.exit);
        setContentView(R.layout.activity_start);
    }

    public void onClickStart(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void onClickExit(View view){
        finish();
    }

}
