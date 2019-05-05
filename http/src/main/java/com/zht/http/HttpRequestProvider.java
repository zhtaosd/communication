package com.zht.http;

import com.zht.http.network.HttpMethod;
import com.zht.http.network.HttpRequest;
import com.zht.http.utils.Utils;

import java.net.URI;

public class HttpRequestProvider {
    private static boolean OKHTTP_REQUEST = Utils.isExists("okhttp3.OkHttpClient", HttpRequestProvider.class.getClassLoader());

    private HttpRequestFactory httpRequestFactory;

    public HttpRequestProvider() {
       if(OKHTTP_REQUEST){
           httpRequestFactory = new OkHttpRequestFactory();
       }
    }

    public HttpRequest getHttpRequest(URI uri, HttpMethod method){
        return httpRequestFactory.createHttpRequest(uri,method);
    }

    public HttpRequestFactory getHttpRequestFactory() {
        return httpRequestFactory;
    }

    public void setHttpRequestFactory(HttpRequestFactory httpRequestFactory) {
        this.httpRequestFactory = httpRequestFactory;
    }
}
