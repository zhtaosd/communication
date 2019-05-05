package com.zht.http.service;

import android.view.accessibility.AccessibilityRequestPreparer;

import com.zht.http.network.HttpMethod;
import com.zht.http.service.convert.Convert;
import com.zht.http.service.convert.JsonConvert;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MoocApiProvider {
    private static final String ENCODING = "utf-8";
    private static WorkStation workStation = new WorkStation();
    private static final List<Convert> converts = new ArrayList();

    static {
        converts.add(new JsonConvert());
    }

    public static byte[] encodeParam(Map<String, String> value) {
        if (value == null || value.size() == 0) {
            return null;
        }
        StringBuffer buffer = new StringBuffer();
        int count = 0;
        try{
            for (Map.Entry<String, String> entry : value.entrySet()) {
                buffer.append(URLEncoder.encode(entry.getKey(), ENCODING)).append("=").
                        append(URLEncoder.encode(entry.getValue(), ENCODING));
                if (count != value.size() - 1) {
                    buffer.append("&");
                }
                count++;
            }
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return buffer.toString().getBytes();
    }

    public static void helloWorld(String url,Map<String,String> values, MoocResponse response){
        MoocRequest request = new MoocRequest();
        WrapperResponse wrapperResponse = new WrapperResponse(response,converts);
        request.setUrl(url);
        request.setMethod(HttpMethod.POST);
        request.setData(encodeParam(values));
        request.setResponse(wrapperResponse);
        workStation.add(request);
    }
}
