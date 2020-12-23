package com.example.ourfirst.util;

//（工具类）判断传入的参数是否为空或者长度<=0   因为很多地方都会用到所以封装起来！
public class StringUtils {
    public static boolean isEmpty(String str) {
        if (str == null || str.length() <= 0) {
            return true;
        } else {
            return false;
        }
    }
}
