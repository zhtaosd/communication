package com.zht.http;

import com.zht.http.network.HttpHeader;
import com.zht.http.network.HttpResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public abstract class BufferHttpRequest extends AbstractHttpRequest{
    private ByteArrayOutputStream mByteArray = new ByteArrayOutputStream();
    
    protected OutputStream getBodyOutputStream() {
        return mByteArray;
    }
    
    protected HttpResponse executeInternal(HttpHeader mHeader) throws IOException {
        byte[] data = mByteArray.toByteArray();
        return executeInternal(mHeader,data);
    }

    protected abstract HttpResponse executeInternal(HttpHeader mHeader, byte[] data) throws IOException;
}
