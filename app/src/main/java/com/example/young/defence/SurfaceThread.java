package com.example.young.defence;

import android.content.Context;
import android.view.SurfaceHolder;

/**
 * Created by YONG on 2018-06-09.
 */

public class SurfaceThread extends Thread {
    SurfaceHolder mholder;
    public SurfaceThread(Context context, SurfaceHolder holder){
        mholder = holder;
    }
    public void run(){

    }
}
