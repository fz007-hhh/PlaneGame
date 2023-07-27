package Plane01;

import javax.swing.*;
import java.io.IOException;

/*创建飞机大战的窗体框架*/
public class GameFrame extends JFrame {
    public static final int Border_Vertical=768;//框架竖直方向边界
    public static final int Border_Horizon=512;//框架横向边界
    public static GamePanel panel;

    public GameFrame(){
        setTitle("飞机大战");//标题
        setSize(Border_Horizon,Border_Vertical);

        /*设置居中显示
        * null 表示相对于屏幕左上角居中*/
        setLocationRelativeTo(null);
        //不允许改变窗口大小
        setResizable(false);
        //设置关闭窗口时控制台终止程序,否则关闭窗口时程序仍然处于执行状态
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) throws IOException {
        GameFrame g1=new GameFrame();
        GamePanel p1=new GamePanel(g1);
        panel=p1;
        p1.action();//开启线程
        g1.add(p1);
        g1.setVisible(true);
    }
}
