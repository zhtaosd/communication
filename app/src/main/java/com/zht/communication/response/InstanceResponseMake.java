package com.zht.communication.response;

import com.zht.communication.bean.RequestBean;
import com.zht.communication.bean.RequestParameter;

import java.lang.reflect.Method;

public class InstanceResponseMake extends ResponseMake {
    private Method method;
    @Override
    protected Object invokeMethod() {
        return null;
    }

    @Override
    protected void setMethod(RequestBean requestBean) {
        RequestParameter[] requestParameters = requestBean.getRequestParameter();

    }
}
