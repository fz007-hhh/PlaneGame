package Plane01.bonus;

import Plane01.GamePanel;

import java.io.IOException;

public class BloodBonus extends Bonus {
    public BloodBonus(int photonum, int hx, int hy) throws IOException {
        super(photonum, hx, hy);
    }

    @Override
    public void Effect() {
        GamePanel.hero.blood =GamePanel.hero.blood +5>15?15:GamePanel.hero.blood +5;
    }
}
