package com.zht.http;


import com.zht.http.network.HttpMethod;
import com.zht.http.network.HttpRequest;

import java.io.IOException;
import java.net.URI;

public interface HttpRequestFactory {
    HttpRequest createHttpRequest(URI uri, HttpMethod method) throws IOException;
}
