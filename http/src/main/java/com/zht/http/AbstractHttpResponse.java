package com.zht.http;

import com.zht.http.network.HttpResponse;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

public abstract class AbstractHttpResponse implements HttpResponse {
    private static final String GZIP = "gzip";
    private InputStream mGzipInputStream;

    @Override
    public void close() {
        if(mGzipInputStream!=null){
            try {
                mGzipInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        closeInternal();
    }

    @Override
    public InputStream getBody() throws IOException {
        InputStream body = getBodyInternal();
        if(isGzip()){
            return getBodyGzip(body);
        }
        return body;
    }

    private InputStream getBodyGzip(InputStream body) throws IOException {
        if(this.mGzipInputStream == null){
            this.mGzipInputStream = new GZIPInputStream(body);
        }
        return mGzipInputStream;
    }

    protected  boolean isGzip(){
        String contentEncoding = getheaders().getContentEncoding();
        if(GZIP.equals(contentEncoding)){
            return true;
        }
        return false;
    }

    protected abstract InputStream getBodyInternal();

    protected abstract void closeInternal();
}
