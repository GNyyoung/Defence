package com.example.young.defence;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by young on 2018-05-22.
 */

public class SpawnThread extends Thread{

    private ArrayList<Monster> monster1ArrayList = null;
    private ArrayList<Monster> monster2ArrayList = null;
    public int monsterCount1 = 0;
    public int monsterCount2 = 0;
    public int spawnCount1 = 0;
    public int spawnCount2 = 0;
    public boolean isRun = true;
    public boolean run = true;
    public boolean pause = false;
    private boolean finishSpawn = false;
    private boolean finishSpawn1 = false;
    private boolean finishSpawn2 = false;
    private Context context;
    boolean stop = false;

    public void run(){
        int spawnTimer1 = 0;
        int spawnTimer2 = 0;

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
                spawnTimer1 += Data.delay;
                spawnTimer2 += Data.delay;

                if(spawnCount1 < monsterCount1){
                    if(spawnTimer1 / 1000 > 0){
                        spawn(1, spawnCount1);
                        spawnCount1++;
                        spawnTimer1 = 0;
                    }
                }
                else{
                    finishSpawn1 = true;
                }

                if(spawnCount2 < monsterCount2){
                    if(spawnTimer2 / 1500 > 0){
                        spawn(2, spawnCount2);
                        spawnCount2++;
                        spawnTimer2 = 0;
                    }
                }
                else{
                    finishSpawn2 = true;
                }

                if(finishSpawn1 && finishSpawn2) {
                    run = false;
                    finishSpawn = true;
                    break;
                }
                try{
                    sleep(Data.delay);
                }catch (InterruptedException e){}
            }
            if(finishSpawn){
                Log.i("SpawnThread", "FinishSpawn");
                return;
            }
        }
    }

    private void spawn(int version, int number){
        switch (version){
            case 1:
                for(int i = 0; i < monster1ArrayList.size(); i++){
                    if(monster1ArrayList.get(i).getNumber() == number){
                        monster1ArrayList.get(i).activate();
                        break;
                    }
                }
                break;
            case 2:
                for(int i = 0; i < monster2ArrayList.size(); i++){
                    if(monster2ArrayList.get(i).getNumber() == number){
                        monster2ArrayList.get(i).activate();
                        break;
                    }
                }
                break;
        }

    }

    public void setMonsterCount(int stage){
        monsterCount1 = Data.monster1Count[stage];
        monsterCount2 = Data.monster2Count[stage];
    }
    public boolean checkFinish(){
        return  finishSpawn;
    }
    public void setMonsterList(){
        monster1ArrayList = GameManager.monster1ArrayList;
        monster2ArrayList = GameManager.monster2ArrayList;
    }
}
