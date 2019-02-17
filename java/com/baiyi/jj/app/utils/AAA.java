package com.baiyi.jj.app.utils;

/**
 * Created by Administrator on 2017/12/4.
 */

public class AAA {

    public static void main(String args[]) {

        System.out.print("ggg" + shareApple(3, 2) + "e");
    }

    static int shareApple(int apple, int basket) {
        if (apple == 0 || basket == 1) {
            return 1;
        } else if (apple < basket) {
            return shareApple(apple, apple);
        } else {
            return shareApple(apple, basket - 1) + shareApple(apple - basket, basket);
        }
    }
}
