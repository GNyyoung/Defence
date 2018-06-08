package com.example.young.defence;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageButton;

import java.util.ArrayList;

/**
 * Created by young on 2018-04-14.
 * 구현할것
 * 1.포탑 주변 인식범위 생성(포탑 중심으로부터의 반지름. 포탑과 몬스터 사이의 거리) / 해결
 * 2.타겟 지정 후 공격. 투사체 발사는 아마 스레드 써야할듯(일정 시간마다 날아가야 하니까) / 타겟 지정
 * 3.타겟이 있을 경우 몬스터에게 투사체 발사 / 해결
 * 4.타겟 몬스터가 죽어도 타겟을 바꾸지 않고 몬스터 죽은 자리에 무한정 공격함. / 해결
 */

public class Tower {
//    포탑이 발사하는 투사체 공격력
    private int damage;
    //    투사체 이동속도
    private float projectileSpeed = 20;
//    포탑이 몬스터를 인식하는 범위
    private float radius;
//    투사체를 발사 후 다시 발사하기까지 걸리는 시간
    private int reloadTime;
    private int timer;
//    포탑 이미지
    public Bitmap towerImage;
//    포탑 좌표
    private float posX, posY;
//    포탑 인식범위 내에 있는 몬스터 목록
    private ArrayList<Monster> targetList = new ArrayList<Monster>();
//    포탑의 공격목표
    public Context context;
    private Monster target;
    private boolean isActivated = false;

    public int towerState; //towerState 0=땅, 1=기본타워, 2=분홍, 3=노랑;

    public Tower(Context context, int damage, float radius, int reloadTime, float posX, float posY){
        this.context = context;
        towerImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.turret_ground);
//        projectileImage = BitmapFactory.decodeResource(res, id);
        this.damage = damage;
        this.reloadTime = reloadTime;
        this.radius = radius;
        this.posX = posX;
        this.posY = posY;
        towerState=0;
    }

//    몬스터와 포탑 사이의 거리를 체크하고 radius보다 거리가 가까우면 targetList에 추가
//    몬스터가 이미 타겟리스트에 포함되어 있다면 추가하지 않음.
    public void identifyTarget(Monster monster){
//        Log.i("Tower", "checkTarget");
        if(isActivated == false)
            return;

        float x = monster.getPosX();
        float y = monster.getPosY();

//        포탑과 몬스터 사이의 거리
        float distance = (float)Math.sqrt(Math.pow((posX - x), 2) + Math.pow((posY - y), 2));
        if(distance <= radius){
            targetList.add(monster);
            Log.i("Tower", "addTarget");
            designateTarget();

        }

    }

//    타겟이 아예 없어서 새로 타겟을 설정하는거랑
//    타겟리스트에 몬스터가 새로 들어왔을 때랑
//    타겟이 죽어서 재설정할 때
//    number가 가장 작은 애를 타겟으로 설정
    private void designateTarget(){
        boolean isGetOutRange = false;

        if(target != null){
            if(target.getLive() == false)
                target = null;
            else{
                for(int i = 0; i < targetList.size(); i++){
                    if(target.getNumber() == targetList.get(i).getNumber()) {
                        isGetOutRange = true;
                        break;
                    }
                }
                if(isGetOutRange == false)
                    target = null;
            }
        }

        if(target != null || targetList.isEmpty() == true)
            return;
        else{
            Monster candidate = targetList.get(0);
            if(targetList.size() > 1){
                for(int i = 1; i < targetList.size(); i++){
                    if(targetList.get(i).getNumber() < candidate.getNumber()){
                        candidate = targetList.get(i);
                    }
                }
                target = candidate;
            }
        }
    }

//    타겟을 공격.
    public void attack(){
        if(isActivated == true){
            if(timer >= reloadTime){
                Projectile projectile = new Projectile(damage, projectileSpeed, posX, posY, target);
                GameManager.projectileArrayList.add(projectile);
                timer = 0;
            }
        }
    }

    public void activate(){
        isActivated = true;
        Log.i("Tower", "activate");
    }

    public boolean isTargeted(){
        if(target != null)
            return true;
        else
            return false;
    }

    public void increaseTimer(int time){
        timer += time;
    }

    public void resetTargetList(){
        targetList.clear();
    }
}
