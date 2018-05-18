package com.example.young.defence;

import java.util.ArrayList;

/**
 * Created by young on 2018-05-03.
 * 게임 스테이지 진행, 승패 판정은 여기서.
 */

public class GameManager {

    public static ArrayList<CheckPoint> checkPointList = new ArrayList<CheckPoint>();
    public static ArrayList<Monster> monsterArrayList = new ArrayList<Monster>();
    public static ArrayList<Tower> towerArrayList = new ArrayList<Tower>();



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
//        이렇게 짜놓으면 다음 스테이지로 진행할 수가 없으니 내용 수정해야함.
    }

    private void startStage(Thread1 thread, int stage){
        ArrayList<Monster> monsterArrayList = new ArrayList<>();
        int monsterCount = Data.monster1Count[stage];
        for(int i = 0; i < monsterCount; i++){
            Monster monster = new Monster(stage, i);
            monsterArrayList.add(monster);
            thread.setMonsterCount(monsterCount);
        }

        thread.start();
    }

}
