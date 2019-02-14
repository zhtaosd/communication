package com.zht.communication;

/**
* 用来定义运行线程的枚举类
* @author zhanghaitao
* created at 2019/2/13  16:06
*/
public enum ThreadMode {
    PostThread,
    MainThread,//运行在主线程
    Async //运行在异步线程
}
