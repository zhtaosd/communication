package com.zht.communication;

import java.lang.reflect.Method;

/**
* 存储标记方法信息的bean
* @author zhanghaitao
* created at 2019/2/13  16:13
*/
public class SubScribeMethod {
    private Method method;  //方法名
    private ThreadMode threadMode;//方法运行线程
    private Class<?> evenType; //方法参数类型

    public SubScribeMethod(Method method, ThreadMode threadMode, Class<?> evenType) {
        this.method = method;
        this.threadMode = threadMode;
        this.evenType = evenType;
    }

    public Method getMethod() {
        return method;
    }

    public ThreadMode getThreadMode() {
        return threadMode;
    }

    public Class<?> getEvenType() {
        return evenType;
    }
}
