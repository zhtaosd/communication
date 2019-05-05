package com.zht.http;

import com.zht.http.network.HttpHeader;
import com.zht.http.network.HttpMethod;
import com.zht.http.network.HttpResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.Map;

public class OriginHttpRequest extends BufferHttpRequest {
    private HttpURLConnection connection;
    private String url;
    private HttpMethod method;

    public OriginHttpRequest(HttpURLConnection connection, String url, HttpMethod method) {
        this.connection = connection;
        this.url = url;
        this.method = method;
    }

    @Override
    protected HttpResponse executeInternal(HttpHeader mHeader, byte[] data) throws IOException {
        for (Map.Entry<String, String> entry : mHeader.entrySet()) {
            connection.addRequestProperty(entry.getKey(),entry.getValue());
        }
        connection.setDoOutput(true);
        connection.setDoOutput(true);
        connection.setRequestMethod(method.name());
        connection.connect();
        if(data!=null&&data.length>0){
            OutputStream out = connection.getOutputStream();
            out.write(data,0,data.length);
            out.close();
        }
        OriginHttpResponse response = new OriginHttpResponse(connection);
        return response;
    }

    @Override
    public HttpMethod getMethod() {
        return method;
    }

    @Override
    public URI getUri() {
        return URI.create(url);
    }
}
