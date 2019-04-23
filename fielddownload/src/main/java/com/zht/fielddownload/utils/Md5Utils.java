package com.zht.fielddownload.utils;

import android.text.TextUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Utils {
    public static String generateCode(String url){
        if(TextUtils.isEmpty(url)){
            return null;
        }
        StringBuffer buffer = new StringBuffer();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("md5");
            messageDigest.update(url.getBytes());
            byte[] ciper = messageDigest.digest();
            for (byte b : ciper) {
                String hexStr = Integer.toHexString(b&0xff);
                buffer.append(hexStr.length()==1?"0"+hexStr:hexStr);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
