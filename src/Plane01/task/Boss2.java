package Plane01.task;

import Plane01.GamePanel;
import Plane01.pojo.Enemy;
import Plane01.pojo.EpFire;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class Boss2 implements Runnable {
    private List<Enemy> enemies;
    private StageBulider stageBulider;
    private Enemy e;

    public Boss2(List<Enemy> enemies1,boolean isBoss) throws IOException {
        stageBulider=new StageBulider(enemies1);
        enemies=enemies1;
        e=new Enemy(22,2, 6,40);
        e.isBoss=isBoss;
        e.blood=800;
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
                if(GamePanel.hero.y-e.y-e.h+10<=0){
                    e.x=GamePanel.hero.x;
                }
                else{
                    int a=random.nextInt(5)+0;
                    switch (a){
                        case 1:{
                            if(enemies.size()>0) commonAttack2(e);
                            break;
                        }
                        case 2: {
                            CircleAttack(e);
                            break;
                        }
                        case 3:{
                            for(int i=-10;i<45;i++){
                                /*当i>=25时，激光柱的角度开始向内旋转，成发散性攻击*/
                                int dearc= i>=25?i-24:0;
                                double k1=(2.75-Math.tan(dearc))/(1+2.75*Math.tan(dearc));
                                double k2=(1.0-Math.tan(dearc))/(1+1.0*Math.tan(dearc));
                                double k3=(0.363-Math.tan(dearc))/(1+0.363*Math.tan(dearc));

                                EpFire fire1 = new EpFire(6, e.x + e.w / 2 - 70, e.y + 55,k1, EpFire.Aim, 12);
                                EpFire fire2 = new EpFire(6, e.x + e.w / 2 - 70, e.y + 55,k2 ,EpFire.Aim, 12);
                                EpFire fire3 = new EpFire(6, e.x + e.w / 2 - 70, e.y + 55,k3, EpFire.Aim, 12);
                                EpFire fire4 = new EpFire(6, e.x + e.w / 2 + 70, e.y + 55,-k1, EpFire.Aim, 12);
                                EpFire fire5 = new EpFire(6, e.x + e.w / 2 + 70, e.y + 55,-k2,EpFire.Aim, 12);
                                EpFire fire6 = new EpFire(6, e.x + e.w / 2 + 70, e.y + 55,-k3, EpFire.Aim, 12);
                                GamePanel.epfs.add(fire1);
                                GamePanel.epfs.add(fire2);
                                GamePanel.epfs.add(fire3);
                                GamePanel.epfs.add(fire4);
                                GamePanel.epfs.add(fire5);
                                GamePanel.epfs.add(fire6);
                                stageBulider.shortBreak(70);
                            }
                            break;
                        }
                        default:{
                            for(int i=0;i<3;i++) {
                                e.ShootHero(2, EpFire.Aim, 11,-4,11,4);
                                stageBulider.shortBreak(80);
                                e.ShootHero(2, EpFire.Aim, 11,5,11,4);
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

    public void commonAttack2(Enemy e) throws Exception {
        for(int i=0;i<3;i++) {
            e.ShootHero(Enemy.ShootMany, EpFire.Aim, 11,-4,11,9);
            stageBulider.shortBreak(80);
            e.ShootHero(Enemy.ShootMany, EpFire.Aim, 11,5,11,9);
            stageBulider.shortBreak(80);
        }
        stageBulider.shortBreak(1000);
        for(int i=0;i<15;i++){
            e.ShootHero(Enemy.ShootOneL, EpFire.Aim, 13,4);
            e.ShootHero(Enemy.ShootOneR, EpFire.Aim, 13,4);
            stageBulider.shortBreak(200);
        }
    }

    public void CircleAttack(Enemy e) throws Exception {
        /*发射一个子弹构成的大圆环，圆环形状像大章鱼*/
        Point center=new Point(e.x+e.w/2,e.y+e.h);
        double arc=0;

        for (int k=0;k<8;k++) {
            arc=Math.toRadians(k*45);//旋转角度
            for(int i=1;i<10;i++){
                double x1=  (center.x+180-Math.cos(Math.toRadians(i*10))*180);
                double y1=  (center.y-Math.sin(Math.toRadians(i*10))*180);
                double firstarc=Math.atan((center.y-y1)/(x1-center.x));//初始角度
                int truex= (int) (center.x+Math.sqrt((x1-center.x)*(x1-center.x)+
                        (y1-center.y)*(y1-center.y))*Math.cos(arc+firstarc));
                int truey= (int) (center.y-Math.sqrt((x1-center.x)*(x1-center.x)+
                        (y1-center.y)*(y1-center.y))*Math.sin(arc+firstarc));
                //            int x1= (int) (center.x+100-Math.cos(i*10)*100);
                //            int y1= (int) (center.y-Math.sin(i*10)*100);
                EpFire fire=new EpFire(9,truex,
                        truey,EpFire.Straight,3);
                GamePanel.epfs.add(fire);
            }
        }
    }
}
