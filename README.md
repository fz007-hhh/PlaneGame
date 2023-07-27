# Planegame

使用java多线程+GUI编写的飞机大战游戏，自创了五个Boss



### 游戏简述

游戏总共分为5关，其中前1-4关为常规关卡，最后一关为boss rush，即把前四关的boss都打一遍才能遇到最终boss

由于游戏敌人的子弹比较密集，所以==主机的伤害判定点只有核心区域！！==

游戏的普通敌人种类主要有以下几种：

其中，第三行敌人为精英怪，血量相比前两行要更厚。Boss战之前会出现两个 ep08敌人（小型直升机），打爆可以随机获得补给道具，本身没有攻击能力

![image-20230728003718519](https://typora-aliyun01.oss-cn-hangzhou.aliyuncs.com/img/image-20230728003718519.png)





### Boss说明

Boss技能均单独编写一个线程，技能发动方式比较简单，是每个一段时间随机发动

Boss战之前游戏会随机生成两个补给道具（攻击翻倍、加血、生命、护盾），吃到道具后会有对应增益效果

击败Boss后直接加5000分（当然加分暂时还没什么用。。）



### 运行结果：

![image](https://github.com/fz007-hhh/Planegame/assets/69685307/8fe6e147-d235-448d-9571-aafe8bbe0c8a)
![image](https://github.com/fz007-hhh/Planegame/assets/69685307/c9a79ed4-98cb-4db2-b673-549895237f1a)

![image-20230728004347819](https://typora-aliyun01.oss-cn-hangzhou.aliyuncs.com/img/image-20230728004347819.png)

![image-20230728004131255](https://typora-aliyun01.oss-cn-hangzhou.aliyuncs.com/img/image-20230728004131255.png)
