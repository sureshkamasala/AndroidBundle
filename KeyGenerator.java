package com.ons.abundle;

import java.util.Random;

public class KeyGenerator {
    static String charArr = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    protected synchronized static String generateKey() {
        String res = "";
        for (int i = 0; i < 6; i++) {
            Random rand = new Random();
            int index = rand.nextInt(62);
            res += charArr.charAt(index);
        }
        return res;
    }

}
