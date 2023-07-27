package Plane01.utils;

import Plane01.pojo.FlyObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

//处理图片的工具类
/*static特点：
1.公用的，所有对象共用
2.可以直接通过类名调用*/
public class ImageUtils {
    public static BufferedImage getImg(String path) throws IOException {
        //BufferedImage加载图片类
        //其实也可以使用IO流...
        try {
            BufferedImage img;
//            img = ImageIO.read(new File(FlyObject.imghead+path));
            img=ImageIO.read(ImageUtils.class.getResource(FlyObject.imghead+path));
            //ImageUtils.class 找到类的路径 getresource() 获取资源
            return img;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void TransPicture(BufferedImage image) throws IOException {
        int height=image.getHeight();
        int width=image.getWidth();

        ImageIcon imageIcon=new ImageIcon(image);
        //icon是图标的意思，你可以将其理解为image图片的压缩版，当你想获取image时
        //需要调用icon.getImage()
        BufferedImage image1=new BufferedImage(width,height,BufferedImage.TYPE_4BYTE_ABGR);
        //第二个图片缓冲区，我们要根据参数image画出同样尺寸的图片出来
        Graphics2D g2D= (Graphics2D) image1.getGraphics();
        g2D.drawImage(imageIcon.getImage(),0,0,null);
        //重新画图

        int alpha=0;//透明度

        for(int y=image1.getMinY();y<image1.getHeight();y++){
            for (int x=image1.getMinX();x<image1.getWidth();x++){
                int rgb=image1.getRGB(x,y);//获取此图形块的颜色码RGB
                int R=(rgb&0xff0000)>>16;
                int G=(rgb&0x00ff00)>>8;
                int B=(rgb&0x0000ff);

                if(R==255&&G==255&&B==255){
                    alpha=0;
                }
                else alpha=255;

                rgb=(alpha<<24)|(rgb&0x00ffffff);//int类型为4个字节
                image1.setRGB(x,y,rgb);
            }
        }
        g2D.drawImage(image1,0,0,null);
        ImageIO.write(image1,"png",new File("D:\\CloudMusic\\new1.bmp"));
        System.out.println("完成画图");
    }
}
/*getImg方法中注意，使用
ImageUtils.class.getResource(path);
ImageUtils.class.getclassloader().getResource(path);
可能会有两种不同的结果
原因在于，Class.getResource和ClassLoader.getResource本质上是一样的，
都是使用ClassLoader.getResource加载资源的
而JVM的规定是：
当path不以'/'开头时，默认是从此类所在的包下取资源；path以'/'开头时，则是从项目的ClassPath根下获取资源,

总结：
class.getResource("/") == class.getClassLoader().getResource("")
Class.getResource真正调用ClassLoader.getResource方法之前，会先获取文件的路径
classloader已经默认到类所在的包上了，而class只是默认到类根路径下
简单点：
class+"/" = classloader = "src/"     直接当成src/使用就行
（就是out目录下当前类所在的包，IDEA在执行java文件时，会把所有.java换成.class，
保存在out目录下，src目录下创建的类和资源转码后都会保存在out中）*/