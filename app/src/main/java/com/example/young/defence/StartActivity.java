package com.example.young.defence;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by young on 2018-06-07.
 */

public class StartActivity extends Activity {
    private Button startButton;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        startButton = findViewById(R.id.startButton);

        setContentView(R.layout.activity_start);
    }

    public void onClickStart(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}
