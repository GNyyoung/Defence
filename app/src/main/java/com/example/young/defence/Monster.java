package com.example.young.defence;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
    private CheckPoint point;
//    몬스터 고유 일련번호
//    포탑이 누구를 먼저 때릴지 판단할 때 사용한다.
    private int number;
//    비트맵이 아닌 다른 이미지 형식을 가진다면 수정한다.
//    import한것도 같이 지우기.
    public Bitmap monsterImage;
    private float collisionRadius = 1;
//    몬스터가 생존했는지 체크. 죽었을 경우 false로 설정해서 arrayList에서 제거한다.
    private boolean isLived = true;
    private boolean isActivated = false;
    public Context context;
//    지금은 인자를 받아오는 걸로 해놨지만 스테이지만 받아오고
//    values에 능력치 파일을 하나 만들어서 거기서 스테이지에 맞는 데이터를 가져올 수 있게 만들자.
    public Monster(Context context, int stage, int number){
//        몬스터 이미지 추가
        this.context = context;
        monsterImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.right1);
        this.hp = Data.monster1HP[stage];
        this.money = Data.monster1Money[stage];
        this.moveSpeed = Data.monster1Speed[stage];
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
            float distanceX = pointX - posX;
//        Log.i("Monster", "distanceX: " + Float.toString(distanceX));
            float distanceY = pointY - posY;
//        빗변
            float hypotenuse = (float)Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));
//        Log.i("Monster", "hypotenuse: " + Float.toString(hypotenuse));
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
//        Log.i("Monster", "move: " + Float.toString(speedX));
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

//    public void die(){
//        isLived = false;
//    }

    public boolean CollisionCheck(float projectilePosX, float projectilePosY){
        if(projectilePosX > posX - collisionRadius
                && projectilePosX < posX + collisionRadius
                && projectilePosY > posY - collisionRadius
                && projectilePosY < posY + collisionRadius)
            return true;
        else
            return false;
    }

/*몬스터가 포인트 근처에 가면 다음 포인트를 넣어주는 건데 이거 없이도 될듯 함.
    private void pointCollisionCheck(){
        if(point.CollisionCheck(posX, posY)){
//            다음 포인트를 입력받는다.

        }
    }
*/
    public void damaged(int damage){
        hp -= damage;
        if(hp <= 0)
//            die();
            isLived = false;
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
