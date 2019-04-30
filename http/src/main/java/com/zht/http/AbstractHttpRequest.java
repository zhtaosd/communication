package com.zht.http;

import com.zht.http.network.HttpHeader;
import com.zht.http.network.HttpRequest;
import com.zht.http.network.HttpResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipOutputStream;

public abstract class AbstractHttpRequest implements HttpRequest {
    private static final String GZIP = "gzip";
    private HttpHeader mHeader = new HttpHeader();
    private ZipOutputStream mZip;
    private boolean executed;

    @Override
    public HttpHeader getheaders() {
        return mHeader;
    }

    @Override
    public OutputStream getBody() {
        OutputStream body = getBodyOutputStream();
        if(isGzip()){
            return getGzipOutputStream(body);
        }
        return body;
    }

    private OutputStream getGzipOutputStream(OutputStream body) {
        if(this.mZip == null){
            return new ZipOutputStream(body);
        }
        return mZip;
    }

    protected  boolean isGzip(){
        String contentEncoding = getheaders().getContentEncoding();
        if(GZIP.equals(contentEncoding)){
            return true;
        }
        return false;
    }

    @Override
    public HttpResponse execute() throws IOException {
        if(mZip != null){
            mZip.close();
        }
        HttpResponse response = executeInternal(mHeader);
        executed = true;
        return response;
    }

    protected abstract HttpResponse executeInternal(HttpHeader mHeader);

    protected abstract OutputStream getBodyOutputStream();
}
