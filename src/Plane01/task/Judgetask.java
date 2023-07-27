package Plane01.task;

import Plane01.GameFrame;
import Plane01.GamePanel;
import Plane01.bonus.Bonus;
import Plane01.pojo.*;

import java.io.IOException;
import java.util.Random;
import java.util.TimerTask;

public class Judgetask extends TimerTask {
    public GamePanel panel;
    private Random random=new Random();
    private int lastbonus=0;//相邻两个补给品不能是同一种

    public Judgetask(GamePanel panel){
        this.panel=panel;
    }

    @Override
    public void run() {
        if (GamePanel.gameover==false) {
            try{
                shoot();
                hitHero(GamePanel.hero);
                flushHeroFire();
                flushBonus();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        panel.repaint();
    }

    private void flushBonus() throws IOException, ClassNotFoundException, InterruptedException {
        if(GamePanel.bonus.size()==0)return;
        for(int i=0;i<GamePanel.bonus.size();i++){
            Bonus bonus=GamePanel.bonus.get(i);
            //如果补给品被吃到就立即产生效果
            if(bonus.BonusIsCatched(GamePanel.hero)){
                GamePanel.bonus.remove(i);
                bonus.Effect();
            }
            else bonus.move();
        }
    }


    int shootnum=0;
    private void shoot() throws IOException {
        //创建子弹
        if(shootnum>=5) {
            Fire fire1 = new Fire(GamePanel.hero.x + 15, GamePanel.hero.y-35,0);
            Fire fire2=new Fire(GamePanel.hero.x+45,GamePanel.hero.y-35,1);
            Fire fire3 = new Fire(GamePanel.hero.x + 75, GamePanel.hero.y-35,2);
            GamePanel.fs.add(fire1);
            GamePanel.fs.add(fire2);
            GamePanel.fs.add(fire3);
            shootnum=0;
        }
        else shootnum++;
    }


    protected void flushHeroFire() throws IOException, InterruptedException {
        for(int i=0;i<GamePanel.fs.size();i++){
            Fire f=GamePanel.fs.get(i);
            f.move();
            //为减小消耗，当子弹飞出边界时及时删除
            if(f.y<=-50)GamePanel.fs.remove(i);
            bang(f);
        }

        for(int i=0;i<GamePanel.epfs.size();i++)
            GamePanel.epfs.get(i).move();

        for(int i=0;i<GamePanel.epfs.size();i++){
            EpFire epf= GamePanel.epfs.get(i);
            if(epf.HavAttack(GamePanel.hero)){
                GamePanel.epfs.remove(i);
                /*护盾状态下主机不受伤，但会有相关效果*/
                if(GamePanel.isShileding){
                    Thread hurt = new Thread(new HurtThread(System.currentTimeMillis(), panel, 0));
                    hurt.start();
                    ShiledThread.attacktime--;
                }
                else if(!GamePanel.isHurting) {
                    Thread hurt = new Thread(new HurtThread(System.currentTimeMillis(), panel, 6));
                    hurt.start();
                }
            }
            else if(epf.y>=GameFrame.Border_Vertical+30||epf.y<=-50){
                GamePanel.epfs.remove(i);
            }
        }//敌机子弹每次造成6点伤害

    }//子弹打敌机,刷新我方和敌方子弹

    private void bang(Fire f) throws IOException, InterruptedException {
        for(int i=0;i<GamePanel.enemies.size();i++){
            Enemy e=GamePanel.enemies.get(i);
            if(e.iSshootedBy(f)){
                e.blood-=f.attack;
                GamePanel.fs.remove(f);
            }
            if(e.x<=-e.w/2-10||e.x>=GameFrame.Border_Horizon+e.w+10||
            e.y>=GameFrame.Border_Vertical+10)
                GamePanel.enemies.remove(e);
            else if(e.blood<=0){
                //击杀补给品敌机会随机掉落补给品
                if(e.isBonus==true){
                    int a1=random.nextInt(4)+1;
                    a1=a1==lastbonus? a1+1 : a1;
                    a1=a1>4?1:a1;
                    GamePanel.bonus.add(new Bonus(a1,e.x,e.y));
                    lastbonus=a1;
                }
                //击杀boss会有通关动画展示
                if(e.isBoss== true){
                    //如果打通第五关，会显示通关提示
                    if(GamePanel.stagenum==4){
                        GamePanel.clearall=true;
                        GamePanel.isHurting=true;
                        GamePanel.stagenum++;
                    }
                    else {
                        GamePanel.clearstage = true;
                        Thread success = new Thread(new Successthread(System.currentTimeMillis(), panel));
                        success.start();
                    }
                    GamePanel.curBoss=null;
                    //击杀boss增加5000分
                    GamePanel.score+=4995;
                }
                /*由于最后一段Boss rush中前四个Boss不算关卡Boss，
                * 他们死亡后技能可能还会持续，这里多加一段判定，
                * 让第五关能在boss rush中抛出异常，避免死亡后技能还在释放*/
                if(e.maxblood>400&&GamePanel.stagenum==4){
                    Stage5task.defeatBoss=true;
                    GamePanel.curBoss=null;
                }

                GamePanel.score+=5;
                GamePanel.enemies.remove(e);
                e=null;
            }
        }
    }//攻击判定

    private void hitHero(Hero hero) throws IOException, InterruptedException, ClassNotFoundException {
        for(int i=0;i<GamePanel.enemies.size();i++){
            Enemy enemy=GamePanel.enemies.get(i);
            enemy.move();//敌机移动

            if(enemy.isHitBy(hero)){
                /*主机处于闪烁状态下无敌，此期间不会再次触发伤害
                * 每次碰撞，敌人默认受到30点伤害
                * 主机受到的伤害有敌机的类型而定*/
                /*护盾状态下主机不受伤，但会有相关效果*/
                if(GamePanel.isShileding){
                    Thread hurt =new Thread(new HurtThread(System.currentTimeMillis(),panel,0));
                    hurt.start();
                    ShiledThread.attacktime--;
                }
               else if(!GamePanel.isHurting) { ;
                    Thread hurt =new Thread(new HurtThread(System.currentTimeMillis(),panel,enemy.deblood));
                    hurt.start();
                    enemy.blood-=30;
                }
                flushHeroLife(hero);
            }

            if(enemy.x<10-enemy.w||enemy.x>502+enemy.w||enemy.blood<=0){
                GamePanel.enemies.remove(enemy);
                continue;
            }
        }
        flushHeroLife(hero);
    }

    private void flushHeroLife(Hero hero) throws InterruptedException {
        if(hero.lifenum<=0){
            GamePanel.gameover=true;
            GamePanel.enemies.clear();
            GamePanel.epfs.clear();
            GamePanel.fs.clear();
            //取消关卡中的线程，防止重开关卡时boss的攻击线程无法停止
            GamePanel.currentstage.cancel();
            /*cancel只是取消了正在执行的某项任务A,保证其不能再次执行，
            并不能直接中断任务A，如果不采取相关措施，任务A会在后台一直执行，
            直到结束
             */
            GamePanel.enemyfrqc.cancel();
            GamePanel.enemyfrqc.purge();
        }//飞机命用完则游戏结束
        if(hero.blood <=0){
            hero.lifenum--;
            Fire.levelup=false;
            GamePanel.isShileding=false;
            hero.blood =15;
        }
    }
}
