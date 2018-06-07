package com.example.young.defence;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by young on 2018-05-22.
 */

public class SpawnThread extends Thread{

    private ArrayList<Monster> monsterArrayList = GameManager.monsterArrayList;
    private int monsterCount = 0;
    private boolean isRun = true;
    private boolean finishSpawn = false;
    private Context context;

    public void run(){
        Log.i("SpawnThread", "start spawn");
        try{
            sleep(3000);
            for(int number = 0; number < monsterCount; number++){
                Log.i("SpawnThread", "monster spawn");
                spawn(number);
                sleep(1000);
            }
            Log.i("SpawnThread", "finish spawn");
            finishSpawn = true;
        } catch(InterruptedException e){}

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
