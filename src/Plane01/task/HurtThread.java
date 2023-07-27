package Plane01.task;

import Plane01.GamePanel;

import java.awt.image.BufferedImage;

/*自创类实现runable接口之后，所创建的对象只能直接调用run，
那样其实还是相当于单线程，必须在创建一个thread，把对象
赋值进去，才能调用start函数，start函数才能开启多线程
thread hurt=new thread(new hurtThread(system.currentTimeMills(),panel))
hurt.start()
*/
public class HurtThread implements Runnable{
    private GamePanel panel;
    private int deblood;//扣血量
    private boolean ontime=false;
    private long starttime;

    HurtThread(long starttime,GamePanel panel,int deblood){
        this.starttime=starttime;
        this.panel=panel;
        this.deblood=deblood;
    }

    //受伤状态下，图片闪烁0.7秒
    @Override
    public  void run() {

        synchronized (HurtThread.class) {
            BufferedImage image=GamePanel.hero.img;
            GamePanel.isHurting=true;
            GamePanel.hero.blood -=deblood;
            while (!ontime) {
                try {
                    GamePanel.hero.img = null;
                    panel.repaint();
                    Thread.sleep(130);
                    GamePanel.hero.img = image;
                    panel.repaint();
                    Thread.sleep(130);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (System.currentTimeMillis() - starttime >= 700) {
                    ontime = true;
                }
            }
            GamePanel.isHurting=false;
        }

    }
}
