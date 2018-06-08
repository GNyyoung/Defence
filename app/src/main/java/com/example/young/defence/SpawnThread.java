package com.example.young.defence;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by young on 2018-05-22.
 */

public class SpawnThread extends Thread{

    private ArrayList<Monster> monsterArrayList = GameManager.monsterArrayList;
    public int monsterCount = 0;
    public int spawnCount = 0;
    public boolean isRun = true;
    public boolean run = true;
    public boolean pause = false;
    private boolean finishSpawn = false;
    private Context context;
    boolean stop = false;

    public void run(){
        try{
            sleep(3000);
        }catch (InterruptedException e){}
        while(run){
            if(stop == false){
                isRun = true;
            }
            while(isRun){
                if(stop == true){
                    isRun = false;
                    break;
                }
                spawn(spawnCount);
                try {
                    sleep(1000);
                }catch (InterruptedException e){}
                spawnCount++;
                if(spawnCount == monsterCount) {
                    run = false;
                    finishSpawn = true;
                    break;
                }
            }
            if(finishSpawn){
                Log.i("SpawnThread", "FinishSpawn");
                return;
            }
        }
    }

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
    public boolean checkFinish(){
        return  finishSpawn;
    }
}
