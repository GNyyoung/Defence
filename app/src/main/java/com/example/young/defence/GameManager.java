package com.example.young.defence;

import java.util.ArrayList;

/**
 * Created by young on 2018-05-03.
 * 게임 스테이지 진행, 승패 판정은 여기서.
 */

public class GameManager {

    final public static ArrayList<CheckPoint> checkPointList = new ArrayList<CheckPoint>();



    public GameManager(){
//        try,catch 하는법 기억 안남... 나중에 수정 바람.
        if(Data.checkPointposX.length == Data.checkPointposY.length){
            for(int i = 0; i < Data.checkPointposX.length; i++){
                CheckPoint point = new CheckPoint(Data.checkPointposX[i], Data.checkPointposY[i], i);
                checkPointList.add(point);
            }
        }
//        else
//        오류 출력

        gameStart();
    }

    private void gameStart(){
        Thread1 thread = new Thread1();
        int stage = 1;
        startStage(thread, stage);
    }

    private void startStage(Thread1 thread, int stage){
        ArrayList<Monster> monsterArrayList = new ArrayList<>();
        int monsterCount = Data.monter1Count[stage];
        for(int i = 0; i < monsterCount; i++){
            Monster monster = new Monster(stage, i);
            monsterArrayList.add(monster);
            thread.monsterArrayList = monsterArrayList;
            thread.setMonsterCount(monsterCount);
        }

    }

}
