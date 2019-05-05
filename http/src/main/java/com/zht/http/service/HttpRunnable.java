package com.zht.http.service;

import com.zht.http.network.HttpRequest;
import com.zht.http.network.HttpResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import okhttp3.Response;

public class HttpRunnable implements Runnable {
    private HttpRequest httpRequest;
    private MoocRequest moocRequest;
    private WorkStation workStation;

    public HttpRunnable(HttpRequest httpRequest, MoocRequest moocRequest, WorkStation workStation) {
        this.httpRequest = httpRequest;
        this.moocRequest = moocRequest;
        this.workStation = workStation;
    }

    @Override
    public void run() {
        try {
            httpRequest.getBody().write(moocRequest.getData());
            HttpResponse response = httpRequest.execute();
            String cotentType = response.getheaders().getContentType();
            moocRequest.setContentType(cotentType);
            if (response.getStatus().isSuccess()) {
                if (moocRequest.getResponse() != null) {
                    moocRequest.getResponse().success(moocRequest, new String(getData(response)));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            workStation.finish(moocRequest);
        }
    }

    private byte[] getData(HttpResponse response) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream((int) response.getContentlength());
        int len;
        byte[] data = new byte[512];
        try {
            while ((len = response.getBody().read(data)) != -1) {
                outputStream.write(data, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputStream.toByteArray();
    }
}
