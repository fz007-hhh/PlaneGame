package Plane01.task;

import Plane01.GamePanel;
import Plane01.pojo.Enemy;
import Plane01.pojo.EpFire;

import java.io.IOException;
import java.util.List;
import java.util.TimerTask;

public class Stage2task extends TimerTask {
    private List<Enemy> enemies;
    private StageBulider stageBulider;
    private Boss2 boss2;

    public Stage2task(List<Enemy> enemies1) throws IOException {
        this.enemies=enemies1;
        stageBulider=new StageBulider(enemies1);
        boss2=new Boss2(enemies1,true);
    }

    @Override
    public void run() {
        try {
            if(!GamePanel.gameover) {
//                stageBulider.easyBoss(1, 5, false);
//                stageBulider.easyBoss(1, 5, true);
//
//                stageBulider.commonBoss_Top(5, 2, 8, Enemy.TopRotateL, EpFire.Straight);
//                stageBulider.commonBoss_Top(5, 2, 8, Enemy.TopRotateR, EpFire.Straight);
//
//                stageBulider.commonBoss_Center(5, 3, 5, Enemy.CenterRotateL,   EpFire.Straight);
//                stageBulider.commonBoss_Center(5, 3, 5, Enemy.CenterRotateR, EpFire.Straight);
//
//                stageBulider.juniorBoss(1, 12, 3, 2,7,2);
//                stageBulider.easyBoss(3, 5, true);
//                stageBulider.easyBoss(3, 5, false);
//                stageBulider.commonBoss_Top(5, 1, 8, Enemy.TopRotateR, EpFire.Aim);
//                stageBulider.commonBoss_Top(5, 1, 8, Enemy.TopRotateL, EpFire.Aim);
//                stageBulider.commonBoss_Center(5, 3, 5, Enemy.CenterRotateR, EpFire.Aim);
//                stageBulider.commonBoss_Center(5, 3, 5, Enemy.CenterRotateL, EpFire.Aim);
//
//                stageBulider.juniorBoss(4, 12, 3, 2,7,2);
//                stageBulider.easyBoss(3, 5, true);
//                stageBulider.easyBoss(3, 5, false);
//                stageBulider.juniorBoss(2, 12, 3, 2,7,2);
//                stageBulider.commonBoss_Center(5, 3, 5, Enemy.CenterRotateL, EpFire.Aim);
//                stageBulider.commonBoss_Center(5, 3, 5, Enemy.CenterRotateR, EpFire.Aim);
//                stageBulider.juniorBoss(3, 12, 3,2,7,2);
//                stageBulider.commonBoss_Top(5, 1, 8, Enemy.TopRotateR, EpFire.Aim);
//                stageBulider.commonBoss_Top(5, 1, 8, Enemy.TopRotateL, EpFire.Aim);
//                stageBulider.betterBoss(13, 2, 2,11,9);
//                stageBulider.easyBoss(3, 5, true);
//                stageBulider.easyBoss(3, 5, false);
                stageBulider.giveBonus();
                new Thread(boss2).start();
            }
        } catch (Exception e) {
            e=new Exception("第2关终止!");
            e.printStackTrace();
        }
    }
}
