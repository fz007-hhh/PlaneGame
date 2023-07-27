package Plane01.bonus;

import Plane01.GamePanel;
import Plane01.pojo.Fire;
import Plane01.utils.ImageUtils;

import java.io.IOException;

public class AttackBonus extends Bonus {
    public AttackBonus(int photonum, int hx, int hy) throws IOException {
        super(photonum, hx, hy);
    }

    @Override
    public void Effect() throws IOException {
        //子弹攻击力翻倍，外形发生变化，一直持续到角色死亡
        Fire.levelup=true;
        for(int i=0;i< GamePanel.fs.size();i++)
            GamePanel.fs.get(i).img= ImageUtils.getImg("/fire2.png");
    }
}
