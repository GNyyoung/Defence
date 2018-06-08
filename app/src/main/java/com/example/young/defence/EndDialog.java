package com.example.young.defence;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;

/**
 * Created by young on 2018-06-08.
 */

public class EndDialog extends Dialog{

    Button buttonYes, buttonNo;

    public EndDialog(Context context){
        super(context);
    }

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_end);

        buttonYes = findViewById(R.id.buttonYes);
        buttonNo = findViewById(R.id.buttonNo);
        buttonYes.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               System.exit(0);
           }
        });
        buttonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    public void onBackPressed(){
        dismiss();
    }
}
