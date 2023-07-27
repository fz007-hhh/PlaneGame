package test;

import Plane01.utils.ImageUtils;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MyTeskTest {
    public static void main(String[] args) throws IOException {
        BufferedImage image= ImageIO.read(new File("D:\\CloudMusic\\blood.bmp"));
        ImageUtils.TransPicture(image);
    }
}
