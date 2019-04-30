package com.zht.http.network;

import java.util.Map;

public interface NameValueMap {
    String get(String name);
    void set(String name,String value);
    void setAll(Map<String,String> map);
}
