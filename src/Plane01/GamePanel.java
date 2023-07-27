package Plane01;

import Plane01.bonus.Bonus;
import Plane01.pojo.*;
import Plane01.task.*;
import Plane01.utils.ImageUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.*;

//Jframe相当于画架的画框，Jpanel相当于画框框住的那张纸
//游戏面板
public class GamePanel extends JPanel {
    public static int stagenum=0;
    public static Scene scene;/*游戏场景*/
    public static Hero hero;
    public static List<Enemy>enemies=new ArrayList<>();//存储敌机
    public static List<Fire> fs=new ArrayList<>();/*存储我方子弹*/
    public static List<EpFire>epfs=new ArrayList<>();/*存储敌机子弹*/
    public static List<Bonus>bonus=new ArrayList<>();/*存储补给品*/
    public static List<TimerTask>stagebox=new ArrayList<>();/*存储所有关卡任务*/
    public static int score;/*分数*/
    public static BufferedImage bloodpng;//血条图片
    public static BufferedImage bossbloodpng;//Boss血条图片
    public static boolean gameover=false;//游戏结束标志
    public static boolean clearstage=false;//过关标志
    public static BufferedImage success;/*过关是有个闪烁状态的小动画，需要使用图片*/
    public static BufferedImage shiled;//护盾
    public static Timer enemyfrqc;//敌机出现频率控制计时器
    public static Timer judgefrqc;//碰撞、攻击、伤害判定计时器
    public static Timer bgfrqc;/*滚动背景计时器*/
    public static Timer imgsnumfrqc;/*图片切换计时器，敌机子弹有闪光效果，需要切换图片*/
    public static boolean isHurting=false;//最开始不处于受伤状态下
    public static boolean isShileding=false;//最开始不处于护盾状态下
    public static boolean clearall=false;//最开始不处于桶滚状态下
    public static Enemy curBoss;//当前Boss
    public TimerTask bgTask;/*滚动背景任务*/
    public TimerTask imgsnumTask;/*图片切换任务*/
    public static TimerTask currentstage;/*当前关卡，使用这个变量可以方法切换关卡*/
    public static MouseAdapter adapter;
    /*鼠标适配器，通关小动画中我们需要暂时停止玩家控制鼠标，
    * 而后还需要恢复，故添加此变量记录玩家操作时的鼠标信息*/


    static {
        try {
            hero = new Hero();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GamePanel( GameFrame frame) throws IOException {
        bloodpng= ImageUtils.getImg("/blood.png");
        bossbloodpng= ImageUtils.getImg("/bossblood.png");
        scene=new Scene(stagenum+1);
        bgfrqc=new Timer();
        imgsnumfrqc=new Timer();
        enemyfrqc=new Timer();
        judgefrqc=new Timer();

        setBackground(Color.pink);
        stagebox.add(new Stage1task(enemies));
        stagebox.add(new Stage2task(enemies));
        stagebox.add(new Stage3task(enemies));
        stagebox.add(new Stage4task(enemies));
        stagebox.add(new Stage5task(enemies));
        currentstage=stagebox.get(stagenum);

        bgTask=new TimerTask() {
            @Override
            public void run() {
                scene.bgy+=2;
            }
        };
        imgsnumTask=new TimerTask() {
            @Override
            public void run() {
                for(int i=0;i<epfs.size();i++){
                    EpFire fire= null;
                    try {
                        fire = epfs.get(i);
                    } catch (Exception e) {
                        System.out.println("敌机子弹数组报错");
                        e.printStackTrace();
                    }
                    if(fire.imgs.size()==1)continue;
                    fire.imgsnum=(fire.imgsnum+1)%fire.imgs.size();
                }
            }
        };
        /*每次导入图片记得  文件->同步  刷新一下*/
        adapter=new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                //让英雄机的坐标等于鼠标的横纵坐标,完成移动
                if (!gameover) {
                    int mx=e.getX();
                    int my=e.getY();

                    hero.moveToMouse(mx,my);
                }
                //刷新页面，将英雄机绘制到新的位置上
                repaint();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                //游戏结束后，点击屏幕可以重新开始
                if(gameover||clearall){
                    try {
                        /*Timertask关卡任务调用之后不能再次被调用，
                        * 所以需要重新封装stagebox*/
                        stagebox.clear();
                        stagebox.add(new Stage1task(enemies));
                        stagebox.add(new Stage2task(enemies));
                        stagebox.add(new Stage3task(enemies));
                        stagebox.add(new Stage4task(enemies));
                        stagebox.add(new Stage5task(enemies));

                        hero=new Hero();
                        score=0;
                        enemyfrqc=new Timer();
                        //清空所有的敌机和子弹
                        enemies.clear();
                        epfs.clear();
                        fs.clear();
                        //默认在死亡关卡重开
                        Random rd=new Random();
                        stagenum=(stagenum)%5;
                        scene= new Scene(stagenum+1);
                        curBoss=null;
                        gameover=false;
                        currentstage=stagebox.get(stagenum);
                        action();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                }
            }
          }
        };

        //适配器加入到监听器中
        addMouseListener(adapter);
        addMouseMotionListener(adapter);

        KeyAdapter kd=new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keycode=e.getKeyCode();
                if(keycode==KeyEvent.VK_UP){
                    hero.y-=10;
                }
                else if(keycode==KeyEvent.VK_DOWN){
                    hero.y+=10;
                }
                else if(keycode==KeyEvent.VK_LEFT){
                    hero.x-=10;
                }
                else if(keycode==KeyEvent.VK_RIGHT){
                    hero.x+=10;
                }
                repaint();
            }
        };
        //键盘监听器需要利用窗体来加
        if (!gameover) {
            frame.addKeyListener(kd);
        }
        judgefrqc.schedule(new Judgetask(this),0,20);
        //记得在任务中刷新页面，并增加游戏是否结束的判定，否则画面会不动或游戏结束后仍有目标移动
        bgfrqc.schedule(bgTask,0,40);
        imgsnumfrqc.schedule(imgsnumTask,0,200);
    }

    public static void action(){

        try {
//            enemyfrqc.schedule(currentstage,0);
            enemyfrqc.schedule(currentstage,0);
        } catch (Exception e) {
            System.out.println(enemyfrqc);
            e.printStackTrace();
        }
    }

    public void rotate(Graphics2D g2D,double dir,EpFire fire)  {
        //当有多张图片时减缓一下图片刷新效果
        g2D.rotate(dir,fire.x+fire.w/2,fire.y+fire.h/2);
        g2D.drawImage(fire.imgs.get(fire.imgsnum),fire.x,fire.y,fire.w,fire.h,null);
        /*g2D旋转之后会保存所旋转的角度，所以我们还得给它再转回来*/
        g2D.rotate(-dir,fire.x+fire.w/2,fire.y+fire.h/2);

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        //滚动背景
        scene.move(g);
        if(shiled!=null)
        g.drawImage(shiled,hero.x-18, hero.y-20,shiled.getWidth(),shiled.getHeight(),null );
        g.drawImage(hero.img,hero.x,hero.y,hero.w,hero.h ,null);
//        g.drawRect(hero.x+51,hero.y+56,20,25);
        for(int i=0;i<enemies.size();i++) {
            Enemy enemy=enemies.get(i);
            if(enemy.isBoss==true||enemy.maxblood>400){curBoss=enemy;}
            g.drawImage(enemy.img, enemy.x, enemy.y, enemy.w, enemy.h, null);
        }//使用get(i)可以防止报错

        for(int i=0;i<fs.size();i++){
            Fire fire=fs.get(i);
            g.drawImage(fire.img,fire.x,fire.y,fire.w,fire.h,null);
        }

        for(int i=0;i<bonus.size();i++){
            Bonus bon=bonus.get(i);
            g.drawImage(bon.img,bon.x,bon.y,bon.w,bon.h,null);
        }
        /*java中直接操作的是指针，赋值赋的就是地址，g转型之后内容变了，
        但是指针地址不变，所以g转为g2D之后实际上g和g2D是同步变化的*/
        Graphics g1=g.create();
        Graphics2D g2D= (Graphics2D) g;
        g2D.setColor(Color.white);

        for(int i=0;i<epfs.size();i++){
            EpFire fire=epfs.get(i);
            double dir=0;
            /*java中旋转角度的范围是[-π/2,π/2]，java坐标系和正常坐标系
            * 沿y轴对称，所以图中斜率的正负和正常坐标系斜率相反*/
            if(fire.id>=3) {
                dir = (fire.dir < 0) ? -(Math.toRadians(90) - Math.atan(fire.dir)) :
                        (Math.toRadians(90) + Math.atan(fire.dir));
                rotate(g2D,dir, fire);
            }
            else{
                g2D.drawImage(fire.img,fire.x,fire.y,fire.w,fire.h,null);
            }
        }

        g=g1;
        if(success!=null)
        g.drawImage(success,30,180,success.getWidth(),success.getHeight(), null);

        g.setColor(Color.GREEN);
        g.setFont(new Font("幼圆",Font.BOLD,20));
        g.drawString("分数："+score,10,70);

        //主机血条展示
        g.setColor(new Color(212,20,14));
        g.drawImage(bloodpng,10,80,bloodpng.getWidth(),bloodpng.getHeight(),null);
        g.fillRect(28,84, (bloodpng.getWidth()-32)*hero.blood /15, bloodpng.getHeight()-9);
        g.setColor(new Color(76,76,76));
        g.fillRect(28+(bloodpng.getWidth()-32)*hero.blood /15,84,
                (bloodpng.getWidth()-32)*(15-hero.blood)/15, bloodpng.getHeight()-9);

        //Boss血条展示
        if(curBoss!=null) {
            g.setColor(new Color(210, 35, 30));
            g.drawImage(bossbloodpng, 5, 10, 480, 20, null);
            g.fillRect(40, 16, 415* curBoss.blood / curBoss.maxblood, 6);
            g.setColor(new Color(14, 23, 18));
            g.fillRect(40 + 415* curBoss.blood / curBoss.maxblood, 16,
                    415 * (curBoss.maxblood - curBoss.blood) / curBoss.maxblood, 6);
        }

        for(int i=0;i<hero.lifenum;i++){
            g.drawImage(hero.img,465-i*35,5,30,30,null);
        }
        if(clearall){
            g.setColor(Color.green);
            g.setFont(new Font("幼圆",Font.BOLD,20));
            g.drawString("恭喜通关！点击屏幕可以再玩一次哦!",100,280);
            g.setColor(new Color(22, 255, 220, 246));
            g.drawString("分数:"+score,130,320);
        }

        if(gameover){
            g.setColor(Color.red);
            g.setFont(new Font("幼圆",Font.BOLD,20));
            g.drawString("闯关失败...点击屏幕,再战！",100,280);
        }

   }
}
 /*
       这里非常不建议使用基于范围的for循环或者iterator，因为这两种循环下
       要求集合内容不发生变化，但是我们这个游戏是多线程的，每次我们移动飞机
       时fs中的集合内容在不断发生变化，从而导致触发异常
       （触发有可能性，当基于for循环和另外线程使用到同一个对象时就会触发）
       for(Fire fire:fs){
           System.out.println(fire.x+"="+fs.size()+" --- "+count);
           g.drawImage(fire.img,fire.x,fire.y,fire.w,fire.h,null);
       }
       那么为什么只要改成get(i)就没错了呢？
       个人猜测，get获取对象是根据位置参数i直接获取的，基于for和迭代器只能通过next方法依次调用；
       还有，get只会获取某一时刻的对象（线程是否更改对象都不影响get，更改了get就获取更改后的对象，
       没更改就获取更改前的对象），next则会利用expectedModCount != modCount进行多次判断，因而报错
       */