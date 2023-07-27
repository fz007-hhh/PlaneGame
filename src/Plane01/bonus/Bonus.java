package Plane01.bonus;

import Plane01.GameFrame;
import Plane01.pojo.FlyObject;
import Plane01.pojo.Hero;
import Plane01.utils.ImageUtils;

import java.awt.*;
import java.io.IOException;
import java.util.Random;

public class Bonus extends FlyObject {
    public Double dir;//补给品的方向
    public int speed;
    public boolean up;//向屏幕上方移动
    public boolean horizon;
    private int id;

    public Bonus(int photonum,int hx,int hy) throws IOException {
        id=photonum;
        Random random=new Random();
        img= ImageUtils.getImg("/bonus"+photonum+".png");
        w=img.getWidth();
        h=img.getHeight();
        x=hx;
        y=hy;
        speed=3;
        dir=new Integer(random.nextInt(6)-3).doubleValue();
        dir=dir==0?-1:dir;
        up=random.nextInt(10)-5>0? true: false;
        horizon=random.nextInt(10)-5>0? true: false;

    }

    @Override
    public void move() throws InterruptedException, ClassNotFoundException {
        //如果到达边界则补给品的移动方向重新变化
        if(x<=0||x+w+5>= GameFrame.Border_Horizon){
            horizon=!horizon;
        }
        if(y<=0||y+h+10>=GameFrame.Border_Vertical){
            up=!up;
        }

        x= (int) (horizon==false? x-speed/Math.sqrt(dir*dir+1) : x+speed/Math.sqrt(dir*dir+1));
        y=up==false? (int) (y+speed/Math.sqrt(dir*dir+1)*Math.abs(dir)):
                (int) (y-speed/Math.sqrt(dir*dir+1)*Math.abs(dir));
    }

    public boolean BonusIsCatched(Hero hero){
        Rectangle hrect=new Rectangle(hero.x+48,hero.y+52,20,25);
        Rectangle rect=new Rectangle(x,y,w,h);
        /*利用矩形是否相交判断是否造成伤害*/
        if(RectCross(rect,hrect))return true;
        return false;
    }

    //不同的补给品具有不同的效果
    public void Effect() throws IOException {
        switch (id){
            case 1:{
                new LifeBonus(id,x,y).Effect();
                break;
            }
            case 2:{
                new BloodBonus(id,x,y).Effect();
                break;
            }
            case 3:{
                new AttackBonus(id,x,y).Effect();
                break;
            }
            default:{
                new DefenseBonus(id,x,y).Effect();
                break;
            }
        }
    };
}
