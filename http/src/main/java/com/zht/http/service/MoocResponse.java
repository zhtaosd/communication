package com.zht.http.service;

public abstract class MoocResponse<T> {
    public abstract void success(MoocRequest request,T data);

    public abstract void fail(int errorCode,String errorMsg);

}
