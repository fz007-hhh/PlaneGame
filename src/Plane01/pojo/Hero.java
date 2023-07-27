package Plane01.pojo;

import Plane01.utils.ImageUtils;

import java.io.IOException;

public class Hero extends  FlyObject{
    public int blood;
    public int lifenum;

    public  Hero() throws IOException  {
        img= ImageUtils.getImg("/hero.png");
        //使用横纵坐标，确定英雄机在游戏开始时的位置
        x=200;
        y=500;
        w= img.getWidth();
        h=img.getHeight();
        lifenum=3;
        blood =15;
    }

    public void moveToMouse(int mx,int my){
        x=mx-w/2;
        y=my-h/2;
    }

    @Override
    public void move() {

    }
}
