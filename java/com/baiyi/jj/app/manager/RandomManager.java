package com.baiyi.jj.app.manager;

import java.util.Random;

/**
 * Created by Administrator on 2017/4/11 0011.
 */
public class RandomManager {


    public static RandomManager instance;
    public static Random random;

    public RandomManager() {
        random = new Random();
    }

    public static RandomManager getInstance()
    {
        if(instance == null)
        {
            instance = new RandomManager();
        }
        return instance;
    }

    public int getRandomNum(){
        return random.nextInt(9);
    }

}
