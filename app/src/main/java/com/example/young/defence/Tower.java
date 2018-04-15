package com.example.young.defence;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;

/**
 * Created by young on 2018-04-14.
 * 구현할것
 * 1.포탑 주변 인식범위 생성(포탑 중심으로부터의 반지름. 포탑과 몬스터 사이의 거리)
 * 2.타겟 지정 후 공격. 투사체 발사는 아마 스레드 써야할듯(일정 시간마다 날아가야 하니까)
 */

public class Tower {
//    포탑이 발사하는 투사체 공격력
    private int damage;
//    포탑이 몬스터를 인식하는 범위
    private float radius;
//    투사체를 발사 후 다시 발사하기까지 걸리는 시간
    private float reloadTime;
//    포탑 이미지
    public Bitmap towerImage;
//    투사체 이미지
    public Bitmap projectileImage;
    private float posX, posY;
    private ArrayList<Monster> targetList = new ArrayList<Monster>();
    private Monster target;

    public Tower(int damage, float radius, float reloadTime, float posX, float posY){
//        towerImage = BitmapFactory.decodeResource(res, id);
//        projectileImage = BitmapFactory.decodeResource(res, id);
        this.damage = damage;
        this.reloadTime = reloadTime;
        this.radius = radius;
        this.posX = posX;
        this.posY = posY;
    }

//    몬스터와 포탑 사이의 거리를 체크하고 radius보다 거리가 가까우면 targetList에 추가
    private void identifyTarget(Monster monster){
        float x = monster.getPosX();
        float y = monster.getPosY();
        boolean isExist = false;

//        포탑과 몬스터 사이의 거리
        float distance = (float)Math.sqrt(Math.pow((posX - x), 2) / Math.pow((posY - y), 2));
        if(distance <= radius){
            if(targetList.isEmpty()){
                    targetList.add(monster);
                    designateTarget();
            }
            else{
                for(int i = 0; i < targetList.size(); i++){
                    int number = targetList.get(i).getNumber();
                    if(monster.getNumber() == number)
                        isExist = true;
                    break;
                }
                //false일때 add 한다는게 영 찝찝함.. isNotExist라고 쓸수도 없고;
                //적당한 단어 있으면 변수명좀 고쳐주기 바람.
                if(isExist == false){
                    targetList.add(monster);
                    designateTarget();
                }
            }
        }
    }

//    타겟이 아예 없어서 새로 타겟을 설정하는거랑
//    타겟리스트에 몬스터가 새로 들어왔을 때 얘가 기존에 있던 애보다 번호가 작은지 체크하는거
    private void designateTarget(){
        if(!targetList.isEmpty() && target == null){
            Monster candidate = targetList.get(0);
            if(targetList.size() > 1){
                for(int i = 1; i < targetList.size(); i++){
                    if(targetList.get(i).getNumber() < candidate.getNumber())
                        candidate = targetList.get(i);
                }
            }
            target = candidate;
        }
        else if(!targetList.isEmpty()){
            for(int i = 0; i < targetList.size(); i++){
                if(targetList.get(i).getNumber() < target.getNumber())
                    target = targetList.get(i);
            }
        }
    }
}
