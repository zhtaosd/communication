package com.zht.http.service;

import com.zht.http.service.convert.Convert;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class WrapperResponse extends MoocResponse<String>{
    private MoocResponse moocResponse;
    private List<Convert> converts;

    public WrapperResponse(MoocResponse response, List<Convert> converts) {
        this.moocResponse = response;
        this.converts = converts;
    }

    @Override
    public void success(MoocRequest request, String data) {
        for (Convert convert : converts) {
            if(convert.isCanParse(request.getContentType())){
                Object o = convert.parse(data,getType());
                moocResponse.success(request,o);
                return;
            }
        }

    }

    private Type getType() {
        Type type = moocResponse.getClass().getGenericSuperclass();
        Type[] paramType = ((ParameterizedType)type).getActualTypeArguments();
        return paramType[0];
    }

    @Override
    public void fail(int errorCode, String errorMsg) {

    }
}
