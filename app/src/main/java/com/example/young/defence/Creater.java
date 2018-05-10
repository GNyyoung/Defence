package com.example.young.defence;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by young on 2018-04-17.
 * 각종 오브젝트 생성 및 파괴는 여기서 담당합니다.
 * 여기서 생성한 오브젝트 중 화면에 띄워야할 것은 모아서 GameView에 보내줍니다.
 * ... 어떻게 사용하려고 했는지 기억 안나므로 GameManager만 사용하도록 하자..
 */

public class Creater {

    private ArrayList<CheckPoint> checkPointList = new ArrayList<CheckPoint>();

    public Creater(){
        for(int i = 0; i < Data.checkPointposX.length; i++){
            CheckPoint point = new CheckPoint(Data.checkPointposX[i], Data.checkPointposY[i], i);
            checkPointList.add(point);
        }
    }

}
