package com.zht.http;

import com.zht.http.network.HttpHeader;
import com.zht.http.network.HttpStatus;

import java.io.InputStream;

import okhttp3.Response;

public class OkHttpResponse extends AbstractHttpResponse {
    private Response mResponse;
    private HttpHeader mHeaders;

    public OkHttpResponse(Response mResponse) {
        this.mResponse = mResponse;
    }

    @Override
    protected InputStream getBodyInternal() {
        return mResponse.body().byteStream();
    }

    @Override
    protected void closeInternal() {
        mResponse.body().close();
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.getValue(mResponse.code());
    }

    @Override
    public String getStatusMsg() {
        return mResponse.message();
    }

    @Override
    public long getContentlength() {
        return mResponse.body().contentLength();
    }

    @Override
    public HttpHeader getheaders() {
        if(mHeaders == null){
            mHeaders = new HttpHeader();
        }
        for (String name : mResponse.headers().names()) {
            mHeaders.set(name,mResponse.headers().get(name));
        }
        return mHeaders;
    }
}
