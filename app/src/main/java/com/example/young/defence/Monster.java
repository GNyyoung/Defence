package com.example.young.defence;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;

/**
 * Created by young on 2018-04-13.
 * 구현해야할것
 * 1.몬스터가 point로 이동하게 한다.
 *   몬스터와 point와의 각도에 따라 이미지를 다르게 출력(앞, 옆, 뒤)
 * 2.몬스터 초기화(생성자)
 * 3.몬스터 파괴(목적지에 닿았을 때, 공격받아 죽을 때)
 * 4.마지막 포인트에 도달하면 게임매니저에서 해당 몬스터를 제거하고 데미지를 준다.
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
    public CheckPoint point;
//    몬스터 고유 일련번호
//    포탑이 누구를 먼저 때릴지 판단할 때 사용한다.
    private int number;
//    비트맵이 아닌 다른 이미지 형식을 가진다면 수정한다.
//    import한것도 같이 지우기.
    public Bitmap monsterImage;
    public AnimationDrawable animationDrawable;
//    public BitmapDrawable right1, right2, right3, right4;
    private float collisionRadius = 20;
//    몬스터가 생존했는지 체크. 죽었을 경우 false로 설정해서 arrayList에서 제거한다.
    private boolean isLived = true;
//    몬스터가 활성화됐는지 확인. 활성화되어야 움직인다.
    private boolean isActivated = false;
    public int state=1;
    public Context context;
    public float hypotenuse;
    public Bitmap[] monstersRight = new Bitmap[4];
    public Bitmap[] monstersBack = new Bitmap[4];
    public Bitmap[] monstersFront = new Bitmap[4];

//    지금은 인자를 받아오는 걸로 해놨지만 스테이지만 받아오고
//    values에 능력치 파일을 하나 만들어서 거기서 스테이지에 맞는 데이터를 가져올 수 있게 만들자.
    public Monster(Context context, int stage, int number, int version){
//        몬스터 이미지 추가
        this.context = context;
        if(version == 1){
//            monsterImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.right1);
            monstersRight[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.right1);
            monstersRight[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.right2);
            monstersRight[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.right3);
            monstersRight[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.right4);
            monstersBack[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.back1);
            monstersBack[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.back2);
            monstersBack[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.back3);
            monstersBack[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.back4);
            monstersFront[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.front1);
            monstersFront[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.front2);
            monstersFront[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.front3);
            monstersFront[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.front4);
            this.hp = Data.monster1HP[stage];
            this.money = Data.monster1Money;
            this.moveSpeed = Data.monster1Speed;
        }

        else if(version == 2) {
//            monsterImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.right2_1);
            monstersRight[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.right2_1);
            monstersRight[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.right2_2);
            monstersRight[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.right2_3);
            monstersRight[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.right2_4);
            monstersBack[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.back2_1);
            monstersBack[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.back2_2);
            monstersBack[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.back2_3);
            monstersBack[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.back2_4);
            monstersFront[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.front2_1);
            monstersFront[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.front2_2);
            monstersFront[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.front2_3);
            monstersFront[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.front2_4);
            this.hp = Data.monster2HP[stage];
            this.money = Data.monster2Money;
            this.moveSpeed = Data.monster2Speed;
        }


//        BitmapDrawable right1 = (BitmapDrawable)context.getResources().getDrawable(R.drawable.left1);
//        BitmapDrawable right2 = (BitmapDrawable)context.getResources().getDrawable(R.drawable.left2);
//        BitmapDrawable right3 = (BitmapDrawable)context.getResources().getDrawable(R.drawable.front3);
//        BitmapDrawable right4 = (BitmapDrawable)context.getResources().getDrawable(R.drawable.back4);
//        animationDrawable = new AnimationDrawable();
//        animationDrawable.addFrame(right1,500);
//        animationDrawable.addFrame(right2,500);
//        animationDrawable.addFrame(right3,500);
//        animationDrawable.addFrame(right4,500);
//        animationDrawable.setOneShot(false);
        this.number = number;
        this.point = GameManager.checkPointList.get(0);
        posX = point.getPosX();
        posY = point.getPosY();
        Log.i("Monster", "Monster: posX : " + Float.toString(posX));
        this.point = GameManager.checkPointList.get(1);

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
        if(isActivated == true){
            float pointX = point.getPosX();
            float pointY = point.getPosY();
            final float distanceX = pointX - posX;
//        Log.i("Monster", "distanceX: " + Float.toString(distanceX));
            final float distanceY = pointY - posY;
//        빗변
            hypotenuse = (float)Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));
//        Log.i("Monster", "hypotenuse: " + Float.toString(hypotenuse));
//        x축, y축 이동속도
            float speedX, speedY;

//        사잇값들이 생략되어 있으니 나중에 <= 또는 >=을 조건문에서 충돌나지 않게 넣어줄것.

//            if(distanceX > 0 && distanceY==0){
////            오른쪽 이미지 출력
//                if(state==1) {
//                    monsterImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.right1);
//                    state++;
//                }
//                else if(state==2) {
//                    monsterImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.right2);
//                    state++;
//                }
//                else if(state==3) {
//                    monsterImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.right3);
//                    state++;
//                }
//                else if(state==4) {
//                    monsterImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.right4);
//                    state=1;
//                }
//            }
//            else if(distanceX == 0 && distanceY < 0){
////            위쪽 이미지 출력
//                if(state==1) {
//                    monsterImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.back1);
//                    state++;
//                }
//                else if(state==2) {
//                    monsterImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.back2);
//                    state++;
//                }
//                else if(state==3) {
//                    monsterImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.back3);
//                    state++;
//                }
//                else if(state==4) {
//                    monsterImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.back4);
//                    state=1;
//                }
//            }
//            else if(distanceX == 0 && distanceY > 0){
////            아래쪽 이미지 출력
//                if(state==1) {
//                    monsterImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.front1);
//                    state++;
//                }
//                else if(state==2) {
//                    monsterImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.front2);
//                    state++;
//                }
//                else if(state==3) {
//                    monsterImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.front3);
//                    state++;
//                }
//                else if(state==4) {
//                    monsterImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.front4);
//                    state=1;
//                }
//            }
//            else if(distanceX < 0 && distanceY==0){
////            아래쪽 이미지 출력 오른쪽
//                if(state==1) {
//                    monsterImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.left1);
//                    state++;
//                }
//                else if(state==2) {
//                    monsterImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.left2);
//                    state++;
//                }
//                else if(state==3) {
//                    monsterImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.left3);
//                    state++;
//                }
//                else if(state==4) {
//                    monsterImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.left4);
//                    state=1;
//                }
//            }



            speedX = moveSpeed * distanceX / hypotenuse;
            speedY = moveSpeed * distanceY / hypotenuse;
            posX += speedX;
            posY += speedY;

//        몬스터와 포인터의 거리가 이동속도보다 짧으면 다음 포인트를 입력받는다.
            if(hypotenuse <= moveSpeed){
                if(point.getNumber() == GameManager.checkPointList.size() - 1){
                    Data.playerHP--;
                    GameManager.monsterArrayList.remove(0);
                }
                else{
                    point = GameManager.checkPointList.get(point.getNumber() + 1);
//                    Log.i("Monster", "point number = " + Integer.toString(point.getNumber()));
                }
            }
        }
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

    public void damaged(int damage){
        hp -= damage;
        if(hp <= 0){
            isLived = false;
            Data.killedCount++;
        }
    }

    public void activate(){
        isActivated = true;
    }
    public boolean getLive(){
        return isLived;
    }
    public int getMoney(){
        return money;
    }

    public void setPoint(CheckPoint checkPoint){
        point = checkPoint;
    }
}
