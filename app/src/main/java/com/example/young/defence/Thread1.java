package com.example.young.defence;

import android.graphics.Canvas;

import java.util.ArrayList;

/**
 * Created by young on 2018-04-14.
 * 주기적으로 실행해야 할 것들은 여기서 실행함.
 * 이동은 onDraw통해서 할 수 있으니 여기서 처리하지 않고
 * GameView에서 처리함.
 */

public class Thread1 extends Thread{

    ArrayList<Monster> monsterArrayList = new ArrayList<Monster>();
    ArrayList<Tower> towerArrayList = new ArrayList<Tower>();
//    ArrayList<Projectile> projectileList = new ArrayList<Projectile>();
    private int monsterCount = 0;

    public void run(){
        if(monsterArrayList.size() < monsterCount){
            try{
                for(int number = 0; number < monsterCount; number++){
                    spawn(number);
                    sleep(1500);
                }
            } catch(Exception e){
//            뭐넣어야하지...?
            }
        }

        for(int i = monsterArrayList.size(); i > 0 ; i--){
            if(monsterArrayList.get(i).getLive() == false){
                Monster diedMonster = monsterArrayList.remove(i);
                Data.playerMoney += diedMonster.getMoney();
            }
        }

        if(towerArrayList.isEmpty() == false){
            for(int i = 0; i < towerArrayList.size(); i++){
                if(towerArrayList.get(i).isTargeted == false){
                    for(int j = 0; j < monsterArrayList.size(); j++){
                        towerArrayList.get(i).identifyTarget(monsterArrayList.get(j));
                    }
                }
            }
        }


//        여기서 몬스터 리스트 받아다 게임 뷰에서 이미지가 나오게 해주면 됨.
        GameView.monsterArrayList = monsterArrayList;
    }

    /*
    public void addList(Monster monster){
        monsterList.add(monster);
    }
    public void addList(Tower tower){
        towerList.add(tower);
    }
    public void addList(Projectile projectile){
        projectileList.add(projectile);
    }*/

    private void spawn(int number){
        for(int i = 0; i < monsterArrayList.size(); i++){
            if(monsterArrayList.get(i).getNumber() == number){
                monsterArrayList.get(i).activate();
                break;
            }
        }
    }
    public void setMonsterCount(int count){
        monsterCount = count;
    }

    public void createTower(Tower tower){
        towerArrayList.add(tower);
    }
}
