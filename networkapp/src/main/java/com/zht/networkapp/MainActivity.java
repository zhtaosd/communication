package com.zht.networkapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zht.fielddownload.file.FileStorageManager;
import com.zht.fielddownload.utils.Logger;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        File file = FileStorageManager.getInstance().getFileName("http://www.imooc.com");
        Logger.debug("nate", "file path = " + file.getAbsoluteFile());
    }
}
