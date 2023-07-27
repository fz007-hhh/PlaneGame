package Plane01.bonus;

import Plane01.GameFrame;
import Plane01.task.ShiledThread;

import java.io.IOException;

public class DefenseBonus extends Bonus {

    public DefenseBonus(int photonum, int hx, int hy) throws IOException {
        super(photonum, hx, hy);
    }

    @Override
    public void Effect() {
        new Thread(new ShiledThread(GameFrame.panel)).start();
    }
}
