package com.example.young.defence;

import android.content.Context;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by YONG on 2018-06-09.
 */

public class SurfaceGameView extends SurfaceView implements SurfaceHolder.Callback{
    SurfaceThread surfaceThread;
    public SurfaceGameView(Context context){
        super(context);
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        surfaceThread = new SurfaceThread(context, holder);
    }
    public void surfaceCreated(SurfaceHolder holder){

    }
    public void surfaceDestroyed(SurfaceHolder holder){

    }
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){

    }
}
