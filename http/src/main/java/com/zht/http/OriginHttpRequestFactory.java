package com.zht.http;

import com.zht.http.network.HttpMethod;
import com.zht.http.network.HttpRequest;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;

public class OriginHttpRequestFactory implements HttpRequestFactory {
    private HttpURLConnection connection;

    public OriginHttpRequestFactory() {
    }

    @Override
    public HttpRequest createHttpRequest(URI uri, HttpMethod method) throws IOException {
        connection = (HttpURLConnection) uri.toURL().openConnection();
        return new OriginHttpRequest(connection,uri.toString(),method);
    }
}
