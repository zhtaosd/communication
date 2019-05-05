package com.zht.http.utils;

public class Utils {
    public static boolean isExists(String className,ClassLoader loader){
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
       return false;
    }
}
