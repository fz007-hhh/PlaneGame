package Plane01.task;

import Plane01.GamePanel;
import Plane01.pojo.Enemy;
import Plane01.pojo.EpFire;

import java.io.IOException;
import java.util.List;
import java.util.Random;

public class Boss1 implements Runnable {
    private List<Enemy> enemies;
    private StageBulider stageBulider;
    private Enemy e;

    public Boss1(List<Enemy> enemies1,boolean isBoss) throws IOException {
        stageBulider=new StageBulider(enemies1);
        enemies=enemies1;
        e=new Enemy(21,2, 6,40);
        e.isBoss=isBoss;
        e.blood=700;
        e.maxblood=e.blood;
    }

    @Override
    public void run() {
        try {
            enemies.add(e);
            while(e.blood>0){
                //如果正常击败boss，那么先在judgetask中删除boss,
                //在stagetask中清除所有子弹
                //防止重新开始boss进程还没结束
                stageBulider.shortBreak(200);
                Random random=new Random();
                //英雄机如果位于敌机上面贴脸输出，敌机会马上跑到英雄机的位置
                if(GamePanel.hero.y-e.y-e.h-10<=0){
                    e.x=GamePanel.hero.x;
                }
                else{
                    int a=random.nextInt(5)+0;
                    switch (a){
                        case 1:{
                            if(enemies.size()>0) commonAttack1(e);
                            break;
                        }
                        case 2: {
                            e.ShootHero(2, EpFire.Straight, 9,0,17,4);
                            e.ShootHero(3,EpFire.Missile,16,6,200);
                            e.ShootHero(2, EpFire.Straight, 9,0,17,4);
                            break;
                        }
                        case 3:{
                            //不停发射激光，中间穿插自瞄导弹
                            bigAttack(e);
                            break;
                        }
                        default:{
                            for(int i=0;i<4;i++) {
                                e.ShootHero(2, EpFire.Aim, 13,-3,9,4);
                                stageBulider.shortBreak(80);
                                e.ShootHero(2, EpFire.Aim, 13,10,9,4);
                                stageBulider.shortBreak(80);
                            }
                        }
                    }

                    stageBulider.shortBreak(1800);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void bigAttack(Enemy e) throws Exception {
        for(int k=0;k<2;k++) {
            for (int i = 50; i >=13; i--) {
                EpFire fire1 = new EpFire(4, e.x + e.w / 2 - i*2, e.y + 40, EpFire.Straight, 11);
                EpFire fire2 = new EpFire(4, e.x + e.w / 2 + i*2, e.y + 40, EpFire.Straight, 11);
                GamePanel.epfs.add(fire1);
                GamePanel.epfs.add(fire2);
                stageBulider.shortBreak(70);
            }
            e.ShootHero(3,3,15,4);
            for(int i=13;i<=50;i++){
                EpFire fire1 = new EpFire(4, e.x + e.w / 2 - i*2, e.y + 40, EpFire.Straight, 11);
                EpFire fire2 = new EpFire(4, e.x + e.w / 2 + i*2, e.y + 40, EpFire.Straight, 11);
                GamePanel.epfs.add(fire1);
                GamePanel.epfs.add(fire2);
                stageBulider.shortBreak(70);
            }
            e.ShootHero(3,EpFire.Missile,15,4);
        }
    }

    private void commonAttack1(Enemy e) throws Exception {
        for(int i=0;i<3;i++) {
            e.ShootHero(Enemy.ShootMany, EpFire.Straight, 9, 0,13,5);
            stageBulider.shortBreak(150);
        }
        stageBulider.shortBreak(500);
        for(int i=0;i<30;i++) {
            e.ShootHero(Enemy.ShootOneL, EpFire.Aim, 13,-3,9,4);
            stageBulider.shortBreak(60);
            e.ShootHero(Enemy.ShootOneR, EpFire.Aim, 13,10,9,4);
            stageBulider.shortBreak(60);
        }
    }
}
