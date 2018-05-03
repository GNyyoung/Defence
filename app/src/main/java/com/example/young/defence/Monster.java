package com.example.young.defence;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by young on 2018-04-13.
 * 구현해야할것
 * 1.몬스터가 point로 이동하게 한다.
 *   몬스터와 point와의 각도에 따라 이미지를 다르게 출력(앞, 옆, 뒤)
 * 2.몬스터 초기화(생성자)
 * 3.몬스터 파괴(목적지에 닿았을 때, 공격받아 죽을 때)
 *
 * override 고려 안하고 작성중
 * 분명 사용할테니 나중에 코드 짤 때 그에 맞춰 수정하도록 한다.
 */

public class Monster {

//    몬스터 체력
    private int hp;
//    죽을 때 플레이어에게 주는 돈
    private int money;
//    몬스터 이동속도
//    기기마다 픽셀 수가 다를테니 화면 최대 크기에서 몇분할 한것을 쓰도록 한다.
    private float moveSpeed;
//    몬스터 현재 좌표
//    포탑에서 발사한 투사체가 향할 좌표를 알려준다.
    private float posX, posY;
//    몬스터의 이동 목표
//    point가 가진 좌표로 이동 후, 다음 point를 입력받는다.
    private CheckPoint point;
//    몬스터 고유 일련번호
//    포탑이 누구를 먼저 때릴지 판단할 때 사용한다.
    private int number;
//    비트맵이 아닌 다른 이미지 형식을 가진다면 수정한다.
//    import한것도 같이 지우기.
    public Bitmap monsterImage;
    private float collisionRadius = 1;

//    지금은 인자를 받아오는 걸로 해놨지만 스테이지만 받아오고
//    values에 능력치 파일을 하나 만들어서 거기서 스테이지에 맞는 데이터를 가져올 수 있게 만들자.
    public Monster(int hp, int money, float moveSpeed, int number){
//        몬스터 이미지 추가
//        monster = BitmapFactory.decodeResource(res, id);
        this.hp = hp;
        this.money = money;
        this.moveSpeed = moveSpeed;
        this.number = number;

//        point는 제일 첫번째 이동해야하는 point로 초기화
//        position은 몬스터가 생성될 위치로 한다.

    }


    public float getPosX(){
        return posX;
    }
    public float getPosY(){
        return posY;
    }
    public int getNumber(){
        return number;
    }

    public void move(){
//        여기서 처리할 것 : point로 이동, 몬스터와 point 각도에 따라 이미지 변화
//        point 좌표 불러와 position값 변화시키면서 이동
//        (y'-y)/(x'-x)로 두 점 간의 각도 구한다.
//        (0이면  x축, 무한이면 y축)
//        !!원점이 좌하단에 있다고 가정하고 작성하였음
//        !!원점이 좌상단이라면 코드 수정바람.
        float pointX = point.getPosX();
        float pointY = point.getPosY();
        float distanceX = pointX - posX;
        float distanceY = pointY - posY;
//        빗변
        float hypotenuse = (float)Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));
//        x축, y축 이동속도
        float speedX, speedY;

//        사잇값들이 생략되어 있으니 나중에 <= 또는 >=을 조건문에서 충돌나지 않게 넣어줄것.
        if(distanceX > 0 && distanceX / distanceY > -1 && distanceX / distanceY < 1){
//            오른쪽 이미지 출력
        }
        else if(distanceX / distanceY > -1 && distanceX / distanceY < 1){
//            왼쪽 이미지 출력
        }
        else if(distanceY > 0 && distanceX / distanceY < -1 && distanceX / distanceY > 1){
//            위쪽 이미지 출력
        }
        else{
//            아래쪽 이미지 출력
        }

        speedX = moveSpeed * distanceX / hypotenuse;
        speedY = moveSpeed * distanceY / hypotenuse;
        posX += speedX;
        posY += speedY;

        pointCollisionCheck();
    }

    public void die(){
    }

    public boolean CollisionCheck(float projectilePosX, float projectilePosY){
        if(projectilePosX > posX - collisionRadius
                && projectilePosX < posX + collisionRadius
                && projectilePosY > posY - collisionRadius
                && projectilePosY < posY + collisionRadius)
            return true;
        else
            return false;
    }

    private void pointCollisionCheck(){
        if(point.CollisionCheck(posX, posY)){
        }
    }
}
