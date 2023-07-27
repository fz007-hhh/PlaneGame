package Plane01.pojo;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class FlyObject {
    //飞行物类，可以简化代码
//    public final static String imghead="D:\\IDEA Ultimate Edit\\JavaBasic\\HeadPro\\PlaneGame\\image";
    public final static String imghead="/image";
    public BufferedImage img;
    public int speed;
    public int x;
    public int y;
    public int w;
    public int h;

    //将移动函数设置为move(),抽象函数
    public abstract void move() throws InterruptedException, ClassNotFoundException;

    public boolean RectCross(Rectangle r1, Rectangle r2){
        Point p1=new Point(r1.x+r1.width/2,r1.y+r1.height/2);
        Point p2=new Point(r2.x+r2.width/2,r2.y+r2.height/2);
        if((Math.abs(p1.x-p2.x)<=r1.width/2+r2.width/2)&&
                Math.abs(p1.y-p2.y)<=r1.height/2+r2.height/2)
            return true;

        return false;
    }/*矩形相交检测*/

}
