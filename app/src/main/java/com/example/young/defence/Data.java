package com.example.young.defence;

import android.content.Context;
import android.content.res.Resources;

/**
 * Created by young on 2018-05-03.
 * 각종 데이터들(좌표, 스탯)을 여기에 입력해놓고 가져가는 형식으로 한다.
 */

public class Data {
//    Context context;
//    GameView gameView;
//    Resources r = context.getResources();
//    float density = context.getResources().getDisplayMetrics().density;
//    int widthDp = (int)(gameView.getWidth()/density);
//    int heightDp = (int)(gameView.getHeight()/density);

    final static float[] checkPointposX = {90, 370, 370, 820, 820, 1400, 1400, 2080
    };
    final static float[] checkPointposY = {590, 590, 275, 275, 700, 700, 480, 480
    };

    final static int maxStage = 2;

    final static float[] towerPosX = {550, 1100, 1700};
    final static float[] towerPosY = {400, 800, 650};
//    스테이지 별 소환 몬스터 수
    final static int[] monster1Count = {0,5,7};
//    스테이지 별 몬스터 능력치
    final static int[] monster1HP = {1,2,3};
    final static int[] monster1Money = {2,4,5};
    final static float[] monster1Speed = {10,10,10};
    final static int[] monster2Count = {5};
    final static int towerCount = 3;

    public static int playerMoney = 50;
    public static int playerHP = 5;

    public static boolean pause = false;

}
