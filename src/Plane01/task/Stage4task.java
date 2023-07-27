package Plane01.task;

import Plane01.GamePanel;
import Plane01.pojo.Enemy;
import Plane01.pojo.EpFire;

import java.io.IOException;
import java.util.List;
import java.util.TimerTask;

public class Stage4task extends TimerTask {
    private List<Enemy> enemies;
    private StageBulider stageBulider;
    private Boss4 boss4;

    public Stage4task(List<Enemy> enemies1) throws IOException {
        this.enemies=enemies1;
        stageBulider=new StageBulider(enemies1);
        boss4=new Boss4(enemies1, true);
    }

    @Override
    public void run() {
        try {
            if(!GamePanel.gameover) {
//                stageBulider.easyBoss(1, 7, false);
//                stageBulider.easyBoss(1, 7, true);
//                stageBulider.commonBoss_Top(5, 2, 10, Enemy.TopRotateL, EpFire.Straight);
//                stageBulider.commonBoss_Top(5, 2, 10, Enemy.TopRotateR, EpFire.Straight);
//                stageBulider.commonBoss_Center(5, 3, 7, Enemy.CenterRotateL, EpFire.Straight);
//                stageBulider.commonBoss_Center(5, 3, 7, Enemy.CenterRotateR, EpFire.Straight);
//
//                stageBulider.juniorBoss(1, 12, 3, 2,11,4);
//
//                stageBulider.easyBoss(3, 7, true);
//                stageBulider.easyBoss(3, 7, false);
//                stageBulider.commonBoss_Top(5, 1, 10, Enemy.TopRotateR, EpFire.Aim);
//                stageBulider.commonBoss_Top(5, 1, 10, Enemy.TopRotateL, EpFire.Aim);
//                stageBulider.commonBoss_Center(5, 3, 7, Enemy.CenterRotateR, EpFire.Aim);
//                stageBulider.commonBoss_Center(5, 3, 7, Enemy.CenterRotateL, EpFire.Aim);
//                stageBulider.betterBoss(13, 4, 2,13,4);
//
//                stageBulider.giveBonus();
//                stageBulider.juniorBoss(4, 12, 3, 2,11,4);
//
//                stageBulider.bombStruck(1,2,3,4,5,6,7,8);
//                stageBulider.bombStruck(0,2,3,4,5,6,7,9);
//                stageBulider.bombStruck(0,1,3,4,5,6,8,9);
//                stageBulider.bombStruck(0,1,2,4,5,7,8,9);
//                stageBulider.bombStruck(0,1,2,3,6,7,8,9);
//                stageBulider.bombStruck(0,1,2,4,5,7,8,9);
//                stageBulider.bombStruck(1,2,3,4,5,6,7,8);
//                stageBulider.bombStruck(0,2,3,4,5,6,7,9);
//                stageBulider.bombStruck(0,1,3,4,5,6,8,9);
//
//
//                stageBulider.easyBoss(3, 7, true);
//                stageBulider.easyBoss(3, 7, false);
//                stageBulider.juniorBoss(2, 12, 3, 2,11,4);
//                stageBulider.commonBoss_Center(5, 3, 7, Enemy.CenterRotateL, EpFire.Aim);
//                stageBulider.commonBoss_Center(5, 3, 7, Enemy.CenterRotateR, EpFire.Aim);
//                stageBulider.juniorBoss(3, 12, 3,2,8,4);
//                stageBulider.commonBoss_Top(5, 1, 9, Enemy.TopRotateR, EpFire.Aim);
//                stageBulider.commonBoss_Top(5, 1, 9, Enemy.TopRotateL, EpFire.Aim);
//                stageBulider.betterBoss(13, 4, 2,13,4);
//                stageBulider.easyBoss(3, 7, true);
//                stageBulider.easyBoss(3, 7, false);
//                stageBulider.commonBoss_Center(5, 3, 7, Enemy.CenterRotateL, EpFire.Aim);
//                stageBulider.commonBoss_Center(5, 3, 7, Enemy.CenterRotateR, EpFire.Aim);
//
//                stageBulider.bombStruck(1,2,3,4,5,6,7,8);
//                stageBulider.bombStruck(0,2,3,4,5,6,7,9);
//                stageBulider.bombStruck(0,1,3,4,5,6,8,9);
//                stageBulider.bombStruck(0,1,2,4,5,7,8,9);
//                stageBulider.bombStruck(0,1,2,3,6,7,8,9);
//                stageBulider.bombStruck(0,1,2,4,5,7,8,9);
//                stageBulider.bombStruck(1,2,3,4,5,6,7,8);
//                stageBulider.bombStruck(0,2,3,4,5,6,7,9);
//                stageBulider.bombStruck(0,1,3,4,5,6,8,9);

                stageBulider.giveBonus();
                new Thread(boss4).start();
            }
        } catch (Exception e) {
            e=new Exception("第4关终止!");
            e.printStackTrace();
        }
    }


}

//                                double[] dir=new double[]{0,1.0,Double.MAX_VALUE,-1.0,0,1.0,Double.MAX_VALUE,-1.0,0,0};
//                                RotateEpfire[] fires=new RotateEpfire[9];
//                                for(int i=0;i<46;i++){
//                                    Point centerL=new Point(e.x + e.w / 2 - 70, e.y + 55);
//                                    Point centerR=new Point(e.x + e.w / 2 + 70, e.y + 55);
//                                    for(int k=0;k<9;k++){
//                                        double truedir=(dir[k]+Math.tan(i))/(1+dir[k]*Math.tan(i));
//                                        RotateEpfire fire=new RotateEpfire(5,centerL.x,centerL.y,
//                                                EpFire.Aim,new Point(centerL.x,centerL.y));
//                                        fires[k]=fire;
//                                        fire.dir=truedir;
//                                        fire.setArc(Math.atan(truedir));
//                                        fire.rotateRadius=150;
//                                        GamePanel.epfs.add(fire);
//                                    }
//                                    stageBulider.shortBreak(150);
//                                    for(int k=0;k<9;k++){
//                                        fires[k].setStartmove(true);
//                                    }
//                                    stageBulider.shortBreak(500);
//                                }
