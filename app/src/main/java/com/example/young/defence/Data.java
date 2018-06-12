package com.example.young.defence;


/**
 * Created by young on 2018-05-03.
 * 각종 데이터들(좌표, 스탯)을 여기에 입력해놓고 가져가는 형식으로 한다.
 * 스테이지가 0이 아니라 1부터 시작하니 그걸 감안해 작성 바람.(몬스터 관련)
 */

public class Data {

    final static float[] checkPointposX = {-50, 370, 370, 820, 820, 1400, 1400, 2100
    };
    final static float[] checkPointposY = {590, 590, 275, 275, 700, 700, 480, 480
    };

    public static int stage = 0;
    final static int maxStage = 2;
    public static boolean startStage = false;

    final static float[] towerPosX = {550, 1100, 1700};
    final static float[] towerPosY = {400, 800, 650};
//    스테이지 별 소환 몬스터 수와 능력치
    final static int[] monster1Count = {0,5,7};
    final static int[] monster1HP = {0,2,4};
    final static int monster1Money = 4;
    final static float monster1Speed = 10;

    final static int[] monster2Count = {0,0,3};
    final static int[] monster2HP = {0,4,6};
    final static int monster2Money = 10;
    final static float monster2Speed = 6;

    final static int towerCount = 3;

    public static int playerMoney = 50;
    public static int playerHP = 5;

    public static boolean pause = false;
    public static int killedCount = 0;
    public static boolean destroyActivity = false;
    public static int delay = 30;

    public static float deviceDpi;
    public static int deviceWidth;
    public static int deviceHeight;
    public static int widthPixel;
    public static int heightPixel;

}
