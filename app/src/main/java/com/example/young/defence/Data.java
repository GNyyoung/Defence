package com.example.young.defence;

/**
 * Created by young on 2018-05-03.
 * 각종 데이터들(좌표, 스탯)을 여기에 입력해놓고 가져가는 형식으로 한다.
 */

public class Data {
    final static float[] checkPointposX = {1000, 100
    };
    final static float[] checkPointposY = {100, 500
    };

    final static int maxStage = 2;

//    스테이지 별 소환 몬스터 수
    final static int[] monster1Count = {0,5,7};
//    스테이지 별 몬스터 능력치
    final static int[] monster1HP = {1,2,3};
    final static int[] monster1Money = {2,4,5};
    final static float[] monster1Speed = {10,10,10};
    final static int[] monster2Count = {5};

    public static int playerMoney = 0;
    public static int playerHP = 5;

}
