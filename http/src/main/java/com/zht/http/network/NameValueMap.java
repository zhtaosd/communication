package com.zht.http.network;

import java.util.Map;

public interface NameValueMap extends Map{
    String get(String name);
    void set(String name,String value);
    void setAll(Map<String,String> map);
}
