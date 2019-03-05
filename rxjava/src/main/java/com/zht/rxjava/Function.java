package com.zht.rxjava;

/**
* 实现类型的转换
* @author zhanghaitao
* created at 2019/3/4  15:16
*/
public interface Function<T,R> {
    R apply(T t);
}
