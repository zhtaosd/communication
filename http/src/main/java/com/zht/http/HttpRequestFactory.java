package com.zht.http;


import com.zht.http.network.HttpMethod;
import com.zht.http.network.HttpRequest;

import java.net.URI;

public interface HttpRequestFactory {
    HttpRequest createHttpRequest(URI uri, HttpMethod method);
}
