package Plane01.pojo;

import Plane01.utils.ImageUtils;

import java.io.IOException;

public class Fire extends FlyObject {
    public static boolean levelup=false;
   public int dir;
   public int attack;
    //子弹当前移动方向，dir=0 左上角；=1 垂直向上；=2 右上角

    public Fire(int hx,int hy,int dir) throws IOException {
        if(levelup==false){
            img= ImageUtils.getImg("/fire.png");
            attack=1;
            w=img.getWidth();
            h=img.getHeight();
        }
        else{
            img= ImageUtils.getImg("/fire2.png");
            attack=2;
            w=img.getWidth();
            h=img.getHeight();
        }

        x=hx;
        y=hy;
        this.dir=dir;
    }

    public void move() {
        if(dir==0){
            x-=2.5;
            y-=13;
        }
        else if(dir==1){
            y-=13;
        }
        else{
            x+=2.5;
            y-=13;
        }
    }
}
