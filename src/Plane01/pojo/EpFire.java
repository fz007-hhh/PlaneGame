package Plane01.pojo;

import Plane01.GamePanel;
import Plane01.utils.ImageUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EpFire extends FlyObject {
    public static final int Aim=1;//沿着dir方向瞄准射击
    public static final int Straight=2;//直接向下方向射击
    public static final int Missile=3;//发射导弹，导弹先瞄准再发射，导弹的瞄准会消耗短暂时间
    public int id;
    public List<BufferedImage> imgs;//使用多张图片，给部分子弹添加闪烁效果
    public int imgsnum=0;
    public Double dir;//子弹发射的方向
    public int maxFloatX;//发射前最后的x位
    public int constX;//初始x位
    int firewalk;//子弹移动方式

    public EpFire(int photonum,int hx,int hy,int firewalk) throws IOException {
        imgs=new ArrayList<>();
        this.id=photonum;
        this.speed=9;
        img= ImageUtils.getImg("/epfire"+photonum+".png");
        w=img.getWidth();
        h=img.getHeight();
        if(photonum>=4){
            if(photonum>=8){
                imgs.add(img.getSubimage(0,0,w/2,h));
                imgs.add(img.getSubimage(w/2,0,w/2,h));
                h/=2;
            }
            else {
                imgs.add(img.getSubimage(0,0,w/2,h));
                imgs.add(img.getSubimage(w/2,0,w/2,h));
            }
            w/=4;
        }
        else imgs.add(img);
        x=hx;
        y=hy;
        constX=hx;
        /*dir为方向，直接瞄准飞机中心点*/
        this.firewalk=firewalk;
        try {
            //用敌机子弹和主机的中心位置计算方向，提高精确度
            dir= (y+h/2- GamePanel.hero.y-65)*1.0/(x+w/2-GamePanel.hero.x-58);
        } catch (Exception e) {
            dir=Double.MAX_VALUE;
        }

    }

    public EpFire(int photonum,int hx,int hy,int firewalk,int speed) throws IOException {
        this(photonum,hx,hy,firewalk);
        this.speed=speed;
    }

    public EpFire(int photonum,int hx, int hy, Double dir,int firewalk) throws IOException {
        this(photonum,hx,hy,firewalk);
        this.dir=dir;
    }

    public EpFire(int photonum,int hx, int hy, Double dir,int firewalk,int speed) throws IOException {
        this(photonum,hx,hy,dir,firewalk);
        this.speed= speed;
    }

    @Override
    public void move() {
        /*sqrt(x^2+(kx)^2) = distance =8
        * x-=8/sqrt(k^2+1)
        * y+=8k/sqrt(k^2+1)
        *
        * 子弹瞄准方式：
        * 1：沿所设方向发射
        * 2：直接向下发射
        * 3. 先移动少许距离再瞄准发射(导弹、激光束专属)*/
        switch (firewalk) {
            case 1:{
                if(dir==Double.MAX_VALUE){y+=speed;}
                else{
                    if(dir<=0){x-=speed/Math.sqrt(dir*dir+1);y+=speed/Math.sqrt(dir*dir+1)*Math.abs(dir);}
                    else{x+=speed/Math.sqrt(dir*dir+1);y+=speed/Math.sqrt(dir*dir+1)*dir;}
                }
                break;
            }
            case 2:{ y += speed; break;}
            case 3:{
                /*考虑到(maxFloatX-constX)/15有时为0，导致子弹无法移动，这里判定为：
                 * 只要x-maxFloatX的绝对值小于或等于(maxFloatX-constX)/15，
                 * 下一帧就是maxFloatX位，从而防止子弹不移动*/
                int cx=x;
                x=Math.abs(x-maxFloatX)<=Math.abs(maxFloatX-constX)/15? maxFloatX:
                        x+(maxFloatX-constX)/15;
                if(x==maxFloatX) {
                    try {
                        //用敌机子弹和主机的中心位置计算方向，提高精确度
                        dir= (y+h/2- GamePanel.hero.y-65)*1.0/(x+w/2-GamePanel.hero.x-58);
                    } catch (Exception e) {
                        dir=Double.MAX_VALUE;
                    }
                    this.firewalk = 1;
                    move();
                }
                break;
            }
        }
    }

    public boolean HavAttack(Hero hero){
        Rectangle hrect=new Rectangle(hero.x+48,hero.y+52,20,25);
        Rectangle rect=new Rectangle(x,y,w,h);

        /*利用矩形是否相交判断是否造成伤害*/
        if(RectCross(rect,hrect))return true;
        return false;
    }
}
