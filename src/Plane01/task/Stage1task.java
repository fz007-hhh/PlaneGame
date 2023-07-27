package Plane01.task;

import Plane01.GamePanel;
import Plane01.pojo.Enemy;
import Plane01.pojo.EpFire;

import java.io.IOException;
import java.util.List;
import java.util.TimerTask;

public class Stage1task extends TimerTask {
    private List<Enemy>enemies;
    private StageBulider stageBulider;
    private Boss1 boss1;

    public Stage1task(List<Enemy> enemies1) throws IOException {
        this.enemies=enemies1;
        boss1=new Boss1(enemies1,true);
        stageBulider=new StageBulider(enemies1);
    }

    @Override
    public void run() {
        try {
            synchronized (Stage1task.class){
            if (!GamePanel.gameover) {
//                stageBulider.easyBoss(2,5,true);
//                stageBulider.easyBoss(2,5,false);
//                stageBulider.commonBoss_Top(5,2,7,Enemy.TopRotateL, EpFire.Straight);
//                stageBulider.commonBoss_Top(5,2,7,Enemy.TopRotateR,EpFire.Straight);
//                stageBulider.commonBoss_Center(5,3,3,Enemy.CenterRotateL,EpFire.Straight);
//                stageBulider.commonBoss_Center(5,3,4,Enemy.CenterRotateR,EpFire.Straight);
//                stageBulider.juniorBoss(1,12, 3);
//                stageBulider.easyBoss(2,5,true);
//                stageBulider.easyBoss(2,5,false);
//                stageBulider.commonBoss_Top(5,1,8,Enemy.TopRotateL,EpFire.Aim);
//                stageBulider.commonBoss_Top(5,1,8,Enemy.TopRotateR,EpFire.Aim);
//                stageBulider.commonBoss_Center(5,3,3,Enemy.CenterRotateL,EpFire.Aim);
//                stageBulider.commonBoss_Center(5,3,4,Enemy.CenterRotateR,EpFire.Aim);
//                stageBulider.juniorBoss(4,12, 3);
//                stageBulider.commonBoss_Top(5,1,8,Enemy.TopRotateL,EpFire.Aim);
//                stageBulider.commonBoss_Top(5,1,8,Enemy.TopRotateR,EpFire.Aim);
//                stageBulider.juniorBoss(2,12, 3);
//                stageBulider.commonBoss_Center(5,3,3,Enemy.CenterRotateL,EpFire.Aim);
//                stageBulider.commonBoss_Center(5,3,4,Enemy.CenterRotateR,EpFire.Aim);
//                stageBulider.juniorBoss(3,12, 3);
//                stageBulider.betterBoss(13,2,2,9,2);
                stageBulider.giveBonus();
                new Thread(boss1).start();
            }}

        } catch (Exception e) {
            e=new Exception("第1关终止！");
            e.printStackTrace();
        }
    }

}
