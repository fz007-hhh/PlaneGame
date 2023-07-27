package Plane01.task;

import Plane01.GamePanel;
import Plane01.utils.ImageUtils;

import java.io.IOException;

public class ShiledThread implements Runnable {
    private GamePanel panel;
    private boolean ontime=false;
    private int lastattacktime;//更新可承受攻击次数
    public static int attacktime;//可承受攻击次数

    public ShiledThread(GamePanel panel){
        this.panel=panel;
        attacktime=1;
        lastattacktime=attacktime;
    }

    /*护盾状态下主机受伤不会造成伤害,可以抵挡1次伤害*/
    @Override
    public void run() {
        synchronized (ShiledThread.class) {
            GamePanel.isShileding=true;
            try {
                GamePanel.shiled= ImageUtils.getImg("/shiled.png");
                panel.repaint();
            } catch (IOException e) {
                e.printStackTrace();
            }
            while (attacktime!=0){
                /*护盾受到第一次伤害的1.5秒内不会受到第二次伤害*/
                if(lastattacktime!=attacktime){
                    int newattacktime=attacktime;
                    long pretime=System.currentTimeMillis();
                    while (System.currentTimeMillis()-pretime<=1500){
                        attacktime=lastattacktime;
                    }
                    lastattacktime=newattacktime;
                    attacktime=newattacktime;
                }
                panel.repaint();
            }

            GamePanel.shiled=null;
            panel.repaint();
            GamePanel.isShileding=false;
        }
    }
}
