package Plane01.pojo;

import Plane01.GameFrame;
import Plane01.GamePanel;
import Plane01.utils.ImageUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Scene {
    private BufferedImage bgimage;//使用队列储存背景图片，取一个释放一个
    public int bgy;//背景图片的Y坐标，方便记录背景图片的移动
    private int vertical;
    private int width;

    public Scene(int num) throws IOException {
        bgimage= ImageUtils.getImg("/bg"+num+".jpg");
        bgy=0;
        vertical= GameFrame.Border_Vertical;
        width=GameFrame.Border_Horizon;
    }

    /*滚动背景实现原理：
    * 利用getSubimage()方法将背景分为上下两部分，
    * 上部分和下部分沿着bgy分别滚动*/
    public void move(Graphics g){
        /*由于我们开了bgtask这个线程，进行scene.move()方法时
        * bgy也在顺着bgtask的作用变化，这里使用变量y保存
        * 调用move方法那一刻时的bgy，以防止调用move()方法
        * 的过程中bgy发生变化造成上下部分闪烁或断层*/
        Integer y=bgy;

        if(y>=vertical||y==0){
            g.drawImage(bgimage,0,0,width,vertical,null);
            bgy=0;
        }
        else if(y>0){
            g.drawImage(bgimage.getSubimage(0,vertical-y,width, y),
                    0,0,width,y+1,null);
            g.drawImage(bgimage.getSubimage(0,0, width, vertical-y),
                    0,y,width,vertical-y,null);
        }
    }

    public void flushScene() throws IOException {
        bgimage=ImageUtils.getImg("/bg"+ GamePanel.stagenum +".jpg");
    }
}
