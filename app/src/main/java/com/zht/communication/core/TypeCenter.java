package com.zht.communication.core;

import android.text.TextUtils;

import com.zht.communication.utils.TypeUtils;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhanghaitao
 * created at 2019/2/21  11:00
 */
public class TypeCenter {
    private static final TypeCenter instance = new TypeCenter();

    public static TypeCenter getInstance() {
        return instance;
    }

    private TypeCenter() {
        mAnnotatedClasses = new ConcurrentHashMap<>();
        mRawmethods = new ConcurrentHashMap<>();
    }

    //存储相关的类信息
    private final ConcurrentHashMap<String, Class<?>> mAnnotatedClasses;
    //存储相关的方法信息
    private final ConcurrentHashMap<Class<?>, ConcurrentHashMap<String, Method>> mRawmethods;

    public void register(Class<?> clazz) {
        registerClass(clazz);
        registerMethod(clazz);
    }

    private void registerMethod(Class<?> clazz) {
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            mRawmethods.putIfAbsent(clazz, new ConcurrentHashMap<String, Method>());
            ConcurrentHashMap map = mRawmethods.get(clazz);
            String key = TypeUtils.getMethodId(method);
            map.put(key, method);
        }
    }

    private void registerClass(Class<?> clazz) {
        String clazzName = clazz.getName();
        mAnnotatedClasses.put(clazzName, clazz);
    }

    public Class<?> getClassType(String name) {
        if (TextUtils.isEmpty(name)) {
            return null;
        }
        Class<?> clazz = mAnnotatedClasses.get(name);
        if (clazz == null) {
            try {
                clazz = Class.forName(name);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return clazz;
    }
}
