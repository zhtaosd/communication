package com.zht.http.network;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

public interface HttpRequest  extends  Header{
    HttpMethod getMethod();
    URI getUri();
    OutputStream getBody();
    HttpResponse execute() throws IOException;
}
