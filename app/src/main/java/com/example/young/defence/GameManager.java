package com.example.young.defence;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Created by young on 2018-05-03.
 * 게임 스테이지 진행, 승패 판정은 여기서.
 */

public class GameManager extends Thread{

    public static ArrayList<CheckPoint> checkPointList = new ArrayList<CheckPoint>();
    public static ArrayList<Monster> monsterArrayList = new ArrayList<Monster>();
    public static ArrayList<Tower> towerArrayList = new ArrayList<Tower>();
    public static ArrayList<Projectile> projectileArrayList = new ArrayList<Projectile>();
    public Context context;

    public boolean isRun = true;

    public GameManager(Context context) {
//        try,catch 하는법 기억 안남... 나중에 수정 바람.
        this.context = context;
        if (Data.checkPointposX.length == Data.checkPointposY.length) {
            for (int i = 0; i < Data.checkPointposX.length; i++) {
                CheckPoint point = new CheckPoint(Data.checkPointposX[i], Data.checkPointposY[i], i);
                checkPointList.add(point);
            }
        }
//        else
//        오류 출력
    }

//    앱 실행 중에 뒤로 갔다 다시 켜면 스레드가 꺼지지 않은 상태에서 두개가 새로 켜져서 스레드가 4개 돌아가는 상황이 생긴다.

    public void run(){
        Log.i("GameManager", "게임매니저 스레드 생성");
        int stage = 0;
        SpawnThread spawnThread = new SpawnThread();
        startTower();
        while(true){
            if(Data.pause == false){
                isRun = true;
            }
            if(isRun == false && spawnThread != null){
                spawnThread.stop = true;
            }
            while(isRun){
                if(Data.pause == true){
                    isRun = false;
                    break;
                }
                if(spawnThread != null && spawnThread.isRun == false){
                    spawnThread.stop = false;
                }

                controlMonster();
                controlProjectile();
                controlTower();

//                플레이어 체력이 0으로 되면 게임오버.
                if(Data.playerHP == 0){
                    Log.i("GameManager", "게임오버");
                    if(spawnThread.isAlive()){
                        spawnThread.interrupt();
                    }
                    if(context.getClass() == MainActivity.class){
                        ((MainActivity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                GameoverDialog gameoverDialog = ((MainActivity) context).gameoverDialog;
                                gameoverDialog.setCancelable(false);
                                gameoverDialog.show();
                            }
                        });
                    }
                    return;
                }

                if(stage == 0 || (spawnThread.checkFinish() == true && monsterArrayList.isEmpty())){
                    Log.i("GameManager", "StageClear");
                    if(spawnThread.isAlive()){
                        spawnThread.interrupt();
                    }

                    if(stage == Data.maxStage){
                        Log.i("GameManager", "게임 승리");
                        if(context.getClass() == MainActivity.class){
                            ((MainActivity)context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    WinDialog winDialog = ((MainActivity) context).winDialog;
                                    winDialog.setCancelable(false);
                                    winDialog.show();
                                }
                            });
                        }
                        return;
                    }
                    else {
                        stage++;
                        addMonster(stage);
                        spawnThread = new SpawnThread();
                        spawnThread.setMonsterCount(Data.monster1Count[stage]);
                        spawnThread.start();
                    }
                }
                try{
                    sleep(Data.delay);
                } catch (InterruptedException e){
                    spawnThread.interrupt();
                }
            }
        }
    }

    private void controlMonster(){
        for(int i = monsterArrayList.size() - 1; i >= 0 ; i--){
            if(monsterArrayList.get(i).getLive() == false){
                Monster deadMonster = monsterArrayList.remove(i);
                Data.playerMoney += deadMonster.getMoney();
            }
            else{
                monsterArrayList.get(i).move();
            }
        }
    }
    private void controlTower(){
        if(towerArrayList.isEmpty() || monsterArrayList.isEmpty())
            return;
        else{
            for(int i = 0; i < towerArrayList.size(); i++){
                towerArrayList.get(i).resetTargetList();
                for(int j = 0; j < monsterArrayList.size(); j++){
                    towerArrayList.get(i).identifyTarget(monsterArrayList.get(j));
                }
                if(towerArrayList.get(i).isTargeted()){
                    towerArrayList.get(i).increaseTimer(Data.delay);
                    towerArrayList.get(i).attack();
                }
            }
        }
    }

    private void controlProjectile(){
        if(projectileArrayList.isEmpty() == false){
//            Log.i("GameManager", "ProjectileCount : " + Integer.toString(projectileArrayList.size()));
            for(int i = projectileArrayList.size() - 1; i >= 0; i--){
                if(projectileArrayList.get(i).getLive() == false){
                    projectileArrayList.remove(i);
                }
                else{
                    projectileArrayList.get(i).followTarget();
                }
            }
        }
    }

//    일정 주기마다 몬스터를 생성하는데, 다 생성이 안된 상태에서 죽은 몬스터가 있다면
//    원래 생성해야 하는 몬스터보다 많이 생성되기 때문에 미리 배정하고 activate시킨다.
    private void addMonster(int stage){
        int monsterCount = Data.monster1Count[stage];
        for(int i = 0; i < monsterCount; i++){
            Monster monster = new Monster(context, stage, i);
            monsterArrayList.add(monster);
        }
    }

    private void startTower(){
        int towerCount = Data.towerCount;
        for(int i=0;i<towerCount;i++){
            Tower tower = new Tower(context,1,400,600,Data.towerPosX[i],Data.towerPosY[i]);
            towerArrayList.add(tower);
        }
    }


}
