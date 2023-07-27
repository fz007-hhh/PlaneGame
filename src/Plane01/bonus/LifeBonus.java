package Plane01.bonus;

import Plane01.GamePanel;

import java.io.IOException;

public class LifeBonus extends Bonus {
    public LifeBonus(int photonum, int hx, int hy) throws IOException {
        super(photonum, hx, hy);
    }

    @Override
    public void Effect() {
        /*生命最多储存4条*/
        GamePanel.hero.lifenum=GamePanel.hero.lifenum+1>4?4:GamePanel.hero.lifenum+1;
    }
}
