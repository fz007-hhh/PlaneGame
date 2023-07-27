package Plane01.task;

import Plane01.GamePanel;
import Plane01.pojo.Enemy;
import Plane01.pojo.EpFire;

import java.io.IOException;
import java.util.List;
import java.util.TimerTask;

public class Stage3task extends TimerTask {
    private List<Enemy> enemies;
    private StageBulider stageBulider;
    private Boss3 boss3;

    public Stage3task(List<Enemy> enemies1) throws IOException {
        this.enemies = enemies1;
        stageBulider = new StageBulider(enemies1);
        boss3=new Boss3(enemies1,true );
    }

    @Override
    public void run() {
        try {
            if (!GamePanel.gameover) {
//                stageBulider.easyBoss(3, 6, false);
//                stageBulider.easyBoss(3, 6, true);
//                stageBulider.commonBoss_Top(5, 2, 9, Enemy.TopRotateR, EpFire.Aim);
//                stageBulider.commonBoss_Top(5, 2, 9, Enemy.TopRotateL, EpFire.Aim);
//                stageBulider.commonBoss_Center(5, 4, 6, Enemy.CenterRotateR, EpFire.Aim);
//                stageBulider.commonBoss_Center(5, 4, 6, Enemy.CenterRotateL, EpFire.Aim);
//                stageBulider.juniorBoss(1, 12, 3, 2,8,4);
//
//                stageBulider.easyBoss(3, 6, true);
//                stageBulider.easyBoss(3, 6, false);
//                stageBulider.commonBoss_Top(5, 2, 9, Enemy.TopRotateL, EpFire.Straight);
//                stageBulider.commonBoss_Top(5, 2, 9, Enemy.TopRotateR, EpFire.Straight);
//                stageBulider.commonBoss_Center(5, 4, 6, Enemy.CenterRotateL, EpFire.Straight);
//                stageBulider.commonBoss_Center(5, 4, 6, Enemy.CenterRotateR, EpFire.Straight);
//
//                stageBulider.betterBoss(13, 2, 13);
//                stageBulider.juniorBoss(4, 12, 3, 2,8,4);
//                stageBulider.giveBonus();
//                stageBulider.easyBoss(7, 6, true);
//                stageBulider.easyBoss(7, 6, false);
//                stageBulider.juniorBoss(2, 12, 3, 2,8,4);
//                stageBulider.commonBoss_Center(7, 3, 6, Enemy.CenterRotateL, EpFire.Aim);
//                stageBulider.commonBoss_Center(7, 3, 6, Enemy.CenterRotateR, EpFire.Aim);
//
//                stageBulider.juniorBoss(3, 12, 3,2,8,4);
//                stageBulider.commonBoss_Top(5, 3, 9, Enemy.TopRotateR, EpFire.Aim);
//                stageBulider.commonBoss_Top(5, 3, 9, Enemy.TopRotateL, EpFire.Aim);
//                stageBulider.betterBoss(13, 2, 2,13,9);
//
//                stageBulider.easyBoss(7, 6, true);
//                stageBulider.easyBoss(7, 6, false);
//                stageBulider.commonBoss_Center(5, 3, 6, Enemy.CenterRotateL, EpFire.Aim);
//                stageBulider.commonBoss_Center(5, 3, 6, Enemy.CenterRotateR, EpFire.Aim);
//                stageBulider.bombStruck(1,2,3,4,5,6,7,8);
//                stageBulider.bombStruck(0,2,3,4,5,6,7,9);
//                stageBulider.bombStruck(0,1,3,4,5,6,8,9);
//                stageBulider.bombStruck(0,1,2,4,5,7,8,9);
//                stageBulider.bombStruck(0,1,2,3,6,7,8,9);
                stageBulider.giveBonus();
                new Thread(boss3).start();
            }
        } catch (Exception e) {
            e = new Exception("第3关终止!");
            e.printStackTrace();
        }
    }
}


