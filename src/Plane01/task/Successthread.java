package Plane01.task;

import Plane01.GamePanel;
import Plane01.pojo.Scene;
import Plane01.utils.ImageUtils;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class Successthread implements Runnable {
    private GamePanel panel;
    private boolean ontime=false;
    private long starttime;

    public Successthread(long starttime,GamePanel panel){
        this.starttime=starttime;
        this.panel=panel;
    }

    @Override
    public void run() {
        synchronized (Successthread.class){
            try {
                GamePanel.isHurting=true;
                BufferedImage image=ImageUtils.getImg("/success.png");
                /*这里禁用之前写好的适配器，主机不由鼠标控制，做一小段通关动画出来*/
                panel.removeMouseListener(GamePanel.adapter);
                panel.removeMouseMotionListener(GamePanel.adapter);
                while (!ontime){
                    try {
                        GamePanel.success = null;
                        panel.repaint();
                        Thread.sleep(250);
                        GamePanel.success = image;
                        panel.repaint();
                        Thread.sleep(250);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (System.currentTimeMillis() - starttime >= 3000) {
                        ontime = true;
                    }
                }

                /*主机短暂向后移动，然后向前猛冲，以呈现出加速效果*/
                int n=10;
                while (n!=0){
                    GamePanel.hero.y+=10;
                    panel.repaint();
                    Thread.sleep(20);
                    n--;
                }
                Thread.sleep(200);
                while (GamePanel.hero.y>-GamePanel.hero.h){
                    GamePanel.hero.y-=16;
                    panel.repaint();
                    Thread.sleep(20);
                }
                Thread.sleep(2000);

                /*进入下一关*/
                GamePanel.stagenum++;
                GamePanel.currentstage=GamePanel.stagebox.get(GamePanel.stagenum);

                try {
                    GamePanel.scene=new Scene(GamePanel.stagenum+1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                GamePanel.action();
                GamePanel.epfs.clear();
                GamePanel.success=null;
                panel.addMouseListener(GamePanel.adapter);
                panel.addMouseMotionListener(GamePanel.adapter);

                /*通关动画中最后主机到了屏幕上方不可见区域，这里要进入下一关了，
                * 把主机的位置初始化到屏幕的中下区域，便于玩家操作
                * 同时进入新的关卡主机会回满血量*/
                GamePanel.hero.x=256-GamePanel.hero.w;
                GamePanel.hero.y=400;
                GamePanel.hero.blood =15;
                GamePanel.isHurting=false;
                GamePanel.clearstage=false;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
