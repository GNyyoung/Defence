package com.example.young.defence;

import android.graphics.Canvas;
import android.os.Debug;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by young on 2018-04-14.
 * 주기적으로 실행해야 할 것들은 여기서 실행함.
 * 이동은 onDraw통해서 할 수 있으니 여기서 처리하지 않고
 * GameView에서 처리함.
 */

public class Thread1 extends Thread{

    private ArrayList<Monster> monsterArrayList = GameManager.monsterArrayList;
    private ArrayList<Tower> towerArrayList = GameManager.towerArrayList;
//    ArrayList<Projectile> projectileList = new ArrayList<Projectile>();
    private int monsterCount = 0;
    private int towerCount = 0;
    private boolean isRun = true;

    public void run(){
        if(monsterArrayList.size() < monsterCount){
//            Log.i("Thread1_SpawnMonster", "start spawn");
            try{
                for(int number = 0; number < monsterCount; number++){
                    spawn(number);
                    sleep(1500);
                }
            } catch(InterruptedException e){
//            뭐넣어야하지...?
            }
        }
        while(isRun){
//            Log.i("Thread1", "Run");
//        스테이지 시작 시 몬스터 생성에 관한 코드


//        죽은 몬스터가 있는지 확인하고 리스트에서 제거
//            Log.i("Thread1_DeadMonster", "start check");
            for(int i = monsterArrayList.size() - 1; i >= 0 ; i--){
                if(monsterArrayList.get(i).getLive() == false){
                    Monster deadMonster = monsterArrayList.remove(i);
                    Data.playerMoney += deadMonster.getMoney();
                }
                else{
                    monsterArrayList.get(i).move();
                }
            }

//        타워가 공격할 수 있는 몬스터가 있는지 확인
            if(towerArrayList.isEmpty() == false){
                for(int i = 0; i < towerArrayList.size(); i++){
                    if(towerArrayList.get(i).isTargeted == false){
                        for(int j = 0; j < monsterArrayList.size(); j++){
                            towerArrayList.get(i).identifyTarget(monsterArrayList.get(j));
                        }
                    }
                }
            }

            GameManager.monsterArrayList = monsterArrayList;
            try{
                    sleep(30);
            } catch(InterruptedException e){}
        }
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

    public void setTowerCount(int count){towerCount = count;}

    public void createTower(Tower tower){
        towerArrayList.add(tower);
    }
}
