package com.zht.http;

import com.zht.http.network.HttpHeader;
import com.zht.http.network.HttpMethod;
import com.zht.http.network.HttpResponse;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpRequest extends BufferHttpRequest {
    private OkHttpClient mClient;
    private HttpMethod mMethod;
    private String mUrl;

    public OkHttpRequest(OkHttpClient mClient, HttpMethod mMethod, String mUrl) {
        this.mClient = mClient;
        this.mMethod = mMethod;
        this.mUrl = mUrl;
    }

    @Override
    protected HttpResponse executeInternal(HttpHeader mHeader, byte[] data) throws IOException {
        boolean isBody = mMethod == HttpMethod.POST;
        RequestBody requestBody = null;
        if(isBody){
            requestBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), data);
        }
        Request.Builder builder = new Request.Builder().url(mUrl).method(mMethod.name(),requestBody);
        for (Map.Entry<String, String> entry : mHeader.entrySet()) {
            builder.addHeader(entry.getKey(),entry.getValue());
        }
        Response response = mClient.newCall(builder.build()).execute();
        return new OkHttpResponse(response);
    }

    @Override
    public HttpMethod getMethod() {
        return mMethod;
    }

    @Override
    public URI getUri() {
        return URI.create(mUrl);
    }
}
