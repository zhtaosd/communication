package com.zht.communication.bean;

/**
* 用来做对请求的封装
* @author zhanghaitao
* created at 2019/2/19  11:17
*/
public class RequestBean {
    private String className;//请求对象的class类型
    private String resultClassName;//返回对象的class类型
    private String requestObject;
    private String methodName;//请求的方法名称
    private RequestParameter[] requestParameter; //请求参数

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getResultClassName() {
        return resultClassName;
    }

    public void setResultClassName(String resultClassName) {
        this.resultClassName = resultClassName;
    }

    public String getRequestObject() {
        return requestObject;
    }

    public void setRequestObject(String requestObject) {
        this.requestObject = requestObject;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public RequestParameter[] getRequestParameter() {
        return requestParameter;
    }

    public void setRequestParameter(RequestParameter[] requestParameter) {
        this.requestParameter = requestParameter;
    }
}
