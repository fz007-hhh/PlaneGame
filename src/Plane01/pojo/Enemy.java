package Plane01.pojo;

import Plane01.GamePanel;
import Plane01.utils.ImageUtils;

import java.awt.*;
import java.io.IOException;

public class Enemy extends  FlyObject{
    /*为movenum添加final值，便于stagebuilder中的方法
    * 规定敌机的移动方式，主要包括：
    * 1.屏幕顶端向左/右沿椭圆形轨迹飞行
    * 2.屏幕中间向左/右沿椭圆形轨迹飞行
    * 3.敌机向屏幕下方移动，移动到maxFloatY之后不再移动
    * 4.敌机跟随主机的移动而移动
    * 5.(送补给敌机专属)在屏幕中间快速飞行*/
    public static final int TopRotateL=1;
    public static final int TopRotateR=2;
    public static final int CenterRotateL=3;
    public static final int CenterRotateR=4;
    public static final int ToMaxFloatY=5;
    public static final int AutoToHero=6;
    public static final int BonusMoveFromL=7;
    public static final int BonusMoveFromR=8;


    /*为攻击类型添加final值
    * 攻击类型主要包括：
    * 1.敌机左/右翼发射一发子弹
    * 2.敌机发射大范围子弹，子弹的数量和轨迹可以自定义
    * 3.敌机发射导弹，导弹自带自瞄，可以自定义发射数量和发射延迟时间*/
    public static final int ShootOneL=1;
    public static final int ShootMany=2;
    public static final int ShootMissle=3;
    public static final int ShootOneR=4;

    public int blood;//敌机血量
    public int maxblood;
    public int deblood;//考虑到敌机有大有小，增加碰撞伤害属性
    public boolean isBoss=false;//是否是boss
    public boolean isBonus=false;//是否是带有补给品的敌机
    private int movenum;//移动方式
    public int maxFloatY;//敌机的Y值最大值为maxFloatY
    private static int[]x1;//不同移动方式的横坐标初始化位置
    private static int[]y1;//不同移动方式的纵坐标初始化位置
    private Rectangle[]rects=new Rectangle[5];

    public Enemy(int photonum,int speed,int movenum,int maxFloatY) throws IOException {
        this(photonum,speed,movenum);
        this.maxFloatY=maxFloatY;
    }

    public Enemy(int photonum,int speed,int movenum) throws IOException {
        img= ImageUtils.getImg("/ep" +photonum/10+photonum%10+".png");
      /* 改变图片颜色
       InvertFilter filter= new InvertFilter();
        img=filter.filter(img,null);
        ContrastFilter filter1=new ContrastFilter();
        filter1.setContrast(0.6f);
        img=filter1.filter(img,null);*/
        w=img.getWidth();
        h=img.getHeight();
        x1=new int[]{0,410,0,-w+10,502+w,150,150,-30,542};
        y1=new int[]{0,-20,-20, (int) (300-2.5*w),(int) (300-2.5*w),-20,-h,200,200};
        if(photonum==8)isBonus=true;//第8张图片就是带有补给品的敌机专属图片
        x=x1[movenum];
        y=y1[movenum];
        blood=5+photonum;
        this.speed=speed;
        this.movenum=movenum;
        this.maxFloatY=200-this.w;
        this.deblood=photonum+3;
        maxblood=blood;
    }

    public void move() throws InterruptedException, ClassNotFoundException {
        /*r2-(r-128)2=400*400  256r=160000+128*128
        * r=(0,-561)=689
        * x^2+(y+561)^2=689^2
        *
        * r=(512,-561)
        * (x-512)^2+(y+561)^2=689^2*/
        switch (movenum){
          case 1:{
              /*敌机在屏幕顶端向左旋转*/
              x-=speed;
              y=(int)Math.sqrt(689*689-x*x)-561+30;
              break;
          }
          case 2:{
              /*敌机在屏幕顶端向右旋转*/
              x+=speed;
              y=(int)Math.sqrt(689*689-(x-512)*(x-512))-561+30;
              break;
          }/*
          (-w,300)  a=3.5w,b=2.5w
          (x+w)2/(3.5w)^2+(y-300)2/(2.5w)^2=1
          (512+w,300) a=4.5w,b=2.5w
          */
            case 3:{
                /*敌机在屏幕中间向右旋转*/
                y+=speed;
                int k=y-300;
                x= (int) Math.sqrt(3.5*3.5*w*w-k*k*(3.5*3.5)/(2.5*2.5))-w;
                break;
            }
            case 4: {
                /*敌机在屏幕顶端向左旋转*/
                y+=speed;
                int k=y-300;
                x= -(int) Math.sqrt(4.5*4.5*w*w-k*k*(4.5*4.5)/(2.5*2.5))+512+w;
                break;
            }
            case 5:{
                /*敌机直接向屏幕下方移动，当Y值达到maxFloatY时停止移动*/
                y= y<maxFloatY?y+speed:y;
                break;
            }
            case 6:{
                /*敌机随着主机的移动而移动
                * 考虑到x+-speed有时始终会和hero.x有出入，这里判定为：
                * 只要敌机和hero的x的绝对值小于speed，下一帧就是hero.x位，防止敌机晃动*/
                y= y<maxFloatY?y+speed:y;
                x= Math.abs(x+w/2-GamePanel.hero.x-61)<=speed? GamePanel.hero.x+61-w/2 : (x>GamePanel.hero.x+61-w/2?x-speed:x+speed);
                break;
            }
            case 7:{
                /*(0,-952)  r=1152
                * (-30,200)*/
                x+=speed;
                y=(int)Math.sqrt(1152*1152-x*x)-952-60;
                break;
            }
            case 8:{
                /*(512,-952) r=1152
                * (512+30,200)*/
                x-=speed;
                y=(int)Math.sqrt(1152*1152-(x-512)*(x-512))-952-60;
                break;
            }
            default:{
                throw new ClassNotFoundException("没有找到相应的移动方法!");
            }
       }
    }

    public void GetHurtFiled(){
        rects[0]=new Rectangle(x,y+h/2,w/4,h/6);
        rects[1]=new Rectangle(x+w/4,y+h/2,w/6,h/2-10);
        rects[2]=new Rectangle(x+5*w/12,y,w/6,h-10);
        rects[3]=new Rectangle(x+7*w/12,y+h/2,w/6,h/2-10);
        rects[4]=new Rectangle(x+3*w/4,y+h/2,w/4,h/6);
    }/*将敌机简化成多个小矩形,以便更加精密的伤害检测*/

    public boolean iSshootedBy(Fire f) {
        GetHurtFiled();
        Rectangle hrect=new Rectangle(f.x,f.y,f.w,f.h);
        //英雄机的判定范围
        for(Rectangle rectangle:rects){
            if(RectCross(hrect,rectangle)){
                return true;
            }
        }
        return false;
    }//子弹击中判定

    public boolean isHitBy(Hero hero) {
        GetHurtFiled();
        Rectangle hrect=new Rectangle(hero.x+51,hero.y+56,20,25);
        //英雄机的判定范围
        for(Rectangle rectangle:rects){
            if(RectCross(hrect,rectangle)){
                return true;
            }
        }
        return false;
    }

    //自定义多种攻击方式
    public void ShootHero(int type,int fireFly,int speed,Integer...args) throws IOException, InterruptedException {

            int photonum=args.length==0?2:args[args.length-1];
        switch (type) {
               //单个子弹
               case 1: { GamePanel.epfs.add(new EpFire(photonum,x + 25, y + 50,fireFly,speed)); break;}
               //连发、大范围攻击
               case 2:{
                   /*数组args中，首元素可以控制九连发子弹的x偏移量,
                   第二个元素可以控制子弹的数量
                   * 最后一个元素控制子弹的图片*/
                  bigFieldAttack(photonum,fireFly,speed,args[0],args[1]);
                   break;
               }
               //两侧各发射args[0]发导弹，并进行跟踪
               case 3:{
                   int n=args.length<1?2:args[0];
                   int relay=args.length>1?args[1]:0;
                   for(int i=-n;i<=n;i++){
                       if(i==0)continue;
                       EpFire fire=new EpFire(3,this.x+this.w/2+20*i,this.y-this.h/2-5,fireFly,speed);
                       fire.maxFloatX=fire.constX+i*35;
                       GamePanel.epfs.add(fire);
                       Thread.sleep(relay);
                   }
                   break;
               }
              case 4:{
                  GamePanel.epfs.add(new EpFire(photonum,x+w-25, y + 50,fireFly,speed)); break;
              }
               default:{}
           }
    }


    public void bigFieldAttack(int photonum, int fireFly, int speed, int floatx,int firenum) throws IOException {
        Point p1=new Point(x+w/2,y+h/2);
        int []a1=new int[firenum+1];
        int n=0;
        for(int i=0;i<=firenum/2;i++)
            a1[n++]=i;

        for(int i=firenum/2-1;i>=0;i--){
            a1[n++]=i;
        }

        for(int i=0;i<a1.length;i++){
            if(this==null){
                System.out.println("kkk");
                break;
            }
            Point p2=new Point(x+i*w/(a1.length-1)+floatx,y+h+5+5*a1[i]);
            double dir=(p2.x==p1.x?Double.MAX_VALUE:(p2.y-p1.y)*1.0/(p2.x-p1.x));
            GamePanel.epfs.add(new EpFire(photonum,p2.x,p2.y,dir,fireFly,speed));
        }
    }
}
