package com.zht.communication.bean;

/**
* 用来进行请求参数的创建
* @author zhanghaitao
* created at 2019/2/19  11:14
*/
public class RequestParameter {
    private String requestParameterClassName;
    private String requestParameterValue;

    public RequestParameter(String requestParameterClassName, String requestParameterValue) {
        this.requestParameterClassName = requestParameterClassName;
        this.requestParameterValue = requestParameterValue;
    }

    public String getRequestParameterClassName() {
        return requestParameterClassName;
    }

    public void setRequestParameterClassName(String requestParameterClassName) {
        this.requestParameterClassName = requestParameterClassName;
    }

    public String getRequestParameterValue() {
        return requestParameterValue;
    }

    public void setRequestParameterValue(String requestParameterValue) {
        this.requestParameterValue = requestParameterValue;
    }
}
