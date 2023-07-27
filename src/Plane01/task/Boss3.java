package Plane01.task;

import Plane01.GamePanel;
import Plane01.pojo.Enemy;
import Plane01.pojo.EpFire;
import Plane01.pojo.RotateEpfire;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class Boss3 implements Runnable {
    private List<Enemy> enemies;
    private StageBulider stageBulider;
    private Enemy e;

    public Boss3(List<Enemy> enemies1,boolean isBoss) throws IOException {
        stageBulider=new StageBulider(enemies1);
        enemies=enemies1;
        e=new Enemy(23,2, 6,40);
        e.isBoss=isBoss;
        e.blood=900;
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
                stageBulider.shortBreak(800);
                Random random=new Random();
                if(GamePanel.hero.y-e.y-e.h-10<=0){
                    e.x=GamePanel.hero.x;
                    continue;
                }
                int a=random.nextInt(5)+0;
                switch (a){
                    case 1:{
                        if(enemies.size()>0) commonAttack3(e);
                        break;
                    }

                    case 2: {
                        CircleAttack(e);
                        break;
                    }

                    case 3:{
                        for (int k=0;k<2;k++) {
                            for(int i=1;i<21;i++)
                                /*激光柱的角度开始向内旋转，成发散性攻击*/
                                rotateLazer(e,i);
                            stageBulider.shortBreak(70);
                            for(int i=21;i>0;i--)
                                /*激光柱的角度开始向外旋转，成发散性攻击*/
                                rotateLazer(e,i);
                        }
                        for(int k=0;k<3;k++){
                            e.ShootHero(Enemy.ShootMany,EpFire.Aim, 8, 0,11,8);
                            stageBulider.shortBreak(100);
                        }
                        for(int k=0;k<2;k++)
                            e.ShootHero(Enemy.ShootMany, EpFire.Aim, 14, 0,15+k,8);
                        stageBulider.shortBreak(150);
                        break;
                    }

                    default:{
                        for(int i=0;i<3;i++) {
                            e.ShootHero(2, EpFire.Aim, 12,-4,8+i,7);
                            stageBulider.shortBreak(80);
                            e.ShootHero(2, EpFire.Aim, 12,5,8+i,7);
                            stageBulider.shortBreak(80);
                        }
                    }
                }

                stageBulider.shortBreak(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void CircleAttack(Enemy e) throws Exception {
        /*连续发射8个子弹环，环形子弹生成完毕后再进行发射，*/
        double arc=0;
        RotateEpfire[] epfires=new RotateEpfire[8];
        for (int k=0;k<8;k++) {
            Point rotate=new Point(e.x+e.w/2,e.y+e.h/2-10);
            for(int i=1;i<8;i++){
                arc=Math.toRadians(45*i);
//                int x1= (int) (center.x+100-Math.cos(i*10)*100);
//                int y1= (int) (center.y-Math.sin(i*10)*100);
                int x1= (int) (rotate.x+100*Math.cos(arc));
                int y1=(int) (rotate.y+100*Math.sin(arc));
                RotateEpfire fire=new RotateEpfire(9,x1,
                        y1,0,new Point(rotate.x,rotate.y));
                epfires[i]=fire;
                /*修改旋转子弹的旋转半径，选中中心的Y轴位置，以及角速度*/
                fire.setDeY(8);
                fire.setDearc(0.20);
                fire.rotateRadius=50;
                fire.setDeradius(3);
                GamePanel.epfs.add(fire);
            }
            for(int i=1;i<=7;i++){
                RotateEpfire rotateEpfire= epfires[i];
                rotateEpfire.setStartmove(true);
            }
            stageBulider.shortBreak(700);
        }
    }

    private void commonAttack3(Enemy e) throws Exception {
        for(int i=0;i<3;i++) {
            e.ShootHero(Enemy.ShootMany, EpFire.Aim, 11,-4,12,9);
            stageBulider.shortBreak(80);
            e.ShootHero(Enemy.ShootMany, EpFire.Aim, 11,5,12,9);
            stageBulider.shortBreak(80);
        }
        stageBulider.shortBreak(1000);
        for(int i=1;i<=35;i++){
            e.ShootHero(Enemy.ShootOneL, EpFire.Straight, 13,6);
            e.ShootHero(Enemy.ShootOneR, EpFire.Straight, 13,6);
            stageBulider.shortBreak(100);
            if(i%6==0)e.ShootHero(Enemy.ShootMissle, EpFire.Missile, 13, 3);
        }
    }

    private void rotateLazer(Enemy e,int dearc) throws Exception {
        double k1=(2.75-Math.tan(dearc))/(1+2.75*Math.tan(dearc));
        double k2=(1.0-Math.tan(dearc))/(1+1.0*Math.tan(dearc));
        double k3=(0.363-Math.tan(dearc))/(1+0.363*Math.tan(dearc));

        EpFire fire1 = new EpFire(7, e.x + e.w / 2 - 70, e.y + 55,k1, EpFire.Aim, 12);
        EpFire fire2 = new EpFire(7, e.x + e.w / 2 - 70, e.y + 55,k2 ,EpFire.Aim, 12);
        EpFire fire3 = new EpFire(7, e.x + e.w / 2 - 70, e.y + 55,k3, EpFire.Aim, 12);
        EpFire fire4 = new EpFire(7, e.x + e.w / 2 + 70, e.y + 55,-k1, EpFire.Aim, 12);
        EpFire fire5 = new EpFire(7, e.x + e.w / 2 + 70, e.y + 55,-k2,EpFire.Aim, 12);
        EpFire fire6 = new EpFire(7, e.x + e.w / 2 + 70, e.y + 55,-k3, EpFire.Aim, 12);
        GamePanel.epfs.add(fire1);
        GamePanel.epfs.add(fire2);
        GamePanel.epfs.add(fire3);
        GamePanel.epfs.add(fire4);
        GamePanel.epfs.add(fire5);
        GamePanel.epfs.add(fire6);
        stageBulider.shortBreak(70);
    }
}
