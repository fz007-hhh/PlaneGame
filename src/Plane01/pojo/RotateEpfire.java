package Plane01.pojo;

import java.awt.*;
import java.io.IOException;

/*旋转型子弹，特点是旋转式移动
* 由于旋转式子弹中要多用几个变量，这里使用了继承,
* 并重写了move()方法，在这个move()方法里只用*/

public class RotateEpfire extends EpFire {
    public Point rotateCenter;//子弹旋转的中心
    public double rotateRadius;//旋转半径
    private double dearc;//子弹偏移量
    private double deradius;//子弹旋转半径的偏移量
    private double deY;//子弹旋转中心Y的偏移量
    private double deX;//子弹旋转中心X的偏移量
    private double arc;//子弹旋转的角度
    private boolean startmove=false;//旋转移动开始标志

    public RotateEpfire(int photonum, int hx, int hy, int firewalk) throws IOException {
        super(photonum, hx, hy, firewalk);
        rotateCenter=new Point(hx-100,hy-100);//默认旋转中心
        rotateRadius=300;//默认旋转半径
        dearc=0.04;//默认角度偏移量
        deradius=0;
        deX=0;
        deY=0;
        arc=0;
    }
    public RotateEpfire(int photonum, int hx, int hy, int firewalk,Point p1) throws IOException {
        this(photonum,hx,hy,firewalk);
        this.rotateCenter=p1;
        double m1=Math.atan((y-rotateCenter.y)*1.0/(x-rotateCenter.x));
        if(m1>0)
            arc=y-rotateCenter.y<0?m1+Math.toRadians(180):m1;
        else arc=y-rotateCenter.y>0?m1+Math.toRadians(180):m1;
        this.rotateRadius=Math.sqrt(Math.pow(hx-p1.x,2)+Math.pow(hy-p1.y,2));
    }

    public RotateEpfire(int photonum, int hx, int hy, int firewalk, int speed) throws IOException {
        super(photonum, hx, hy, firewalk, speed);
    }

    public RotateEpfire(int photonum, int hx, int hy, Double dir, int firewalk) throws IOException {
        super(photonum, hx, hy, dir, firewalk);
    }

    public RotateEpfire(int photonum, int hx, int hy, Double dir, int firewalk, int speed) throws IOException {
        super(photonum, hx, hy, dir, firewalk, speed);
    }

    @Override
    public void move(){
        //每次偏移角度增加speed°,x=center.x+r*cos(arc)  y=center.y+r*sin(arc)
        //仅当可移动标志为true时才能移动，这样可以编辑更多的子弹效果
        if (startmove==true) {
            arc+=dearc;
//        if(dir<0)rotateCenter.x-=speed/Math.sqrt(dir*dir+1);
//        else rotateCenter.x+=speed/Math.sqrt(dir*dir+1);

            rotateRadius+=deradius;
            rotateCenter.x+=deX;
            rotateCenter.y+=deY;

            x= (int) (rotateCenter.x+Math.cos(arc)*rotateRadius);
            y= (int) (rotateCenter.y+Math.sin(arc)*rotateRadius);
        }
    }

    public double getDearc() {
        return dearc;
    }

    public void setDearc(double dearc) {
        this.dearc = dearc;
    }

    public double getDeradius() {
        return deradius;
    }

    public void setDeradius(double deradius) {
        this.deradius = deradius;
    }

    public double getDeY() {
        return deY;
    }

    public void setDeY(double deY) {
        this.deY = deY;
    }

    public double getDeX() {
        return deX;
    }

    public void setDeX(double deX) {
        this.deX = deX;
    }

    public double getArc() {
        return arc;
    }

    public void setArc(double arc) {
        this.arc = arc;
    }

    public boolean isStartmove() {
        return startmove;
    }

    public void setStartmove(boolean startmove) {
        this.startmove = startmove;
    }
}
