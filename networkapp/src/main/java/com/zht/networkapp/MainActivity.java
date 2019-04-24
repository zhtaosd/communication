package com.zht.networkapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.zht.fielddownload.file.FileStorageManager;
import com.zht.fielddownload.http.DownloadCallback;
import com.zht.fielddownload.http.HttpManager;
import com.zht.fielddownload.utils.Logger;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        File file = FileStorageManager.getInstance().getFileName("http://www.imooc.com");
        Logger.debug("nate", "file path = " + file.getAbsoluteFile());

        iv = findViewById(R.id.imageView);
        HttpManager.getInstance().AsyncRequest("https://img.mukewang.com/szimg/5a37579800019dc405400300-280-160.jpg", new DownloadCallback() {
            @Override
            public void success(File file) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath());
                        iv.setImageBitmap(bm);
                    }
                });

            }

            @Override
            public void fail(int errorcode, String errorMessage) {

            }

            @Override
            public void progress(int progress) {

            }
        });
    }
}
