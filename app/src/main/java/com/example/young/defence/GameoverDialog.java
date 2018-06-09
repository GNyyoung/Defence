package com.example.young.defence;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by young on 2018-06-09.
 */

public class GameoverDialog extends Dialog{

    Button buttonExit, buttonRestart;
    TextView monsterCount, endText;

    public GameoverDialog(Context context){
        super(context);
    }

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_end);

        buttonExit = findViewById(R.id.exit);
        buttonRestart = findViewById(R.id.restart);
        monsterCount = findViewById(R.id.monsterCount);
        endText = findViewById(R.id.endText);

        monsterCount.setText("잡은 수 : " + Data.killedCount);
        endText.setText("패배하셨습니다!");
        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });
        buttonRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                Data.destroyActivity = true;
                getContext().startActivity(intent);
                dismiss();
            }
        });
    }


}
