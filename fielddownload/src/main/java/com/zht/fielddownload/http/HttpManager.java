package com.zht.fielddownload.http;

import android.content.Context;

import com.zht.fielddownload.file.FileStorageManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ResourceBundle;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpManager {
    private static HttpManager INSTANCE = new HttpManager();
    private Context context;
    private OkHttpClient client;
    private static final int NETWORK_ERROR_CODE = 1;
    public static final int CONTENT_LENGTH_ERROR_CODE = 2;
    public static final int TASK_RUNNING_ERROR_CODE = 3;

    private HttpManager() {
        client = new OkHttpClient();
    }

    public static HttpManager getInstance() {
        return INSTANCE;
    }

    public void init(Context context) {
        this.context = context;
    }

    public Response syncRequest(String url) {
        Request request = new Request.Builder().url(url).build();
        try {
            return client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Response syncRequestByRange(String url, long start, long end) {
        Request request = new Request.Builder().
                url(url).
                addHeader("Range", "bytes=" + "start" + "-" + end).
                build();
        try {
            return client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void AsyncRequest(final String url,final DownloadCallback callback) {
        Request request = new Request.Builder().
                url(url).
                build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(!response.isSuccessful()&&callback!=null){
                    callback.fail(NETWORK_ERROR_CODE,"请求错误");
                }
                File file = FileStorageManager.getInstance().getFileName(url);
                byte[] buffer = new byte[1024*500];
                int len;
                FileOutputStream fos = new FileOutputStream(file);
                InputStream is = response.body().byteStream();
                while((len = is.read(buffer,0,buffer.length))!=-1){
                    fos.write(buffer,0,len);
                    fos.flush();
                }
                callback.success(file);
            }
        });
    }
}
