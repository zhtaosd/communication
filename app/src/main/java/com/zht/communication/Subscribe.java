package com.zht.communication;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
* 标记接受方法的注解，运行时注解
* @author zhanghaitao
* created at 2019/2/13  16:09
*/
@Retention(RetentionPolicy.RUNTIME)
public @interface Subscribe {
    ThreadMode threadMode() default ThreadMode.PostThread;
}
