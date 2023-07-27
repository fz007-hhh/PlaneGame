package Plane01.task;

import Plane01.GameFrame;
import Plane01.GamePanel;
import Plane01.pojo.Enemy;

import java.io.IOException;
import java.util.List;

public class StageBulider {
    private List<Enemy> enemies;
    private int[] junbossX;
    private int[] junbossY;

    public StageBulider(List<Enemy> enemies) {
        this.enemies = enemies;
    }

    /*屏幕上方依次出现7架敌机，位置互相交错，子弹直接向下*/
    public void easyBoss(int photonum,int speed ,boolean isLeft) throws Exception {
        int []x1;
        int border=GameFrame.Border_Horizon;
        //如果是设计排布左半边
        if(isLeft==true)x1=new int[]{75,145,5};
        else x1=new int[]{border-160,border-260,border-60};

        try {
            for(int i=0;i<7;i++) {
                Enemy enemy=new Enemy(photonum,speed,Enemy.ToMaxFloatY);
                enemy.blood=8;
                enemy.maxFloatY=enemy.w+10+GameFrame.Border_Vertical;
                enemy.x=x1[i%3];
                enemies.add(enemy);
                /*每产生一次敌机就要短暂暂停，否则生成的多个敌机会重合*/
                shortBreak(500);
                enemy.ShootHero(1, 2, 9);
            }
            shortBreak(1500);
        } catch (Exception e){
            throw new RuntimeException("easyBoss");
        }
//        for(int i=enemies.size()-7;i<enemies.size();i++){
//            try {
//                enemies.get(i).ShootHero(1,fireFly,9);
//            }catch (Exception e){
//                continue;
//            }
//            Thread.sleep(15);
//        }

    }//敌机生成方法

    /*屏幕上方依次出现5架敌机，按弧形轨迹移动，子弹可以直接向下，也可以瞄准主机*/
    public void commonBoss_Top(int n, int photonum, int speed, int movenum,int fireFly) throws Exception {
        try {
            for(int i=0;i<n;i++) {
                Enemy enemy=new Enemy(photonum,speed,movenum);
                enemy.blood=8;
                enemies.add(enemy);
                /*每产生一次敌机就要短暂暂停，否则生成的多个敌机会重合*/
                shortBreak(210);
            }
            for(int i=enemies.size()-n;i<enemies.size();i++){
                try {
                    enemies.get(i).ShootHero(1,fireFly,16);
                }catch (Exception e){
                    continue;
                }
                shortBreak(15);
            }
            shortBreak(1800);
        } catch (Exception e){
            throw new RuntimeException("commonboss_top");
        }

    }//敌机生成方法

    /*屏幕左右两方依次出现5架敌机，按弧形轨迹移动，子弹可以直接向下，也可以瞄准主机*/
    public void commonBoss_Center(int n, int photonum, int speed, int movenum,int fireFly) throws IOException, InterruptedException {
        try {
            for(int i=0;i<n;i++) {
                Enemy enemy=new Enemy(photonum,speed,movenum);
                enemy.blood=8;
                enemies.add(enemy);
                /*每产生一次敌机就要短暂暂停，否则生成的多个敌机会重合*/
                Thread.sleep(220);
            }
            for(int i=enemies.size()-n;i<enemies.size();i++){
                try {
                    enemies.get(i).ShootHero(1,fireFly,15);
                }catch (Exception e){
                    continue;
                }
                Thread.sleep(15);
            }
            shortBreak(1800);
        } catch (Exception e){
            throw new RuntimeException("common_center");
        }

    }//敌机生成方法

    //shootHero用来控制子弹的生成，fireFly用来控制子弹的飞行轨迹(按方向执行、瞄准主机、自定义轨迹)
    /*中级敌机攻击方式：
    * 连续5次释放较大范围子弹，
    * 然后放出4发导弹，导弹具备瞄准功能*/
    public void juniorBoss(int n, int photonum, int speed,Integer...firenumber) throws IOException, InterruptedException {
        int firenum=firenumber.length==0?5:firenumber[1];

        Enemy tem=new Enemy(photonum,speed,5);
        junbossX=new int[]{0, GameFrame.Border_Horizon-tem.x,GameFrame.Border_Horizon/2-(tem.x/2)};
        junbossY=new int[]{60,60,105};

        try {
            for (int i=n/4;i<(n==4?2:n%4);i++) {
                Enemy enemy = new Enemy(photonum, speed, Enemy.ToMaxFloatY,junbossY[i]);
                enemy.blood = 70;
                enemy.x=junbossX[i];
                enemies.add(enemy);
            }

            while(enemies.size()>0){
                int k=enemies.size()-n<=0? 0:enemies.size()-n;
                for(int a=0;a<5;a++){
                for(int i=k;i<enemies.size();i++)
                    /*args的最后一个元素对应子弹的图片，第一个元素对应子弹的偏移x量,中间元素决定子弹发射数量*/
                    enemies.get(i).ShootHero(2,1,15,0,firenum,2);
                    shortBreak(400);
                }
                for(int i=k;i<enemies.size();i++)
                    enemies.get(i).ShootHero(3,3,15,2);

                shortBreak(1400);
            }
            shortBreak(2000);
        } catch (Exception e) {
            throw new RuntimeException("junior");
        }

    }

    /*较高级boss攻击方式：
    * 1.具有跟踪功能，会随着主机的移动而移动
    * 2.发射较大范围且相互交错的子弹*/
    public void betterBoss(int photonum, int speed,Integer...firenumber) throws IOException, InterruptedException {
        int firenum=firenumber.length==0?9:firenumber[1];
        Enemy enemy=new Enemy(photonum,speed,Enemy.AutoToHero,80);
        enemy.blood=130;
        enemies.add(enemy);

        try {
            Enemy e=enemies.get(enemies.size()-1);
            while (enemy.blood>0){
                for(int a=0;a<4;a++){
                    if(enemies.size()==0){e=null; break;}
                    Enemy e1=enemies.get(enemies.size()-1);
                    /*args的最后一个元素对应子弹的图片，第一个元素对应子弹的偏移x量,中间元素决定子弹发射数量*/
                    e1.ShootHero(2,1,10,9,firenum,2);
                    shortBreak(110);
                    /*args的最后一个元素对应子弹的图片，第一个元素对应子弹的偏移x量,中间元素决定子弹发射数量*/
                    e1.ShootHero(2,1,10,-3,firenum,2);
                    shortBreak(110);

                }
                shortBreak(200);
                if(e!=null) e.ShootHero(3,3,15,3);
                shortBreak(1600);
            }
            shortBreak(2000);
        } catch (Exception e) {
            throw new RuntimeException("betterboss");
        }

    }

    /*导弹攻击，可以自定义导弹的降落位置*/
    public void bombStruck(Integer...args) throws IOException, InterruptedException {
        try {
            for(int i=0;i<args.length;i++){
                Enemy e1=new Enemy(15,25,Enemy.ToMaxFloatY,GameFrame.Border_Vertical+30);
                e1.blood=300;
                e1.x=args[i]*50+1;
                enemies.add(e1);
            }
            shortBreak(800);
        } catch (Exception e) {
            throw new RuntimeException("bombstruck");
        }
    }

    //提供补给道具的敌机，打败之后会掉落随机的补给道具
    public void giveBonus(){
        try {
            Enemy enemy=new Enemy(8,7,Enemy.BonusMoveFromL);
            enemy.blood=3;
            enemies.add(enemy);

            Enemy enemy2=new Enemy(8,7,Enemy.BonusMoveFromR);
            enemy2.blood=3;
            enemies.add(enemy2);

            shortBreak(1000);
        } catch (Exception e) {
            throw new RuntimeException("givebonus");
        }
    }

    /*游戏中敌人往往成群出现，而且敌机发射的子弹距离往往也需要自定义编辑，
    * 所以需要用到thread.sleep()
    * 可是直接使用的话有个后果，就是命数为0重开游戏时timertask实际上
    * 是还在运行的，虽然重开时我们已经清空了所有敌机和子弹，但是如果
    * 我们重开的时候timertask恰好处于thread.sleep()中时，就会发生
    * 错误，导致重开时已经死亡的敌机技能无法停止。
    * 所以每次使用sleep时都得增加一个if条件，在特殊情况下抛出异常，及时中断timertask
    * 为简化代码，故新增shortBreak方法*/
    public void shortBreak(long sleeptime) throws Exception {
        /*游戏结束重开或者通关时，要及时中断旧线程timertask，否则旧线程timertask
        * 会继续运行导致游戏异常;
        * shortBreak睡眠时间不能过长，否则重开时timertask恰好处于thread.sleep()中
        * 的概率会非常大，这里为了避免线程sleep()时间过长，将睡眠限制在0.1s之内*/
        if(sleeptime>=100){
            for(int i=0;i<sleeptime/100-1;i++){
                shortBreak(100);
            }
            shortBreak(sleeptime%100);
        }
        if((GamePanel.gameover==false&&GamePanel.clearstage==false)&&Stage5task.defeatBoss== false){
            Thread.sleep(sleeptime);
            return;
        }
        throw new RuntimeException("游戏结束，关卡终止，等待重开!");
    }
}
