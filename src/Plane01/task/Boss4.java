package Plane01.task;

import Plane01.GamePanel;
import Plane01.pojo.Enemy;
import Plane01.pojo.EpFire;
import Plane01.pojo.RotateEpfire;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class Boss4 implements Runnable {
    private List<Enemy> enemies;
    private StageBulider stageBulider;
    private Enemy e;

    public Boss4(List<Enemy> enemies1,boolean isBoss) throws IOException {
        stageBulider=new StageBulider(enemies1);
        enemies=enemies1;
        e=new Enemy(24,2, 6,40);
        e.isBoss=isBoss;
        e.blood=1050;
        e.maxblood=e.blood;
    }

    @Override
    public void run() {
        try {
            enemies.add(e);
            Random random=new Random();
            while(e.blood>0){
                //如果正常击败boss，那么先在judgetask中删除boss,
                //在stagetask中清除所有子弹
                //防止重新开始boss进程还没结束
                stageBulider.shortBreak(800);
                if(GamePanel.hero.y-e.y-e.h-10<=0){
                    e.x=GamePanel.hero.x;
                    continue;
                }
                int a=random.nextInt(5)+0;
                switch (a){
                    case 1:{
                        if(enemies.size()>0) commonAttack4(e);
                        break;
                    }
                    case 2: {
                        /*发射1个不断旋转的风扇型子弹环，子弹环的旋转方向随机，
                         * 子弹环的旋转半径逐渐增大，旋转中心逐渐向屏幕下方移动*/
                        int a1=random.nextInt(10)+1;
                        a1=a1>5?-1:1;
                        RotateAttack(e,a1);
                        break;
                    }
                    case 3:{
                        /*激光从6个方向射击，每次射击完毕之后8个方向都顺时针旋转1°，
                         * 算是旋转式多角度射击*/
                        //6个方向的斜率，添加斜率的目的是为了让激光的角度可以旋转
                        sixDirAttack(e);
                        break;
                    }
                    default:{
                        /*发射大范围的闪烁弹药*/
                        for(int i=0;i<4;i++) {
                            e.ShootHero(2, EpFire.Aim, 12,-4,8+i,9);
                            stageBulider.shortBreak(80);
                            e.ShootHero(2, EpFire.Aim, 12,5,8+i,9);
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

    private void RotateAttack(Enemy e, int a1) throws Exception {
        /*连续发射1个风扇型子弹环，环形子弹生成完毕后再进行发射，*/
        Point center=new Point(e.x+e.w/2,e.y+e.h);
        double arc=0;
        //控制旋转方向是顺时针还是逆时针
        RotateEpfire[][] epfires=new RotateEpfire[8][10];

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
                RotateEpfire fire=new RotateEpfire(9,truex,
                        truey,0,new Point(center.x,center.y));

                fire.setDeY(2);
                fire.setDearc(0.04*a1);
                fire.rotateRadius=Math.sqrt(Math.pow(x1-center.x,2)+Math.pow(y1-center.y,2));
                fire.setDeradius(2);

                epfires[k][i]=fire;
                GamePanel.epfs.add(fire);
            }
            stageBulider.shortBreak(50);
        }
        for(int k=0;k<8;k++)
            for(int i=1;i<10;i++){
                RotateEpfire rotateEpfire= epfires[k][i];
                rotateEpfire.setStartmove(true);
            }
    }

    private void commonAttack4(Enemy e) throws Exception {
        double[]dir=new double[]{2.75,-2.75};
        for(int i=0;i<2;i++) {
            e.ShootHero(Enemy.ShootMany, EpFire.Aim, 11,-4,12,9);
            stageBulider.shortBreak(80);
            e.ShootHero(Enemy.ShootMany, EpFire.Aim, 11,5,12,9);
            stageBulider.shortBreak(80);
        }
        stageBulider.shortBreak(1000);
        for(int i=1;i<=50;i++){
            for(int k=0;k<2;k++) {
                EpFire fire1 = new EpFire(5, e.x + e.w / 2 - 70, e.y + 55, dir[k], EpFire.Aim, 12);
                GamePanel.epfs.add(fire1);
            }
            for(int k=0;k<2;k++) {
                EpFire fire1 = new EpFire(5, e.x + e.w / 2 + 70, e.y + 55, dir[k], EpFire.Aim, 12);
                GamePanel.epfs.add(fire1);
            }
            e.ShootHero(Enemy.ShootOneL, EpFire.Straight, 13,5);
            e.ShootHero(Enemy.ShootOneR, EpFire.Straight, 13,5);
            stageBulider.shortBreak(100);
            if(i%6==0)e.ShootHero(Enemy.ShootMissle, EpFire.Missile, 13, 3);
        }
    }

    private void sixDirAttack(Enemy e) throws Exception {
        double dir[]=new double[]{2.75,1.0,0.363,-0.363,-1.0,-2.75};
        for (int k=0;k<3;k++) {
            for(int i=0;i<=20;i++){
                /*激光柱的角度开始向内旋转，成发散性攻击*/
                int dearc=i;
                for(int j=0;j<6;j++) {
                    double k1=(dir[j]-Math.tan(dearc))/(1+dir[j]*Math.tan(dearc));
                    EpFire fire1 = new EpFire(5, e.x + e.w / 2 - 70, e.y + 55, k1, EpFire.Aim, 12);
                    GamePanel.epfs.add(fire1);
                }
                for(int j=0;j<6;j++) {
                    double k1=(dir[j]+Math.tan(dearc))/(1-dir[j]*Math.tan(dearc));
                    EpFire fire1 = new EpFire(5, e.x + e.w / 2 + 70, e.y + 55, k1, EpFire.Aim, 12);
                    GamePanel.epfs.add(fire1);
                }
                stageBulider.shortBreak(90);
            }
            stageBulider.shortBreak(200);
        }

        for(int k=0;k<5;k++){
            e.ShootHero(Enemy.ShootMany,EpFire.Aim, 8, 0,11,10);
            stageBulider.shortBreak(100);
        }
        for(int k=0;k<3;k++)
            e.ShootHero(Enemy.ShootMany,EpFire.Aim, 13+k, 0,13+k,10);
        stageBulider.shortBreak(150);
    }
}
