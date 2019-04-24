package com.zht.fielddownload.file;

import android.content.Context;
import android.os.Environment;

import com.zht.fielddownload.utils.Md5Utils;

import java.io.File;
import java.io.IOException;

public class FileStorageManager {
    //采用单例的模式进行书写
    //构造方法私有化

    public FileStorageManager() {
    }

    //实例变量为静态变量
    private  static FileStorageManager INSTANCE  = new FileStorageManager();

    private Context mContex;
    //获取方法为静态方法

    public static FileStorageManager getInstance(){
        return INSTANCE;
    }

    public void init(Context context){
        this.mContex = context;
    }

    public  File getFileName(String url){
        File parent;
        //判断当前设备是否有外置存储卡
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            parent = mContex.getExternalCacheDir();
        }else {
            parent = mContex.getCacheDir();
        }
        String fileName = Md5Utils.generateCode(url);
        File file = new File(parent,fileName);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }
}

