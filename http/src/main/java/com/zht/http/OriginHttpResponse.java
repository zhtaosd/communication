package com.zht.http;

import com.zht.http.network.HttpHeader;
import com.zht.http.network.HttpStatus;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

public class OriginHttpResponse extends AbstractHttpResponse{

    private HttpURLConnection connection;

    public OriginHttpResponse(HttpURLConnection connection) {
        this.connection = connection;
    }

    @Override
    protected InputStream getBodyInternal() throws IOException {
        return connection.getInputStream();
    }

    @Override
    protected void closeInternal() {
        connection.disconnect();
    }

    @Override
    public HttpStatus getStatus(){
        try {
            return HttpStatus.getValue(connection.getResponseCode());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getStatusMsg() {
        try {
            return connection.getResponseMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public long getContentlength() {
        return connection.getContentLength();
    }

    @Override
    public HttpHeader getheaders() {
        HttpHeader header = new HttpHeader();
        for (Map.Entry<String, List<String>> entry : connection.getHeaderFields().entrySet()) {
            header.set(entry.getKey(),entry.getValue().get(0));
        }
        return header;
    }
}
