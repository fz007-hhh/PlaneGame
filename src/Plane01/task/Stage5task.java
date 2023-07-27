package Plane01.task;

import Plane01.GamePanel;
import Plane01.pojo.Enemy;
import Plane01.pojo.EpFire;

import java.io.IOException;
import java.util.List;
import java.util.TimerTask;

public class Stage5task extends TimerTask {
    private List<Enemy> enemies;
    private StageBulider stageBulider;
    public  static boolean defeatBoss=false;
    private Boss1 boss1;
    private Boss2 boss2;
    private Boss3 boss3;
    private Boss4 boss4;
    private Boss5 boss5;

    public Stage5task(List<Enemy> enemies1) throws IOException {
        this.enemies=enemies1;
        stageBulider=new StageBulider(enemies1);
        boss1=new Boss1(enemies1,false);
        boss2=new Boss2(enemies1,false);
        boss3=new Boss3(enemies1,false);
        boss4=new Boss4(enemies1,false);
        boss5=new Boss5(enemies1);
    }

    @Override
    public void run() {
        try {
            if(!GamePanel.gameover) {
                stageBulider.easyBoss(1, 5, false);
                stageBulider.easyBoss(1, 5, true);
                stageBulider.giveBonus();
                boss1.run();

                defeatBoss=false;
                stageBulider.commonBoss_Top(5, 1, 8, Enemy.TopRotateR, EpFire.Aim);
                stageBulider.commonBoss_Top(5, 1, 8, Enemy.TopRotateL, EpFire.Aim);
                stageBulider.giveBonus();
                boss2.run();

                defeatBoss=false;
                stageBulider.commonBoss_Center(5, 3, 5, Enemy.CenterRotateR, EpFire.Aim);
                stageBulider.commonBoss_Center(5, 3, 5, Enemy.CenterRotateL, EpFire.Aim);
                stageBulider.giveBonus();
                boss3.run();

                defeatBoss=false;
                stageBulider.juniorBoss(3, 12, 3,2,8,4);
                stageBulider.giveBonus();
                boss4.run();

                defeatBoss=false;
                stageBulider.betterBoss(13, 4, 2,13,4);
                stageBulider.giveBonus();

                new Thread(boss5).start();
            }
        } catch (Exception e) {
            e=new Exception("第5关终止!");
            e.printStackTrace();
        }
    }
}
