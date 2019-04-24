package com.zht.networkapp;

import android.app.Application;

import com.zht.fielddownload.file.FileStorageManager;
import com.zht.fielddownload.utils.Logger;

import java.io.File;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FileStorageManager.getInstance().init(this);
    }
}
