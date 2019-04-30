package com.zht.http.network;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

public interface HttpResponse extends Header, Closeable {
     HttpStatus getStatus();
     String getStatusMsg();
     InputStream getBody() throws IOException;
     void close();
     long getContentlength();
}
